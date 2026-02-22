package app.dao;

import app.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

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

    public User getUser(int id) {
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
            u = getUser(id);
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
