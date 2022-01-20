package com.example.courseregistration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.courseregistration.CourseRegOps.*;

public class LoginController {
    @FXML
    private PasswordField passwordField;
    @FXML
    private Text enterPassField;
    @FXML
    private TextField userField;
    @FXML
    private Text enterUserField;

    @FXML
    private Text badComboFields;

    static int id = 0;

    public void clickedLogin(ActionEvent e) {
        Boolean emptyField = false;
        Boolean badCombo = true;
        String getUsers = "SELECT * FROM USER_ACCOUNT";
        int userID = 0;

        enterPassField.setVisible(false);
        enterUserField.setVisible(false);
        badComboFields.setVisible(false);

        if(userField.getText().isEmpty()) {
            enterUserField.setVisible(true);
            emptyField = true;
        }

        if(passwordField.getText().isEmpty()) {
            enterPassField.setVisible(true);
            emptyField = true;
        }

        if(emptyField) {
            return;
        }

        String enteredUser = userField.getText();
        String hashedPass = PasswordManager.getHash(passwordField.getText());

        try {
            Application.connection.setAutoCommit(false);

            Statement stmnt = Application.connection.createStatement();
            ResultSet rs = stmnt.executeQuery(getUsers);

            while(rs.next()) {
                String user = rs.getString("Username");
                String hashPass = rs.getString("Password_Hash");
                int user_ID = rs.getInt("User_ID");
                if(user.compareTo(enteredUser) == 0 && hashedPass.compareTo(hashPass) == 0) {
                    badCombo = false;
                    userID = user_ID;
                    break;
                }
            }

            Application.connection.commit();
            stmnt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if(badCombo) {
            badComboFields.setVisible(true);
            return;
        }

        id = userID;
        findUser(userID);
    }

    public void clickedQuit(ActionEvent e) {
        System.exit(0);
    }
}