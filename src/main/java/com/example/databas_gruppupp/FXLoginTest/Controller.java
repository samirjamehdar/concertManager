package com.example.databas_gruppupp.FXLoginTest;

import com.example.databas_gruppupp.CreatingDummyData;
import com.example.databas_gruppupp.entities.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button loginButton;
    @FXML
    private Button signupSceneButton;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.logInUser(event, new User(emailField.getText(), passwordField.getText()));

            }
        });
        signupSceneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/fxml/sign-up-credentials.fxml", "Skapa Konto", null);
            }
        });
    }
    public void dummyData(){

        try {
            String databaseName = "wigellsconsert";
            String userName = "root";
            String password = "%HQz~7PAOj%JynV0pM5w";

            String url = "jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull";
            Connection connection = DriverManager.getConnection(url,userName, password);

            String sql = "DROP DATABASE IF EXISTS " + databaseName;
            String sql2 = "CREATE DATABASE " + databaseName;

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.executeUpdate(sql2);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        CreatingDummyData.addData();
    }
}