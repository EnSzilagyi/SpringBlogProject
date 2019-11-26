package dev.esz.blog.security.filters;

import dev.esz.blog.security.JwtTokenUtil;
import dev.esz.blog.security.service.JwtUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private String tokenHeader;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JwtAuthenticationTokenFilter(JwtUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, @Value("${jwt.header}") String tokenHeader){
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestHeader = request.getHeader(this.tokenHeader);
        String authToken = null;
        String username = null;
        //System.out.println(requestHeader);
        if(requestHeader != null && requestHeader.startsWith("Bearer ")){
            authToken = requestHeader.substring(7);
            //System.out.println(authToken);
            try{

                username = jwtTokenUtil.getUserNameFromToken(authToken);
                //System.out.println(username);
            } catch (IllegalArgumentException ex){
                logger.error("An error has occurred during getting the username from the token");
            }
        }
        else {
            logger.info(requestHeader);
            logger.info("Couldn't find bearer string in the header, so we will ignore it");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtTokenUtil.validateToken(authToken, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetailsService, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               // System.out.println("userDetails"+userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                logger.info(new StringBuilder("User ").append(username).append(" detected").toString());
            }else {
                logger.warn("Invalid token for user" + username);
            }
        }
        filterChain.doFilter(request, response);
    }
}
