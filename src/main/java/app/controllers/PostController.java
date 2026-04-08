package app.controllers;

import app.dao.PostDAO;
import app.dao.UserDAO;
import jakarta.persistence.EntityManagerFactory;

public class PostController {
    EntityManagerFactory emf;
    PostDAO postDAO;
    public PostController(EntityManagerFactory emf){
        this.emf = emf;
        this.postDAO = new PostDAO(emf);
    }
}
