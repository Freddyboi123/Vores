package app.controllers;

import app.dao.UserDAO;
import jakarta.persistence.EntityManagerFactory;

public class FeedController {
    EntityManagerFactory emf;

    public FeedController(EntityManagerFactory emf){
        this.emf = emf;
        //Todo continue from here
    }
}
