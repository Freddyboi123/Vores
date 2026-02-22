package app;

import app.utils.WeatherApiHandler.WeatherApi;
import app.entities.Weather.WeeklyForcast;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {

    //private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO(emf);
//
//        User user = User.builder().name("Frederik").email("frederik@gmail.com").password("12345").build();
//        User user2 = User.builder().name("Emil").email("Emil@gmail.com").password("12345").build();
//
//        PrivacySettings privacySettings = new PrivacySettings(true,true,false);
//        PrivacySettings privacySettings2 = new PrivacySettings(true,true,false);
//
//        user.setPrivacySettings(privacySettings);
//        user2.setPrivacySettings(privacySettings2);
//
//        userDAO.createUser(user);
//        userDAO.createUser(user2);
//
//        Post post = Post.builder().postContent("This is a test post").user(user).build();
//        Post post2 = Post.builder().postContent("This is alo a test, and will be removed").user(user2).build();
//
//        user.addPost(post);
//        user2.addPost(post2);
//
//        PostDAO postDAO = new PostDAO(emf);
//        postDAO.createPost(post);
//        postDAO.createPost(post2);
//
//        Comment comment1 = new Comment("hello this is a test",user,post);
//        Comment comment2 = new Comment("hello this is a test",user,post2);
//        Comment comment3 = new Comment("hello this is a test2",user2,post2);
//        user.addComment(comment1);
//        user.addComment(comment2);
//        user2.addComment(comment3);
//
//
//        CommentDAO commentDAO = new CommentDAO(emf);
//        commentDAO.createComment(comment1);
//        commentDAO.createComment(comment2);
//        commentDAO.createComment(comment3);
//
//        userDAO.deleteUser(user2.getId());

        WeatherApi weatherApi = new WeatherApi();
        WeeklyForcast forecast = weatherApi.getWeatherByCity("Højby", "Odsherred Kommune");
        forecast.getWeeklyForecast();
    }
}