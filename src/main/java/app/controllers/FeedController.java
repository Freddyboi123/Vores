package app.controllers;

import app.dao.CommentDAO;
import app.dao.PostDAO;
import app.dao.UserDAO;
import app.dto.CommentDTO;
import app.dto.FullPostDTO;
import app.dto.PostResponseDTO;
import app.entities.Comment;
import app.entities.Post;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeedController {
    EntityManagerFactory emf;
    PostDAO postDAO;
    CommentDAO commentDAO;
    public FeedController(EntityManagerFactory emf){
        this.emf = emf;
        this.postDAO = new PostDAO(emf);
        this.commentDAO = new CommentDAO(emf);
    }

    private final int LIMIT = 3;
    public void getNextPosts(Context ctx) {
        String offsetParam = ctx.queryParam("offset");
        int offset = (offsetParam != null)
                ? Integer.parseInt(offsetParam)
                : 0;
        List<Post> posts = postDAO.getLatestPosts(LIMIT, offset);

        Set<FullPostDTO>response = new HashSet<>();
        for (Post p : posts){
            Set<CommentDTO>commentsFromPost = commentDAO.getAllCommentsFromPost(p.getPostId());
            FullPostDTO fullPostDTO = new FullPostDTO(p,commentsFromPost);
            response.add(fullPostDTO);
        }
        ctx.json(response);
        ctx.status(201);
    }
}
