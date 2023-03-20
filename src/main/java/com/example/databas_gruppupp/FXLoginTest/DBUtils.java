package com.example.databas_gruppupp.FXLoginTest;

import com.example.databas_gruppupp.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, User user) {
        Parent root = null;

        if (user != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInfo(user.getCustomer());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, User newUser) {

        String sql = "FROM User WHERE email = '" + newUser.getEmail() + "'";
        Query query;
        List<User> result;
        Session session;

        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            query = session.createQuery(sql);
            result = query.getResultList();

            if (result.size() != 0) {
                System.out.println("Mailaddressen är redan registrerad!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Felmeddelande");
                alert.setContentText("Mailaddressen är redan registrerad!");
                alert.show();
            } else {
                session.persist(newUser);
                session.getTransaction().commit();
                changeScene(event, "/fxml/logged-in.fxml", "Welcome", newUser);
            }
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logInUser(ActionEvent event, User newUser) {

        String hql = "FROM User WHERE email = '" + newUser.getEmail() + "'";
        Query query;
        List<User> result;
        Session session;

        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            query = session.createQuery(hql);
            result = query.getResultList();

            if (result.size() == 0) {
                System.out.println("QTEST: " + query.getQueryString());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Felmeddelande");
                alert.setContentText("Mailaddressen kunde inte hittas. Registrera ett konto och försök igen!");
                alert.show();
            } else {
                User userDatabase = result.get(0);
                if (userDatabase.getPassword().equals(newUser.getPassword())) {
                    changeScene(event, "/fxml/logged-in.fxml", "Welcome!", userDatabase);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Det angivna lösenordet är fel.");
                    alert.show();
                }
            }
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}