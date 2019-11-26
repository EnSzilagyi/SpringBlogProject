package dev.esz.blog.security;

import dev.esz.blog.security.dto.JwtAuthenticationResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.impl.DefaultClock;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import sun.tools.jconsole.JConsole;


import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import java.util.Map;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Clock clock = DefaultClock.INSTANCE;

    public String getUserNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token){
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        JwtUser user = (JwtUser) userDetails;
        final String username =getUserNameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        return username.equals(user.getUsername())
                && !isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate());
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }


    private <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Boolean isTokenExpired(String token){
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset){
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private  Date calculateExpirationDate (Date createdDate){
        return new Date(createdDate.getTime() + expiration * 1000 );
    }
    public JwtAuthenticationResponse generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }


    private JwtAuthenticationResponse doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return new JwtAuthenticationResponse(Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact(), expiration);
    }

}
