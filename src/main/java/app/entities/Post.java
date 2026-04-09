package app.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comments;

import javax.security.sasl.AuthorizeCallback;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter

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
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Viewability viewability;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Comment> comments = new HashSet<>();
    public Post() {
        this.likesCount = 0;
        this.dislikesCount = 0;
        this.commentsCount = 0;
    }
    public Post(String postContent) {
        this.postContent = postContent;
        this.likesCount = 0;
        this.dislikesCount = 0;
        this.commentsCount = 0;
    }

    public Post(String postContent, int likesCount, int dislikesCount) {
        this.postContent = postContent;

        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
    }

    public void setUser(User user) {
        this.user = user;
        if (user.getPrivacySettings().isPostsPublic()){
            this.viewability = Viewability.PUBLIC;
        } else {
            this.viewability = Viewability.FRIENDS_ONLY;
        }
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }


    public void likePost(){
        this.likesCount ++;
    }
    public void dislikePost(){
        this.dislikesCount ++;
    }

    public enum Viewability {
        PUBLIC,
        FRIENDS_ONLY,
        PRIVATE

    }
}
