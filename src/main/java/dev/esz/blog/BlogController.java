package dev.esz.blog;


import dev.esz.blog.BlogNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import sun.tools.jconsole.JConsole;


import java.util.List;

@RestController
@RequestMapping(value = "/api/blog")
@CrossOrigin(allowedHeaders = "*")
public class BlogController extends WebMvcConfigurationSupport {
    private final BlogService blogService;


    @Autowired
    public BlogController(BlogService blogService){this.blogService = blogService;}

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Blog> getAllBlogs(){
        return blogService.getBlogs();
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Blog getBlog(@PathVariable("id") Long id) throws BlogNotFoundException {
        return  blogService.getBlogById(id).orElseThrow(BlogNotFoundException::new);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND,
            reason = "Student not found!")
    @ExceptionHandler(BlogNotFoundException.class)
    public void notFound() {
        // Nothing to do
    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long addNewBlog(@RequestBody Blog blog) {
        blogService.addBlog(blog);
        System.out.println(blog.getId());
        return blog.getId();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateBlog(@RequestBody Blog blog, @PathVariable String id) throws BlogNotFoundException {
        blogService.updateStudent(blog);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteBlog(@PathVariable("id") Long id) throws BlogNotFoundException {
        System.out.println("here");
        blogService.deleteBlog(id);
    }



}
