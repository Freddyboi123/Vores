package app.entities;

import io.javalin.security.RouteRole;

public enum Roles implements RouteRole {
    USER,
    ADMIN
}
