package com.example.databas_gruppupp.FXLoginTest;

import com.example.databas_gruppupp.entities.Address;
import com.example.databas_gruppupp.entities.Customer;
import com.example.databas_gruppupp.entities.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SignUpCredController implements Initializable {
    @FXML
    private Button signupButton;
    @FXML
    private Button loginViewButton;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private TextField addressField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField phoneNoField;
    @FXML
    private TextField passwordField;

    @FXML
    private Text firstNameError;
    @FXML
    private Text lastNameError;
    @FXML
    private Text emailError;
    @FXML
    private Text dateError;
    @FXML
    private Text addressError;
    @FXML
    private Text postalCodeError;
    @FXML
    private Text cityError;
    @FXML
    private Text phoneNoError;
    @FXML
    private Text passwordError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        signupButton.setOnAction(event -> {

            if (dateOfBirth.getValue() != null) {
                if (!firstNameField.getText().trim().isEmpty() && !lastNameField.getText().trim().isEmpty() && !emailField.getText().trim().isEmpty() &&
                        dateOfBirth.getValue().isBefore(LocalDate.now().minusYears(13)) && dateOfBirth.getValue().isAfter(LocalDate.now().minusYears(100)) &&
                        !addressField.getText().trim().isEmpty() && !postalCodeField.getText().trim().isEmpty() && !cityField.getText().trim().isEmpty() &&
                        !phoneNoField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty()) {

                    String addressText = addressField.getText().trim();
                    String houseNumber = addressText.substring(addressText.indexOf(" ") + 1, addressText.length());
                    addressText = addressText.substring(0, addressText.indexOf(" "));

                    Address address = new Address(addressText, houseNumber, postalCodeField.getText(), cityField.getText());
                    User user = new User(emailField.getText().trim(), passwordField.getText());
                    Customer customer = new Customer(firstNameField.getText().trim(), lastNameField.getText().trim(), dateOfBirth.getValue(),
                            phoneNoField.getText().trim(), address, user);
                    user.setCustomer(customer);

                    try {
                        DBUtils.signUpUser(event, user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // if any field is empty or WRONG

            firstNameError.setOpacity(firstNameField.getText().trim().isEmpty() ? 1 : 0);
            lastNameError.setOpacity(lastNameField.getText().trim().isEmpty() ? 1 : 0);

            if (dateOfBirth.getValue() == null) {
                dateError.setText("*");
                dateError.setOpacity(1);
            } else if (dateOfBirth.getValue().isAfter(LocalDate.now().minusYears(13))) {
                dateError.setText("* Ålder inte giltig");
                dateError.setOpacity(1);
            } else {
                dateError.setOpacity(0);
            }
            addressError.setOpacity(addressField.getText().trim().isEmpty() ? 1 : 0);
            postalCodeError.setOpacity(postalCodeField.getText().trim().isEmpty() ? 1 : 0);
            cityError.setOpacity(cityField.getText().trim().isEmpty() ? 1 : 0);
            phoneNoError.setOpacity(phoneNoField.getText().trim().isEmpty() ? 1 : 0);
            passwordError.setOpacity(passwordField.getText().trim().isEmpty() ? 1 : 0);
        });

        loginViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.changeScene(actionEvent, "/fxml/login-view.fxml", "Logga in", null);
            }
        });
    }
}