package app.config.security;


import app.entities.Roles;
import app.entities.User;
import io.javalin.validation.ValidationException;

import javax.management.relation.Role;

public interface ISecurityDAO {
    User getVerifiedUser(String email, String password) throws ValidationException; // used for login
    User createUser(User user); // used for register
    Role createRole(String role);
    User addUserRole(int id, Roles role);
}

