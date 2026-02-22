package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.awt.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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

    public Comment(String commentContent, User user, Post post) {
        this.commentContent = commentContent;
        this.user = user;
        this.post = post;
    }

}


