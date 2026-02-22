package app.dao;

import app.entities.Post;
import app.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PostDAO {

    EntityManagerFactory emf;
    public PostDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Post createPost(Post post) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(post);
            em.getTransaction().commit();
        }
        return post;
    }

    public Post getPost(int id) {
        Post post = null;
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            post = em.find(Post.class, id);
            em.getTransaction().commit();
        }
        if(post !=  null) {
            return post;
        }
        else
            System.out.println("Post not found with id " + id);
        return null;
    }

    public Post updatePost(int id, String postContent)
    {
        Post p = null;
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            p = getPost(id);
            if(p != null){
                p.setPostContent(postContent);
                em.merge(p);
                em.getTransaction().commit();
                System.out.println("Post successfully updated with id " + id);
            }
            else {
                System.out.println("failed to updated post with id " + id);
            }
        }
        return p;
    }

    public void deletePost(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Post post =  em.find(Post.class, id);
            em.remove(post);
            em.getTransaction().commit();
            System.out.println("Post successfully deleted with id " + id);
        }
    }
}

