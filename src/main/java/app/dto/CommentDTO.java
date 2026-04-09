package app.dto;

import app.entities.Comment;
import app.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDTO {
    private int commentID;
    private String body;
    private String username;
    private int upVotes;
    private int downVotes;
    private int userId;
    private int postId;


    public CommentDTO(){}

    public CommentDTO(Comment comment){
        this.commentID = comment.getCommentId();
        this.body = comment.getCommentContent();
        this.upVotes = comment.getLikesCount();
        this.downVotes = comment.getDislikesCount();
        this.userId = comment.getUser().getId();
        this.username = comment.getUser().getUsername();
        this.postId = comment.getPost().getPostId();
    }

}
