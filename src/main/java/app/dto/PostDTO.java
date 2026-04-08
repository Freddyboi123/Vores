package app.dto;

import app.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private int userId;
    private String postContent;

    public PostDTO(){}

    public PostDTO(int userId, String postContent) {
        this.userId = userId;
        this.postContent = postContent;
    }
}