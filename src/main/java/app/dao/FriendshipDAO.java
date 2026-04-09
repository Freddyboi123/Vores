package app.dao;

import app.entities.Friendship;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class FriendshipDAO {

    private EntityManagerFactory emf;

    public FriendshipDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void sendRequest(Long requesterId, Long addresseeId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Friendship f = new Friendship();
            f.setRequesterId(requesterId);
            f.setAddresseeId(addresseeId);
            f.setStatus(Friendship.Status.PENDING);
            f.setCreatedAt(LocalDateTime.now());

            em.persist(f);
            em.getTransaction().commit();
        }
    }

    public void acceptRequest(long requestId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Friendship f = em.find(Friendship.class, requestId);
            f.setStatus(Friendship.Status.ACCEPTED);

            em.getTransaction().commit();
        }
    }

    //Todo convert to int
    public List<Long> getFriends(long userId) {
        try (EntityManager em = emf.createEntityManager()) {

            List<Friendship> result = em.createQuery(
                            "SELECT f FROM Friendship f WHERE f.status = :status AND " +
                                    "(f.requesterId = :id OR f.addresseeId = :id)", Friendship.class)
                    .setParameter("status", Friendship.Status.ACCEPTED)
                    .setParameter("id", userId)
                    .getResultList();

            return result.stream()
                    .map(f -> f.getRequesterId().equals(userId)
                            ? f.getAddresseeId()
                            : f.getRequesterId())
                    .toList();
        }
    }

    public List<Friendship> getPendingRequests(long userId) {
        try (EntityManager em = emf.createEntityManager()) {
            List<Friendship> result = em.createQuery(
                            "SELECT f FROM Friendship f WHERE f.status = :status AND f.addresseeId = :id",
                            Friendship.class)
                    .setParameter("status", Friendship.Status.PENDING)
                    .setParameter("id", userId)
                    .getResultList();

            return result;
        }
    }
}