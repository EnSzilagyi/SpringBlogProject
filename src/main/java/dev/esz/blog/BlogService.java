package dev.esz.blog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BlogService {
    private final BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
    }

    public List<Blog> getBlogs(){
        return StreamSupport.stream(blogRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }


    public Optional<Blog> getBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public void addBlog(Blog blog) {
        blogRepository.save(blog);
    }

    public void updateStudent(Blog blog) throws BlogNotFoundException {
        getBlogById(blog.getId()).orElseThrow(BlogNotFoundException::new);
        blogRepository.save(blog);
    }

    public void deleteBlog(Long id) throws BlogNotFoundException {
        Blog blog = getBlogById(id).orElseThrow(BlogNotFoundException::new);
        blogRepository.delete(blog);
    }
}
