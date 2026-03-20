package app.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;

    public UserDTO(String email, String username){
        this.username = email;
        this.password = password;
    }
}
