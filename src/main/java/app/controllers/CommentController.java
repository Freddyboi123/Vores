package app.controllers;

import app.dao.CommentDAO;
import app.dao.UserDAO;
import app.dto.CommentDTO;
import app.entities.Comment;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

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
}
