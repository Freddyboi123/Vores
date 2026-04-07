package app.controllers;

import app.dao.CommentDAO;
import app.dao.UserDAO;
import app.dto.CommentDTO;
import app.entities.Comment;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class CommentController {
    EntityManagerFactory emf;
    CommentDAO commentDAO;
    public CommentController(EntityManagerFactory emf){
        this.emf = emf;
        this.commentDAO = new CommentDAO(emf);
    }
    public void getAllCommentsFromPost(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        Set<CommentDTO> allComments = commentDAO.getAllCommentsFromPost(id);
        ctx.json(allComments);
        ctx.status(200);
    }

    public void updateComment(Context ctx) {
        CommentDTO comment = ctx.bodyAsClass(CommentDTO.class);

        Comment updatedComment = commentDAO.updateComment(comment);
        if ( updatedComment == null) {
            ctx.status(404).json(Map.of("error", "Comment not found"));
            return;
        }
        ctx.status(200).json(new CommentDTO(updatedComment));
    }
}
