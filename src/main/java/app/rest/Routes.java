package app.rest;

import app.config.Hibernate.HibernateConfig;
import app.controllers.CommentController;
import app.controllers.DevController;
import app.controllers.SecurityController;
import app.controllers.UserController;
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
    DevController devController;
    EntityManagerFactory emf;

    public Routes(EntityManagerFactory emf){
        this.emf = emf;
        this.securityController = new SecurityController(emf);
        this.userController = new UserController(emf);
        this.commentController = new CommentController(emf);
        this.devController = new DevController(emf);
    }

    public EndpointGroup getRouteResource(String resourceName) {
        return switch (resourceName.toLowerCase()) {
            case "msg" -> () -> path("msg", () -> {
                ObjectNode on = objectMapper.createObjectNode();
                on.put("msg", "Hello World from your new and correct Java Project, now with WatchTower. WUHU watchtower is working!, nowtissemand");
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
                delete("deleteUser/{id}",userController::deleteUserByID);
                post("updateUser/{id}",userController::updateUser);
            });
            case "comments" -> () -> path("comments", () -> {
                get("getAllCommentFromPost/{id}",commentController::getAllCommentsFromPost);
                post("updateComment/{id}",commentController::updateComment);

            });
            case "dev" -> () -> path("dev", () -> {
                post("pop",devController::runpop);

            });

            default -> throw new IllegalArgumentException("Unknown resource name: " + resourceName);
        };
    }
    }

