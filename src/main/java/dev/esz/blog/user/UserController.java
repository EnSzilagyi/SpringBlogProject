package dev.esz.blog.user;

import dev.esz.blog.security.JwtTokenUtil;
import dev.esz.blog.user.dao.User;
import dev.esz.blog.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;



@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(allowedHeaders = "*")
public class UserController extends WebMvcConfigurationSupport {
    private final UserService userService;
    private JwtTokenUtil jwtTokenUtil;
    private Logger logger;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(){
        return userService.getUsers() ;
    }

    @GetMapping(value = "/username", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUsername(HttpServletRequest request,@Value("${jwt.header}") String tokenHeader) {
        return userService.getUsernameFromToken(request,tokenHeader);
    }







}


