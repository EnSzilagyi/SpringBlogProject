package dev.esz.blog.blog.controller;


import dev.esz.blog.blog.dto.AddBlogDTO;
import dev.esz.blog.blog.dto.BlogDTO;
import dev.esz.blog.blog.entity.Blog;
import dev.esz.blog.BlogNotFoundException;
import dev.esz.blog.blog.service.BlogService;
import dev.esz.blog.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/blog")
@CrossOrigin(allowedHeaders = "*")
public class BlogController extends WebMvcConfigurationSupport {

    private final BlogService blogService;



    @Autowired
    public BlogController(BlogService blogService){
        this.blogService = blogService;
    }
    @GetMapping(value = "/usersblogs",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public List<BlogDTO> getUsersBlogs(HttpServletRequest request, @Value("${jwt.header}") String tokenHeader){
        return  blogService.getBlogsForUser(request, tokenHeader);
    }
    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public List<BlogDTO> getAllBlogs(){
        return  blogService.getBlogs();
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public BlogDTO getBlog(@PathVariable("id") Long id)  {
        return  blogService.getBlog(id);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND,
            reason = "Blog not found!")
    @ExceptionHandler(BlogNotFoundException.class)
    public void notFound() {
        // Nothing to do
    }

    @GetMapping(value = "/isusersblog/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public boolean isUsersBlog(@PathVariable("id")Long id,   HttpServletRequest request,@Value("${jwt.header}") String tokenHeader){
        return blogService.isUsers(id,request,tokenHeader);
    }


    @PostMapping("/addblog")
    @ResponseStatus(value = HttpStatus.CREATED)
    @Transactional
    public Long addNewBlog(@RequestBody AddBlogDTO addBlogDTO,HttpServletRequest request,@Value("${jwt.header}") String tokenHeader) {
        blogService.addBlog(addBlogDTO,request,tokenHeader);
        System.out.println(addBlogDTO.getId());
        return addBlogDTO.getId();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @Transactional
    public void updateBlog(@RequestBody Blog blog, @PathVariable String id) throws BlogNotFoundException {

        blogService.updateBlog(blog);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBlog(@PathVariable("id") Long id) throws BlogNotFoundException {
        System.out.println("here");
        blogService.deleteBlog(id);
    }



}
