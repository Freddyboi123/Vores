package app;

import app.entities.User;
import app.utils.WeatherApiHandler.WeatherApi;
import app.entities.Weather.WeeklyForcast;
import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {

    //private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


    public static void main(String[] args) {
    User user = new User("Frederik", "DerErEtYndightLand","Fred@dk.dk");
        System.out.println(user);


    }
}