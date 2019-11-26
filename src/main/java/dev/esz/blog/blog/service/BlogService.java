package dev.esz.blog.blog.service;


import dev.esz.blog.BlogNotFoundException;
import dev.esz.blog.blog.dto.AddBlogDTO;
import dev.esz.blog.blog.dto.BlogDTO;
import dev.esz.blog.blog.entity.Blog;
import dev.esz.blog.blog.repository.BlogRepository;
import dev.esz.blog.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserService userService;
    @Autowired
    public BlogService(BlogRepository blogRepository,UserService userService){
        this.blogRepository = blogRepository;
        this.userService = userService;
    }

    public List<BlogDTO> getBlogs(){
        //return StreamSupport.stream(blogRepository.findAll().spliterator(), false).collect(Collectors.toList());
        //  System.out.println( blogRepository.findByUserId(userId));
        List<Blog> blogs = blogRepository.findAll();
        return getBlogDTOS(blogs);

    }



    public BlogDTO getBlog(Long id){
        Blog blog = blogRepository.findBlogById(id);
        return new BlogDTO(id,blog.getTitle(),blog.getStory(),blog.getUser().getUsername());
    }

    public List<BlogDTO> getBlogsForUser(HttpServletRequest request,String tokenHeader){
        Long userId = userService.getIdFromToken(request,tokenHeader);

        List<Blog> blogs = blogRepository.findAllBlogsByUserId(userId);
        return getBlogDTOS(blogs);

    }

    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public void addBlog(AddBlogDTO addBlogDTO,HttpServletRequest request,String tokenHeader) {
        blogRepository.addBlogQuery(addBlogDTO.getTitle(),addBlogDTO.getStory(),userService.getIdFromToken(request,tokenHeader));
    }

    public void updateBlog(Blog blog) {
        blogRepository.updateBlog(blog.getStory(),blog.getId());
    }

    public void deleteBlog(Long id) throws BlogNotFoundException {
        Blog blog = getBlogById(id).orElseThrow(BlogNotFoundException::new);
        blogRepository.delete(blog);
    }

    public boolean isUsers(Long id,HttpServletRequest request,@Value("${jwt.header}") String tokenHeader){
        Long uid = userService.getIdFromToken(request,tokenHeader);
        System.out.println(blogRepository.findBlogByUserId(uid, id) != null);
        return blogRepository.findBlogByUserId(uid, id) != null;
    }

    private List<BlogDTO> getBlogDTOS(List<Blog> blogs) {
        List<BlogDTO> blogDTOS= new ArrayList<BlogDTO>();
        BlogDTO blogDTO;
        for(Blog blog : blogs){
            blogDTO= new BlogDTO(blog.getId(),blog.getTitle(),blog.getStory(),blog.getUser().getUsername());
            blogDTOS.add(blogDTO);
        }
        return blogDTOS;
    }


}
