package app;

import app.config.Hibernate.HibernateConfig;
import app.config.security.ApplicationConfig;
import app.dao.UserDAO;
import app.entities.User;
import app.rest.Routes;

import jakarta.persistence.EntityManagerFactory;


public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        public static void main(String[] args) {
            UserDAO userDAO = new UserDAO(emf);
            userDAO.createUser(new User("Peter","1234","Peter@dk.dk"));
            Routes routes = new Routes(emf);
            new ApplicationConfig(emf)
                    .security()
//                .route(securityRoutes.getRouteResource("auth"))
//                .route(securityRoutes.getRouteResource("protected"))
//                .route(routes.getRouteResource("open/person"))
                    .route(routes.getRouteResource("msg"))
                    .route(routes.getRouteResource("auth"))
//                .route(() -> {
//                    path("/index", () -> {
//                        get("/", ctx -> ctx.render("index.html"));
//                    });
//                })
                    .cors()
                    .exceptions()
                    .apiExceptions()
                    .start(7072);
        }
    }