package dev.esz.blog.user.dao;


import dev.esz.blog.blog.entity.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "user",uniqueConstraints = {
    @UniqueConstraint(columnNames = {
            "username"
    })
})
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String username;
    private String password;
    private boolean enabled;
    private Date lastPasswordResetDate;
//    @NotNull
//    @Lob
//    //private String blogs;

    @Transient
    private List<Authority> authorities = Arrays.asList(Authority.ROLE_ADMIN);

    public User(String username, String password,boolean enabled, Date lastPasswordResetDate){
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.lastPasswordResetDate=lastPasswordResetDate;
    }

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Authority> roles = new HashSet<>();


}
