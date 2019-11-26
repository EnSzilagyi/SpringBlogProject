package dev.esz.blog.blog.repository;

import dev.esz.blog.blog.entity.Blog;
import dev.esz.blog.user.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Blog findByUser(User user);
    Blog findBlogById(Long id);
    @Query(value = "SELECT * FROM blog WHERE user_id = :userId", nativeQuery = true)
    List<Blog> findAllBlogsByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "UPDATE blog SET STORY = :story WHERE id = :blogId",nativeQuery = true)
    void updateBlog(@Param("story")String story , @Param("blogId")Long blogId);
    //INSERT INTO `blog`(title ,story ,user_id) VALUES ("This is a test","This is a query created blog",58);
    @Modifying
    @Query(value = "insert into blog(title,story,user_id) values (:title,:story,:userId)",nativeQuery = true)
    void addBlogQuery(@Param("title")String title, @Param("story")String story,@Param("userId")Long userId);


    @Query(value = "SELECT * FROM blog WHERE user_id = :userId and id = :id", nativeQuery = true)
    Blog findBlogByUserId(@Param("userId") Long userId, @Param("id") Long id);


}
