package app.config.Hibernate;

import app.utils.Utils;
import jakarta.persistence.EntityManagerFactory;

import java.util.Properties;

public final class HibernateConfig {

    private static volatile EntityManagerFactory emf;

    private HibernateConfig() {}

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            synchronized (HibernateConfig.class) {
                if (emf == null) {
                    emf = HibernateEmfBuilder.build(buildProps());
                }
            }
        }
        return emf;
    }

    private static Properties buildProps() {
        Properties props = HibernateBaseProperties.createBase();

        // Teaching-friendly default - change to update in production
        props.put("hibernate.hbm2ddl.auto", "create");

        if (System.getenv("DEPLOYED") != null) {
            setDeployedProperties(props);
        } else {
            setDevProperties(props);
        }
        return props;
    }

    private static void setDeployedProperties(Properties props) {
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println("DB_NAME = " + System.getenv("DB_NAME"));
        System.out.println("DB_URL = " + System.getenv("DB_URL"));
        System.out.println("DB_HOST = " + System.getenv("DB_HOST"));
        System.out.println("DB_PORT = " + System.getenv("DB_PORT"));

        String dbName = System.getenv("DB_NAME");
        props.setProperty("hibernate.connection.url", "jdbc:postgresql://db:5432/" + dbName);
        props.setProperty("hibernate.connection.username", System.getenv("DB_USERNAME"));
        props.setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"));
    }

    private static void setDevProperties(Properties props) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("DB_NAME = " + System.getenv("DB_NAME"));
        System.out.println("DB_URL = " + System.getenv("DB_URL"));
        System.out.println("DB_HOST = " + System.getenv("DB_HOST"));
        System.out.println("DB_PORT = " + System.getenv("DB_PORT"));
        String dbName = Utils.getPropertyValue("DB_NAME", "config.properties");
        String username = Utils.getPropertyValue("DB_USERNAME", "config.properties");
        String password = Utils.getPropertyValue("DB_PASSWORD", "config.properties");

        props.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/" + dbName);
        props.put("hibernate.connection.username", username);
        props.put("hibernate.connection.password", password);
    }

}