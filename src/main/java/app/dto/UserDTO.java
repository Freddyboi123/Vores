package app.dto;

import lombok.Data;

@Data
public class UserDTO extends dk.bugelhartmann.UserDTO {
    private String username;
    private String password;
    private String email;

    public UserDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public UserDTO(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
    }

}
