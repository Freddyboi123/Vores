package app.config.Hibernate;


import app.entities.*;
import org.hibernate.cfg.Configuration;

final class EntityRegistry {

    private EntityRegistry() {}

    static void registerEntities(Configuration configuration) {
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(PrivacySettings.class);
            configuration.addAnnotatedClass(Post.class);
            configuration.addAnnotatedClass(Comment.class);
            configuration.addAnnotatedClass(Friendship.class);
    }
}