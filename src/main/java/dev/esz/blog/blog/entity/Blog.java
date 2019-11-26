package dev.esz.blog.blog.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.esz.blog.user.dao.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "blog",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "id"
        })
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @NotNull
    @Lob
    private String story;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
}
