package app.config;


import app.entities.Comment;
import app.entities.Post;
import app.entities.PrivacySettings;
import app.entities.User;
import org.hibernate.cfg.Configuration;

final class EntityRegistry {

    private EntityRegistry() {}

    static void registerEntities(Configuration configuration) {

            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(PrivacySettings.class);
            configuration.addAnnotatedClass(Post.class);
            configuration.addAnnotatedClass(Comment.class);
    }
}