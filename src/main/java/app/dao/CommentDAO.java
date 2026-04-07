package app.dao;

import app.dto.CommentDTO;
import app.entities.Comment;
import app.entities.Post;
import app.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<CommentDTO> getAllCommentsFromPost(int post_id){
        Set<Comment> dataComments;

        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Comment> query =
                    em.createQuery("SELECT c FROM Comment c WHERE c.post.postId =:post_id", Comment.class);
            query.setParameter("post_id",post_id);

            dataComments = new HashSet<>(query.getResultList());
        }

        Set<CommentDTO> postComments = dataComments.stream()
                .map(CommentDTO::new)
                .collect(Collectors.toSet());
        return postComments;
    }
}

