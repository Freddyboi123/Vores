package app.controllers;

import app.devTools.Populator;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class DevController {


    public DevController(EntityManagerFactory emf){


    }

    public void runpop(Context ctx) {
        Populator.main(new String[]{});
        ctx.status(200);
    }
}
