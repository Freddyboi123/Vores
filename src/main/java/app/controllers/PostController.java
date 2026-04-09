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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PostController {
    EntityManagerFactory emf;
    PostDAO postDAO;
    UserDAO userDAO;

    public PostController(EntityManagerFactory emf) {
        this.emf = emf;
        this.postDAO = new PostDAO(emf);
        this.userDAO = new UserDAO(emf);
    }

    public void createPost(Context ctx) {
        PostDTO dto = ctx.bodyAsClass(PostDTO.class);

        User user = userDAO.getUserById(dto.getUserId());

        if (user == null) {
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


    private final int LIMIT = 3;
    public void getNextPosts(Context ctx) {
        String offsetParam = ctx.queryParam("offset");
        int offset = (offsetParam != null)
                ? Integer.parseInt(offsetParam)
                : 0;
        List<Post> posts = postDAO.getLatestPosts(LIMIT, offset);

        List<PostResponseDTO> response = new ArrayList<>();
        for (Post p : posts){
            response.add(new PostResponseDTO(p));
        }
        ctx.json(response);
    }
}
