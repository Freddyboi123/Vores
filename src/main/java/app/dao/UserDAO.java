package app.dao;

import app.config.security.ISecurityDAO;
import app.dto.UserDTO;
import app.entities.Roles;
import app.entities.User;
import io.javalin.validation.ValidationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDAO implements ISecurityDAO {

    private final EntityManagerFactory emf;

    public UserDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public User createUser(User user) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
        return user;
    }

    @Override
    public Role createRole(String role) {
        return null;
    }

    @Override
    public User getVerifiedUser(String email, String password){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<User> query =  em.createQuery("SELECT u FROM User u WHERE u.email = :userEmail", User.class);
            query.setParameter("userEmail",email);
            User foundUser = query.getSingleResult();

            if (foundUser.verifyPassword(password)){
                return foundUser;
            } else {
                throw new ValidationException(Map.of());
            }
        } catch (NoResultException e) {
            System.out.println("No user was found with the email: " + email);
            return null;
        }
        }

        @Override
        public User addUserRole(int id, Roles role){
        User user = null;
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            user = getUserById(id);

            if (user != null && !user.getRoles().contains(role)) {

                user.addRole(role);
                em.merge(user);
                em.getTransaction().commit();
                System.out.println(user.getUsername() + " has now been assigned the role: " + role);
            } else {
                System.out.println("this user either does not exist or already have this role");
                return null;
            }

        }return user;
    }
    public UserDTO getUserDTOById(int id) {
        User user = null;
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            user = em.find(User.class, id);
            em.getTransaction().commit();
        }
        if(user !=  null) {
            UserDTO userDTO = new UserDTO(user);
            return userDTO;
        }
        else
            System.out.println("User not found with id " + id);
            return null;
    }

    public User getUserById(int id) {
        User user = null;
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            user = em.find(User.class, id);
            em.getTransaction().commit();
        }
        if(user !=  null) {
            return user;
        }
        else
            System.out.println("User not found with id " + id);
        return null;
    }

    public User updateUser(User user)
    {
        User u = null;
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            u = getUserById(user.getId());
        if(u != null){
            u.setUsername(user.getUsername());
            u.setEmail(user.getEmail());
            u.setPassword(user.getPassword());

            em.merge(u);
            em.getTransaction().commit();
            System.out.println("User successfully updated with id " + user.getId());
        }
        else {
            System.out.println("failed to updated with id " + user.getId());
        }
        }
        return u;
    }

    public void deleteUser(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user = em.find(User.class, id);

            if (user == null){
                System.out.println("no user found with id: " + id);
                return;
            }

            em.remove(user);
            em.getTransaction().commit();
            System.out.println("User successfully deleted with id " + id);
        }
    }

    public Set<UserDTO> getAllUsers(){
        Set<User> dbUsers;

        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<User> query =
                    em.createQuery("SELECT u FROM User u", User.class);

            dbUsers = new HashSet<>(query.getResultList());
        }

        Set<UserDTO> dataUsers = dbUsers.stream()
                .map(UserDTO::new)
                .collect(Collectors.toSet());
        return dataUsers;
    }
}
