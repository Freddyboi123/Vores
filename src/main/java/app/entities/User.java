package app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties (ignoreUnknown = true)
public class User
{
    //used to create users
    public User(String username, String password, String email){
        String salt = BCrypt.gensalt(12);
        String hashedPassword = BCrypt.hashpw(password,salt);


        this.username = username;
        this.email = email;
        this.password =hashedPassword;

        addRole(Roles.USER);
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    Set<Roles>roles = new HashSet<>();

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private PrivacySettings privacySettings;


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnore
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Builder.Default
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();


    public void addComment(Comment comment) {
        comments.add(comment);
        if (comment != null){
            comment.setUser(this);
        }
    }

    public void addPost(Post post) {
        if (posts == null) {
            posts = new HashSet<>();
        }
        posts.add(post);
        post.setUser(this);
    }

    public boolean verifyPassword(String password){
        return BCrypt.checkpw(password,this.password);
    }

    public void addRole(Roles role){
       roles.add(role);
    }

    public String getRolesAsString(){

        String rolesAsString = "";
        for (Roles r : roles){
            String temp = r.toString();
            rolesAsString += temp + ",";
        }
            rolesAsString = rolesAsString.substring(0, rolesAsString.length() - 1);
        return rolesAsString;
    }

    public void addPrivacySettings(PrivacySettings privacySettings){
        this.privacySettings = privacySettings;

        if (privacySettings != null) {
            privacySettings.setUser(this);
        }
    }

    @Override
    public String toString() {
        return "User: " +  username + "\n" +
                "Email: " + email + "\n" +
                "Password: " + password + "\n" +
                "Roles: " + roles.toString();
    }
}
