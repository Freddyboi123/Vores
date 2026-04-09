package app.controllers;

import app.dao.CommentDAO;
import app.dao.PostDAO;
import app.dao.UserDAO;
import app.dto.CommentDTO;
import app.dto.CommentResponseDTO;
import app.entities.Comment;
import app.entities.Post;
import app.entities.User;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.Map;
import java.util.Set;

public class CommentController {
    EntityManagerFactory emf;
    CommentDAO commentDAO;
    UserDAO userDAO;
    PostDAO postDAO;
    public CommentController(EntityManagerFactory emf){
        this.emf = emf;
        this.commentDAO = new CommentDAO(emf);
        this.userDAO = new UserDAO(emf);
        this.postDAO = new PostDAO(emf);

    }
    public void createComment(Context ctx){
        CommentDTO dto = ctx.bodyAsClass(CommentDTO.class);

        User user = userDAO.getUserById(dto.getUserId());
        Post post = postDAO.getPost(dto.getPostId());

        if (user == null || post == null) {
            ctx.status(400).result("User or Post not found");
            return;
        }

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setCommentContent(dto.getBody());

        commentDAO.createComment(comment);

        // 🔥 Convert to response DTO
        CommentResponseDTO response = new CommentResponseDTO(comment);

        ctx.json(response);
        ctx.status(201);
    }

    public void getAllCommentsFromPost(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        Set<CommentDTO> allComments = commentDAO.getAllCommentsFromPost(id);
        ctx.json(allComments);
        ctx.status(200);
    }

    public void updateComment(Context ctx) {
        CommentDTO comment = ctx.bodyAsClass(CommentDTO.class);

        CommentDTO updatedComment = commentDAO.updateComment(comment);
        if ( updatedComment == null) {
            ctx.status(404).json(Map.of("error", "Comment not found"));
            return;
        }
        ctx.status(200).json(updatedComment);
    }


}
