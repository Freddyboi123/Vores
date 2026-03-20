package app;

import app.config.Hibernate.HibernateConfig;
import app.config.security.ApplicationConfig;
import app.rest.Routes;

import jakarta.persistence.EntityManagerFactory;


public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        public static void main(String[] args) {
            Routes routes = new Routes(emf);
            new ApplicationConfig(emf)
                    .security()
//                .route(securityRoutes.getRouteResource("auth"))
//                .route(securityRoutes.getRouteResource("protected"))
//                .route(restRoutes.getRouteResource("open/person"))
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
                    .start(7070);
        }
    }