package com.example.courseregistration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CounselorScene {

    @FXML
    private Text counselorText;

    @FXML
    private Text counselorID;

    @FXML
    private TextField nameField;

    @FXML
    private TextField idField;

    @FXML
    private Button search;

    @FXML
    private Button quit;

    @FXML
    private Button load;

    public void initCounsScene() {
        counselorText.setText("");
        counselorID.setText("");

        String FName = "", LName="", MName="";
        int CID = 0;

        try {
            Application.connection.setAutoCommit(false);

            Statement stmnt = Application.connection.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM USER WHERE User_ID = " + LoginController.id);

            while(rs.next()) {
                int UID = rs.getInt("User_ID");
                String fname = rs.getString("FName");
                String lname = rs.getString("LName");
                String mname = rs.getString("MName");

                if(LoginController.id == UID) {
                    CID = UID;
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

        counselorText.setText("Name: " + counselorText.getText() + " " + FName + " " + MName + " " + LName);
        counselorID.setText("ID: " + counselorID.getText() + " " + CID);
    }

    public void quit(ActionEvent e) {
        System.exit(1);
    }
}
