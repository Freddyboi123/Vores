package app.dto;

import app.entities.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDTO {
    int postID;
    int userID;
    String postContent;

    public PostResponseDTO(Post post){
        this.postID = post.getPostId();
        this.userID = post.getUser().getId();
        this.postContent = post.getPostContent();
    }

}
