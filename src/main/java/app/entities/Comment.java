package app.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.awt.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private Integer likesCount;
    private Integer dislikesCount;

    public Comment(String commentContent, User user) {
        this.commentContent = commentContent;
        this.user = user;
        this.likesCount = 0;
        this.dislikesCount = 0;
    }

    public void setPost(Post post) {
        this.post = post;
        if (post != null) {
            post.getComments().add(this);
        }
    }
    }



