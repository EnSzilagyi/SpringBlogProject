package dev.esz.blog;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BlogApplication  {

    public static void main(String[] args) {

        SpringApplication.run(BlogApplication.class, args);
       //
        // zPasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();

        //System.out.println(passwordEncoder.encode("test"));
        // !!! This should be taken from a database, for a demo we use hardcoded values.

    }


}
