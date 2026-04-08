package app.rest;

import app.config.Hibernate.HibernateConfig;
import app.controllers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {
    ObjectMapper objectMapper = new ObjectMapper();

    SecurityController securityController;
    UserController userController;
    CommentController commentController;
    PostController postController;
    EntityManagerFactory emf;

    public Routes(EntityManagerFactory emf){
        this.emf = emf;
        this.securityController = new SecurityController(emf);
        this.userController = new UserController(emf);
        this.commentController = new CommentController(emf);
        this.postController = new PostController(emf);
    }

    public EndpointGroup getRouteResource(String resourceName) {
        return switch (resourceName.toLowerCase()) {
            case "msg" -> () -> path("msg", () -> {
                ObjectNode on = objectMapper.createObjectNode();
                on.put("msg", "Hello World from your new and correct Java Project, now with WatchTower. WUHU watchtower is working!");
                get("hello", ctx -> ctx.json(on));
                post("echo", ctx -> ctx.result(ctx.body()));
            });
            case "auth" -> () -> path("auth", () -> {

                post("register", securityController::register);
                post("login",securityController::login);
            });
            case "users" -> () -> path("users", () -> {

                get("getAll", userController::getAllUsers);
                get("getUser/{id}", userController::getUserByID);
                post("createUser",userController::createUser);
                post("updateUser/{id}",userController::updateUser);
                delete("deleteUser/{id}",userController::deleteUserByID);
            });
            case "comments" -> () -> path("comments", () -> {
                get("getAllCommentFromPost/{id}",commentController::getAllCommentsFromPost);
                post("updateComment/{id}",commentController::updateComment);

            });
            case "post" -> () -> path("post", () -> {
                //post("createPost",postController::create);

            });

            default -> throw new IllegalArgumentException("Unknown resource name: " + resourceName);
        };
    }
    }

