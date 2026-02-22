package app.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comments;

import javax.security.sasl.AuthorizeCallback;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    private String postContent;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private Integer likesCount;
    private Integer dislikesCount;
    private Integer commentsCount;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Comment> comments = new HashSet<>();

    public Post(String postContent, User user) {}

}
