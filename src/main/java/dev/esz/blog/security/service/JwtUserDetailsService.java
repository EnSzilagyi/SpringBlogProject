package dev.esz.blog.security.service;

import dev.esz.blog.security.JwtUser;
import dev.esz.blog.security.JwtUserFactory;
import dev.esz.blog.user.dao.User;
import dev.esz.blog.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    public UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return JwtUserFactory.create(user);
    }


}
