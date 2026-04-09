package app.controllers;

import app.dao.FriendshipDAO;
import app.dao.UserDAO;
import app.dto.UserDTO;
import app.entities.Friendship;
import app.entities.User;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FriendshipController {


    EntityManagerFactory emf;
    FriendshipDAO friendshipDAO;
    UserDAO userDAO;
    public FriendshipController(EntityManagerFactory emf){
        this.emf = emf;
        this.friendshipDAO = new FriendshipDAO(emf);
        this.userDAO = new UserDAO(emf);
    }

    public void sendRequest(Context ctx) {
        Long from = ctx.attribute("userId");

        if (from == null) {
            ctx.status(401).result("Unauthorized");
            return;
        }

        Long to = Long.parseLong(ctx.pathParam("id"));

        friendshipDAO.sendRequest(from, to);

        ctx.status(201);
    }

    public void accept(Context ctx) {
        int requestId = Integer.parseInt(ctx.pathParam("id"));

        friendshipDAO.acceptRequest(requestId);

        ctx.status(200);
    }

    public void getFriends(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("id"));

        List<Long> friends = friendshipDAO.getFriends(userId);

        ctx.json(friends);
    }

    public void getRequests(Context ctx){
        int userId = Integer.parseInt(ctx.pathParam("id"));
        List<Friendship> friends = friendshipDAO.getPendingRequests(userId);

        List<UserDTO> incomingRequests = new ArrayList<>();
        for (Friendship f: friends){
            UserDTO dto = new UserDTO(userDAO.getUserById(f.getRequesterId()));
            incomingRequests.add(dto);
        }
        ctx.json(incomingRequests);
        ctx.status(201);
    }
}
