package com.example.courseregistration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProfessorScene {
    @FXML
    private Text profText;

    @FXML
    private Text profID;

    @FXML
    private TextField CnameField;

    @FXML
    private TextField CIDField;

    @FXML
    private TextField secIDField;

    @FXML
    private Button search;

    @FXML
    private Button reset;

    @FXML
    private Button quit;

    @FXML
    private Button load;

    public void initProfScene() {
        profText.setText("");
        profID.setText("");

        String FName = "", LName="", MName="", Dpt="";
        int PID = 0;

        try {
            Application.connection.setAutoCommit(false);

            Statement stmnt = Application.connection.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM USER WHERE User_ID = " + LoginController.id);

            while(rs.next()) {
                int UID = rs.getInt("User_ID");
                String fname = rs.getString("FName");
                String lname = rs.getString("LName");
                String mname = rs.getString("MName");
                String dpt = rs.getString("Dept_Name");

                if(LoginController.id == UID) {
                    PID = UID;
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

        profText.setText("Name: " + profText.getText() + " " + FName + " " + MName + " " + LName + " :" + Dpt);
        profID.setText("ID: " + profID.getText() + " " + PID);
    }

    public void quit(ActionEvent e) {
        System.exit(1);
    }

}

