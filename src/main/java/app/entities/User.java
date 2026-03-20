package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import app.entities.Roles;
import org.mindrot.jbcrypt.BCrypt;
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

    Set<Roles>roles = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "privacy_settings_id")
    private PrivacySettings privacySettings;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();

    public User(String name, String password, String email){
        String salt = BCrypt.gensalt(12);
        String hashedPassword = BCrypt.hashpw(password,salt);

        this.name = name;
        this.email = email;
        this.password =hashedPassword;

        addRole(Roles.USER);
    }

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

    public boolean verifyPassword(String password){
        return BCrypt.checkpw(password,this.password);
    }

    public void addRole(Roles role){
       roles.add(role);
    }


    @Override
    public String toString() {
        return "User: " +  name + "\n" +
                "Email: " + email + "\n" +
                "Password: " + password + "\n" +
                "Roles: " + roles.toString();
    }
}
