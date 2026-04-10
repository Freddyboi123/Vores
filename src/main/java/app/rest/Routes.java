package app.rest;

import app.config.Hibernate.HibernateConfig;
import app.controllers.*;
import app.entities.Roles;
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
    FeedController feedController;
    FriendshipController friendshipController;
    EntityManagerFactory emf;

    public Routes(EntityManagerFactory emf){
        this.emf = emf;
        this.securityController = new SecurityController(emf);
        this.userController = new UserController(emf);
        this.commentController = new CommentController(emf);
        this.postController = new PostController(emf);
        this.feedController = new FeedController(emf);
        this.friendshipController = new FriendshipController(emf);
    }

    public EndpointGroup getRouteResource(String resourceName) {
        return switch (resourceName.toLowerCase()) {
            case "msg" -> () -> path("msg", () -> {
                ObjectNode on = objectMapper.createObjectNode();
                on.put("msg", "Hello and welcome to my exam project");
                get("hello", ctx -> ctx.json(on));
                post("echo", ctx -> ctx.result(ctx.body()));
            });
            case "auth" -> () -> path("auth", () -> {

                post("register", securityController::register);
                post("login",securityController::login, Roles.USER);
            });
            case "users" -> () -> path("users", () -> {

                get("getAll", userController::getAllUsers, Roles.USER);
                get("getUser/{id}", userController::getUserByID, Roles.USER);
                post("createUser",userController::createUser, Roles.USER);
                post("updateUser/{id}",userController::updateUser, Roles.USER);
                delete("deleteUser/{id}",userController::deleteUserByID, Roles.ADMIN);
            });
            case "comments" -> () -> path("comments", () -> {
                get("getAllCommentFromPost/{id}",commentController::getAllCommentsFromPost, Roles.USER);
                post("createComment",commentController::createComment, Roles.USER);
                post("updateComment/{id}",commentController::updateComment, Roles.USER);

            });
            case "posts" -> () -> path("posts", () -> {
                post("createPost",postController::createPost, Roles.USER);
                get("getNew/{offset}",postController::getNextPosts, Roles.USER);

            });
            case "feed" -> () -> path("feed", () -> {
                get("getNew/{offset}",feedController::getNextPosts, Roles.USER);
                get("getRealPost/{offset}",feedController::getNextPosts, Roles.USER);

            });
            case "friend" -> () -> path("friend", () -> {
                post("sendReq/{id}",friendshipController::sendRequest, Roles.USER);
                get("getReq/{id}",friendshipController::getRequests, Roles.USER);
                get("getFriends/{id}",friendshipController::getFriends, Roles.USER);
                post("accReg/{id}",friendshipController::accept, Roles.USER);
                post("declineReq/{id}",friendshipController::decline, Roles.USER);
            });

            default -> throw new IllegalArgumentException("Unknown resource name: " + resourceName);
        };
    }
    }

