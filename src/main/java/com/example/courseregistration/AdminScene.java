package com.example.courseregistration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminScene {

    @FXML
    private Text adminname;

    @FXML
    private Text adminid;

    @FXML
    private TextField nameField;

    @FXML
    private TextField IDField;

    @FXML
    private TextField userTypeField;

    @FXML
    private Button load;

    @FXML
    private Button modUser;

    @FXML
    private Button editUsers;

    @FXML
    private Button editAccounts;

    @FXML
    private Button quit;

    public void initAdminScene() {
        adminname.setText("");
        adminid.setText("");

        String FName = "", LName="", MName="";
        int AID = LoginController.id;

        try {
            Application.connection.setAutoCommit(false);

            Statement stmnt = Application.connection.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM USER WHERE User_ID = " + LoginController.id);

            while(rs.next()) {
                String fname = rs.getString("FName");
                String lname = rs.getString("LName");
                String mname = rs.getString("MName");

                if(LoginController.id == AID) {
                    FName = fname;
                    LName = lname;
                    MName = mname == null ? "" : mname;
                    break;
                }
            }

            Application.connection.commit();
            stmnt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        adminname.setText("Name: " + adminname.getText() + " " + FName + " " + MName + " " + LName);
        adminid.setText("ID: " + adminid.getText() + " " + AID);
    }

    public void quit(ActionEvent e) {
        System.exit(1);
    }

}
