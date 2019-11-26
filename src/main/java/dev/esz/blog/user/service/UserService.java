package dev.esz.blog.user.service;

import dev.esz.blog.security.JwtTokenUtil;
import dev.esz.blog.security.JwtUser;
import dev.esz.blog.user.dao.User;
import dev.esz.blog.user.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService  {
    private final UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;
    private Logger logger;


    @Autowired
    public UserService(UserRepository userRepository,JwtTokenUtil jwtTokenUtil){
        this.userRepository = userRepository;
        this.jwtTokenUtil=jwtTokenUtil;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return new JwtUser(user);
//    }
    public List<User> getUsers(){
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
    public Optional<User> getUserByUsername(String username){
        return getUsers().stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    public String getUsernameFromToken (HttpServletRequest request,String tokenHeader) {
        final String requestHeader = request.getHeader(tokenHeader);
        String authToken;
        String username = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7); // cut "Bearer " from the front of the requestHeader
            try {
                username = jwtTokenUtil.getUserNameFromToken(authToken);
            } catch (IllegalArgumentException ex) {
                logger.error("An an error occured during getting username from token:" + ex.getMessage());
            }
        } else {
            logger.info("Couldn't find bearer string in the header, so we will ignore it.");
        }
        //System.out.println( userRepository.findByUsername(username).getUserId());
        return username;
    }

    public Long getIdFromToken (HttpServletRequest request,String tokenHeader){
        String username = getUsernameFromToken(request,tokenHeader);
       // System.out.println(userRepository.findByUsername(username).getUserId());
        return userRepository.findByUsername(username).getUserId();
    }


}
