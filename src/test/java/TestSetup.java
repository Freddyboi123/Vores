import app.config.Hibernate.HibernateBaseProperties;
import app.config.Hibernate.HibernateConfig;
import app.config.Hibernate.HibernateEmfBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Properties;

public class TestSetup {
    EntityManagerFactory emf;
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");


//    @BeforeAll
//    public void setup() {
//        Properties props = HibernateBaseProperties.createBase();
//        props.put("hibernate.connection.url", postgres.getJdbcUrl());
//        props.put("hibernate.connection.username", postgres.getUsername());
//        props.put("hibernate.connection.password", postgres.getPassword());
//        props.put("hibernate.hbm2ddl.auto", "create-drop");
//        emf = HibernateEmfBuilder.build(props);
//    }
}
