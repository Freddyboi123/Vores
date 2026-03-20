package app.rest;

import app.config.Hibernate.HibernateConfig;
import app.controllers.SecurityController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {
    ObjectMapper objectMapper = new ObjectMapper();

    SecurityController securityController;

    public Routes(EntityManagerFactory emf){
        securityController = new SecurityController(emf);
    }

    public EndpointGroup getRouteResource(String resourceName) {
        return switch (resourceName.toLowerCase()) {
            case "msg" -> () -> path("msg", () -> {
                ObjectNode on = objectMapper.createObjectNode();
                on.put("msg", "Hello World");
                get("hello", ctx -> ctx.json(on));
                post("echo", ctx -> ctx.result(ctx.body()));
            });
            case "auth" -> () -> path("auth", () -> {

                post("register", securityController::register);
                post("login",securityController::login);
            });
            default -> throw new IllegalArgumentException("Unknown resource name: " + resourceName);
        };
    }
    }

