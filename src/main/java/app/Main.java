package app;

import app.config.Hibernate.HibernateConfig;
import app.config.security.ApplicationConfig;
import app.dao.UserDAO;
import app.entities.Weather.WeeklyForcast;
import app.rest.Routes;

import app.utils.WeatherApiHandler.WeatherApi;
import app.utils.devTools.ThreadsClass;
import jakarta.persistence.EntityManagerFactory;

import java.util.concurrent.*;


public class Main {

    public static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        public static void main(String[] args) {
            Routes routes = new Routes(emf);
            new ApplicationConfig(emf)
                    .security()
                    .routeOverview()
                    .fakeAuth()
                    .route(routes.getRouteResource("msg"))
                    .route(routes.getRouteResource("auth"))
                    .route(routes.getRouteResource("users"))
                    .route(routes.getRouteResource("comments"))
                    .route(routes.getRouteResource("posts"))
                    .route(routes.getRouteResource("feed"))
                    .route(routes.getRouteResource("friend"))
                    .cors()
                    .exceptions()
                    .apiExceptions()
                    .start(7072);
        }
    }