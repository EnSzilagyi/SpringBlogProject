package dev.esz.blog.user.repository;

import dev.esz.blog.user.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String Username);
    Boolean existsByUsername(String username);
}
