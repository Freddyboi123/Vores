package app.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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


    @Override
    public String toString() {
        return "User: " +  name + "\n" +
                "Email: " + email + "\n" +
                "Password: " + password;
    }

}
