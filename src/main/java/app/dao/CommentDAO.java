package app.dao;

import app.entities.Comment;
import app.entities.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class CommentDAO {
    EntityManagerFactory emf;
    public CommentDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Comment createComment(Comment comment) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(comment);
            em.getTransaction().commit();
        }
        return comment;
    }

    public Comment getComment(int id) {
        Comment comment = null;
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            comment = em.find(Comment.class, id);
            em.getTransaction().commit();
        }
        if(comment !=  null) {
            return comment;
        }
        else
            System.out.println("Post not found with id " + id);
        return null;
    }

    public Comment updateComment(int id, String postContent)
    {
        Comment c = null;
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            c = getComment(id);
            if(c != null){
                c.setCommentContent(postContent);
                em.merge(c);
                em.getTransaction().commit();
                System.out.println("Comment successfully updated with id " + id);
            }
            else {
                System.out.println("failed to updated Comment with id " + id);
            }
        }
        return c;
    }

    public void deleteComment(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Comment comment =  em.find(Comment.class, id);
            em.remove(comment);
            em.getTransaction().commit();
            System.out.println("Comment successfully deleted with id " + id);
        }
    }
}

