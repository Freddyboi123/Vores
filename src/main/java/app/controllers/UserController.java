package app.controllers;

import app.dao.UserDAO;
import app.dto.UserDTO;
import app.entities.User;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class UserController {
    EntityManagerFactory emf;
    UserDAO userDAO;
    public UserController(EntityManagerFactory emf){
        this.emf = emf;
        this.userDAO = new UserDAO(emf);
    }

    public void getAllUsers(Context ctx){
        Set<UserDTO> allUsers = userDAO.getAllUsers();
        ctx.json(allUsers);
        ctx.status(201);
    }

    public void getUserByID( Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        UserDTO user = userDAO.getUserDTOById(id);
        ctx.json(user);
        ctx.status(201);
    }

    public void deleteUserByID(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        userDAO.deleteUser(id);
        ctx.status(204);
    }

    public void updateUser(Context ctx) {
        User tempUser = ctx.bodyAsClass(User.class);

        User user = new User(
                tempUser.getUsername(),
                tempUser.getEmail(),
                tempUser.getPassword()
        );

        user.setId(tempUser.getId()); // 🔥 important

        User updatedUser = userDAO.updateUser(user);

        if (updatedUser == null) {
            ctx.status(404).json(Map.of("error", "User not found"));
            return;
        }

        ctx.status(200).json(new UserDTO(updatedUser));
    }
}
