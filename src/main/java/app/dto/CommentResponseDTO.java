package app.dto;

import app.entities.Comment;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentResponseDTO {
    private int id;
    private String body;
    private String username;
    private int postId;

    public CommentResponseDTO(Comment comment) {
        this.id = comment.getCommentId();
        this.body = comment.getCommentContent();
        this.username = comment.getUser().getUsername();
        this.postId = comment.getPost().getPostId();
    }

    public CommentResponseDTO(CommentDTO comment) {
        this.id = comment.getCommentID();
        this.body = comment.getBody();
        this.username = comment.getUsername();
        this.postId = comment.getPostId();
    }
}