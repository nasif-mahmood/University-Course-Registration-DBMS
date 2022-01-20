package com.example.courseregistration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeanScene {

    @FXML
    private Text deanname;

    @FXML
    private Text deanid;

    @FXML
    private TextField nameField;

    @FXML
    private TextField IDField;

    @FXML
    private TextField userTypeField;

    @FXML
    private Button modCourse;

    @FXML
    private Button modStndCouns;

    @FXML
    private Button load;

    @FXML
    private Button modUser;

    @FXML
    private Button editCourses;

    @FXML
    private Button quit;

    public void initDeanScene() {
        deanname.setText("");
        deanid.setText("");

        String FName = "", LName="", MName="", Dpt="";
        int DID = LoginController.id;

        try {
            Application.connection.setAutoCommit(false);

            Statement stmnt = Application.connection.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM USER WHERE User_ID = " + LoginController.id);

            while(rs.next()) {
                String fname = rs.getString("FName");
                String lname = rs.getString("LName");
                String mname = rs.getString("MName");
                String dpt = rs.getString("Dept_Name");

                if(LoginController.id == DID) {
                    FName = fname;
                    LName = lname;
                    MName = mname == null ? "" : mname;
                    Dpt = dpt;
                    break;
                }
            }

            Application.connection.commit();
            stmnt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        deanname.setText("Name: " + deanname.getText() + " " + FName + " " + MName + " " + LName + " :" + Dpt);
        deanid.setText("ID: " + deanid.getText() + " " + DID);
    }

    public void quit(ActionEvent e) {
        System.exit(1);
    }
}
