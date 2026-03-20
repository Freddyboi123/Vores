package app.dao;

import app.entities.Roles;
import app.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;

public class UserDAO {

    private EntityManagerFactory emf;

    public UserDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public User createUser(User user) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
        return user;
    }

    public User getVerifiedUser(String email){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            TypedQuery<User> query =  em.createQuery("SELECT u FROM User u WHERE u.email = :userEmail", User.class);
            query.setParameter("userEmail",email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No user was found with the email: " + email);
            return null;
        }
        }

        public User addRoleToUser(int id, Roles role){
        User user = null;
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            user = getUserById(id);
//&& user.getRoles().contains(role)
            if (user != null && !user.getRoles().contains(role)) {

                user.addRole(role);
                em.merge(user);
                em.getTransaction().commit();
                System.out.println(user.getName() + " has now been assigned the role: " + role);
            } else {
                System.out.println("this user either does not exist or already have this role");
                return null;
            }

        }return user;
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

    public User updateUser(int id, String name, String email, String password)
    {
        User u = null;
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            u = getUserById(id);
        if(u != null){
            u.setName(name);
            u.setEmail(email);
            u.setPassword(password);

            em.merge(u);
            em.getTransaction().commit();
            System.out.println("User successfully updated with id " + id);
        }
        else {
            System.out.println("failed to updated with id " + id);
        }
        }
        return u;
    }

    public void deleteUser(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            User user =     em.find(User.class, id);
            em.remove(user);
            em.getTransaction().commit();
            System.out.println("User successfully deleted with id " + id);
        }
    }
}
