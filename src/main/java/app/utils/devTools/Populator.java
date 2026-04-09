package app.utils.devTools;

import app.config.Hibernate.HibernateConfig;
import app.dao.CommentDAO;
import app.dao.PostDAO;
import app.dao.UserDAO;
import app.entities.*;
import jakarta.persistence.EntityManagerFactory;

import static app.Main.emf;


public class Populator {
    //private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    public static void main(String[] args) {


        User user1 = new User("Frederik","SolOverGudhejm","fred@dk.dk");
        User user2 = new User ("Daniel","RønneStrand","dan@dk.dk");
        User user3 = new User ("Luke","AlingeSandvig","luke@dk.dk");
        User user4 = new User("Emil","VivaLaFrancs","Emil@dk.dk");

        UserDAO userDAO = new UserDAO(emf);
        PostDAO postDAO = new PostDAO(emf);
        CommentDAO commentDAO = new CommentDAO(emf);

        PrivacySettings privacySettings1 = new PrivacySettings(false,false,false);
        PrivacySettings privacySettings2 = new PrivacySettings(false,false,false);
        PrivacySettings privacySettings3 = new PrivacySettings(true,true,true);
        PrivacySettings privacySettings4 = new PrivacySettings(true,true,true);

        user1.addRole(Roles.ADMIN);

        user1.addPrivacySettings(privacySettings1);
        user2.addPrivacySettings(privacySettings2);
        user3.addPrivacySettings(privacySettings3);
        user4.addPrivacySettings(privacySettings4);

        userDAO.createUser(user1);
        userDAO.createUser(user2);
        userDAO.createUser(user3);
        userDAO.createUser(user4);

        Post post1 = new Post("vejerte er så godt i dag <3");
        Post post2 = new Post("Vi har hold følsesdag med Lise i dag");
        Post post3 = new Post("Er der nogle der vil med ud på Vesterbro og drikke en øl?");
        Post post4 = new Post("Marius vil gerne ud og cykle med nogle i dag hvis der er nogle der har lyst");
        Post post5 = new Post("Er der nogle der har set vores kat?");
        Post post6 = new Post("Vi har ekstra smør hos os hvis nogle mangler ");
        user1.addPost(post1);
        user2.addPost(post2);
        user3.addPost(post3);
        user4.addPost(post4);
        user1.addPost(post5);
        user2.addPost(post6);

        postDAO.createPost(post1);
        postDAO.createPost(post2);
        postDAO.createPost(post3);
        postDAO.createPost(post4);
        postDAO.createPost(post5);
        postDAO.createPost(post6);

        Comment comment1 = new Comment("det kan jeg kun give dig ret i!", user2);
        Comment comment2 = new Comment("Ej det blæser ret meget her i Gentofte :(", user3);
        Comment comment3 = new Comment("Ej hvor ser det hyggeligt ud", user4);
        Comment comment4 = new Comment("Hvor gammel bliver hun?!", user3);
        Comment comment5 = new Comment("Håber i gemmer noget kage til mig!!", user1);
        comment1.setPost(post1);
        comment2.setPost(post1);
        comment3.setPost(post2);
        comment4.setPost(post2);
        comment5.setPost(post2);

        commentDAO.createComment(comment1);
        commentDAO.createComment(comment2);
        commentDAO.createComment(comment3);
        commentDAO.createComment(comment4);
        commentDAO.createComment(comment5);


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

    }
}
