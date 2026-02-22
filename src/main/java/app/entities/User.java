package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "privacy_settings_id")
    private PrivacySettings privacySettings;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();



    public void addComment(Comment comment) {
        comments.add(comment);
        if (comment != null){
            comment.setUser(this);
        }
    }

    public void addPost(Post post) {
        posts.add(post);
        if (post != null){
            post.setUser(this);
        }
    }






    @Override
    public String toString() {
        return "User: " +  name + "\n" +
                "Email: " + email + "\n" +
                "Password: " + password;
    }
}
