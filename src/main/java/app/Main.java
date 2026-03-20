package app;

import app.config.Hibernate.HibernateConfig;
import app.dao.UserDAO;
import app.entities.Roles;
import app.entities.User;
import app.utils.WeatherApiHandler.WeatherApi;
import app.entities.Weather.WeeklyForcast;
import jakarta.persistence.EntityManagerFactory;
import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


    public static void main(String[] args) {





}}