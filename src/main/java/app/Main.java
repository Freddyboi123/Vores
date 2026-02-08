package app;

import app.config.HibernateConfig;
import app.dao.UserDAO;
import app.entities.User;
import jakarta.persistence.EntityManagerFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO(emf);

        User user = User.builder().name("Frederik").email("frederik@gmail.com").password("12345").build();
        userDAO.createUser(user);
        System.out.println(userDAO.getUser(1));
        userDAO.updateUser(user.getId(),"Frederik","frederik@outlook.dk","12345");
        System.out.println(userDAO.getUser(1));

    }
}