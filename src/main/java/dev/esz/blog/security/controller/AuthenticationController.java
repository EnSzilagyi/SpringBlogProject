package dev.esz.blog.security.controller;

import dev.esz.blog.security.JwtTokenUtil;
import dev.esz.blog.security.dto.JwtAuthenticationRequest;
import dev.esz.blog.security.dto.JwtAuthenticationResponse;
import dev.esz.blog.security.dto.JwtError;
import dev.esz.blog.security.dto.JwtSignUpRequest;
import dev.esz.blog.user.dao.User;
import dev.esz.blog.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sun.tools.jconsole.JConsole;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(allowedHeaders = "*")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private String tokenHeader;
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService,
                                    @Value("${jwt.header}") String tokenHeader, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenHeader = tokenHeader;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
            throws DisabledException, BadCredentialsException {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
      // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        System.out.println(userDetails.getUsername());
        return jwtTokenUtil.generateToken(userDetails);
    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody JwtSignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new JwtError(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        System.out.println(signUpRequest.getPassword());
        // Creating user's account

        User user = new User( signUpRequest.getUsername(),signUpRequest.getPassword(), true , new Date());
        System.out.println(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new JwtError(true, "User registered successfully"));
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class})
    public JwtError handleAuthenticationException() {
        return new JwtError(false,"Bad credentials!");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({DisabledException.class})
    public JwtError handleDisabledException() {
        return new JwtError( false,"User is disabled");
    }

    private void authenticate(String username, String password) throws DisabledException, BadCredentialsException {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
