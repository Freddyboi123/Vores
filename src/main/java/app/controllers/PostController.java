package app.controllers;

import app.dao.PostDAO;
import app.dao.UserDAO;
import app.dto.PostDTO;
import app.dto.PostResponseDTO;
import app.entities.Post;
import app.entities.User;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

public class PostController {
    EntityManagerFactory emf;
    PostDAO postDAO;
    UserDAO userDAO;
    public PostController(EntityManagerFactory emf){
        this.emf = emf;
        this.postDAO = new PostDAO(emf);
        this.userDAO = new UserDAO(emf);
    }

    public void createPost( Context ctx) {
        PostDTO dto = ctx.bodyAsClass(PostDTO.class);

        User user = userDAO.getUserById(dto.getUserId());

        if (user == null){
            ctx.status(400).result("User not found");
            return;
        }
        Post post = new Post();
        post.setUser(user);
        post.setPostContent(dto.getPostContent());

        postDAO.createPost(post);

        PostResponseDTO response = new PostResponseDTO(post);

        ctx.json(response);
        ctx.status(201);
    }
}
