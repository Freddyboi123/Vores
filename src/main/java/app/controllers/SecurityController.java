package app.controllers;

import app.config.security.ISecurityController;
import app.dao.UserDAO;

import app.entities.User;
import app.exeptions.ApiException;
import app.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dk.bugelhartmann.ITokenSecurity;
import dk.bugelhartmann.TokenSecurity;
import dk.bugelhartmann.TokenVerificationException;
import dk.bugelhartmann.UserDTO;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.validation.ValidationException;
import jakarta.persistence.EntityManagerFactory;

import java.text.ParseException;
import java.util.Set;


public class SecurityController implements ISecurityController {

    private EntityManagerFactory emf;
    private ObjectMapper objectMapper = new ObjectMapper();
    private ITokenSecurity tokenSecurity = new TokenSecurity();


    public SecurityController(EntityManagerFactory emf){
       this.emf = emf;
    }
    UserDAO userDAO = new UserDAO(emf);

    @Override
    public void login(Context ctx) {
        UserDTO user = ctx.bodyAsClass(UserDTO.class);
        try{
            User userEntity = userDAO.getVerifiedUser(user.getUsername(), user.getPassword());
            String token = createToken(new UserDTO(userEntity.getName(),userEntity.getRolesAsString()));
            ObjectNode node = objectMapper.createObjectNode();

            ctx.status(200).json(node
                    .put("token",token)
                    .put("username",userEntity.getName()));
        } catch (ValidationException ex) {
            throw new ApiException(401, ex.getMessage());
        }
    }

    @Override
    public void register(Context ctx) {

    }

    @Override
    public void authenticate(Context ctx) {

    }

    @Override
    public void authorize(Context ctx) {

    }


    private String createToken(dk.bugelhartmann.UserDTO user) {
        try {
            String ISSUER;
            String TOKEN_EXPIRE_TIME;
            String SECRET_KEY;

            if (System.getenv("DEPLOYED") != null) {
                ISSUER = System.getenv("ISSUER");
                TOKEN_EXPIRE_TIME = System.getenv("TOKEN_EXPIRE_TIME");
                SECRET_KEY = System.getenv("SECRET_KEY");
            } else {
                ISSUER = Utils.getPropertyValue("ISSUER", "config.properties");
                TOKEN_EXPIRE_TIME = Utils.getPropertyValue("TOKEN_EXPIRE_TIME", "config.properties");
                SECRET_KEY = Utils.getPropertyValue("SECRET_KEY", "config.properties");
                System.out.println(ISSUER+" "+TOKEN_EXPIRE_TIME+" "+SECRET_KEY);
            }
            return tokenSecurity.createToken(user, ISSUER, TOKEN_EXPIRE_TIME, SECRET_KEY);

        } catch (Exception e) {
//            logger.error("Could not create token", e);
            e.printStackTrace();
            throw new ApiException(500, "Could not create token");
        }
    }
    private static String getToken(Context ctx) {
        String header = ctx.header("Authorization");
        if (header == null) {
            throw new UnauthorizedResponse("Authorization header is missing"); // UnauthorizedResponse is javalin 6 specific but response is not json!
        }

        // If the Authorization Header was malformed, then no entry
        String token = header.split(" ")[1];
        if (token == null) {
            throw new UnauthorizedResponse("Authorization header is malformed"); // UnauthorizedResponse is javalin 6 specific but response is not json!
        }
        return token;
    }

    private boolean isOpenEndpoint(Set<String> allowedRoles) {
        // If the endpoint is not protected with any roles:
        if (allowedRoles.isEmpty())
            return true;

        // 1. Get permitted roles and Check if the endpoint is open to all with the ANYONE role
        if (allowedRoles.contains("ANYONE")) {
            return true;
        }
        return false;
    }

    private dk.bugelhartmann.UserDTO validateAndGetUserFromToken(Context ctx) {
        String token = getToken(ctx);
        dk.bugelhartmann.UserDTO verifiedTokenUser = verifyToken(token);
        if (verifiedTokenUser == null) {
            throw new UnauthorizedResponse("Invalid user or token"); // UnauthorizedResponse is javalin 6 specific but response is not json!
        }
        return verifiedTokenUser;
    }

    private dk.bugelhartmann.UserDTO verifyToken(String token) {
        boolean IS_DEPLOYED = (System.getenv("DEPLOYED") != null);
        String SECRET = IS_DEPLOYED ? System.getenv("SECRET_KEY") : Utils.getPropertyValue("SECRET_KEY", "config.properties");

        try {
            if (tokenSecurity.tokenIsValid(token, SECRET) && tokenSecurity.tokenNotExpired(token)) {
                return tokenSecurity.getUserWithRolesFromToken(token);
            } else {
                throw new ApiException(403, "Token is not valid");
            }
        } catch (ParseException | TokenVerificationException e) {
//            logger.error("Could not create token", e);
            throw new ApiException(HttpStatus.UNAUTHORIZED.getCode(), "Unauthorized. Could not verify token");
        }
    }

    private static boolean userHasAllowedRole(dk.bugelhartmann.UserDTO user, Set<String> allowedRoles) {
        return user.getRoles().stream()
                .anyMatch(role -> allowedRoles.contains(role.toUpperCase()));
    }

}
