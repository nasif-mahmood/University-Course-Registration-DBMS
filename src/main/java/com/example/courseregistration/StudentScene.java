package com.example.courseregistration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentScene {

    @FXML
    private Button histButton;

    @FXML
    private Button viewEnrButton;

    @FXML
    private Button addCourses;

    @FXML
    private Button removeCourses;

    @FXML
    private  Text studentText;

    @FXML
    private  Text studentIDText;

    @FXML
    private  Text counselorText;

    @FXML
    private  Text counselorIDText;

    @FXML
    private Button quitButton;

    @FXML
    private Button backButton;

    static String studentName;
    static int studentID;

    public void initStudentScene() {
        studentText.setText("Student:");
        studentIDText.setText("User_ID:");
        counselorText.setText("Counselor:");
        counselorIDText.setText("Counselor_ID:");

        String FName = "", LName="", DPT_NM="", MName="", CFName="", CMName="", CLName="";
        int SID = 0, CID = 0;

        try {
            Application.connection.setAutoCommit(false);

            Statement stmnt = Application.connection.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM USER, STUDENT WHERE Student_ID = User_ID");

            while(rs.next()) {
                int UID = rs.getInt("User_ID");
                String fname = rs.getString("FName");
                String lname = rs.getString("LName");
                String mname = rs.getString("MName");
                String dpt_nm = rs.getString("Dept_Name");
                int cid = rs.getInt("Counselor_ID");

                if(LoginController.id == UID) {
                    SID = UID;
                    FName = fname;
                    LName = lname;
                    DPT_NM = dpt_nm;
                    MName = mname == null ? "" : mname;
                    CID = cid;
                    break;
                }
            }

            stmnt = Application.connection.createStatement();
            rs = stmnt.executeQuery("SELECT * FROM USER");

            while(rs.next()) {
                int UID = rs.getInt("User_ID");
                String fname = rs.getString("FName");
                String lname = rs.getString("LName");
                String mname = rs.getString("MName");

                if(CID == UID) {
                    CFName = fname;
                    CMName = mname == null ? "" : mname;
                    CLName = lname;
                    break;
                }
            }

            Application.connection.commit();
            stmnt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        studentText.setText(studentText.getText() + " " + FName + " " + MName + " " + LName + ": " + DPT_NM);
        studentName = FName + " " + MName + " " + LName;
        studentID = SID;
        studentIDText.setText(studentIDText.getText() + " " + SID);
        counselorText.setText(counselorText.getText() + " " + CFName + " " + CMName  + " " + CLName);
        counselorIDText.setText(counselorIDText.getText() + " " + CID);
    }

    public void quit(ActionEvent e) {
        //((Node) e.getSource()).getScene().getWindow().hide();
        System.exit(1);
    }

    public void seeCourseHistory(ActionEvent e) {
        SceneManager.courseHistoryScene(StudentScene.studentID);
    }

    public void seeEnrolledCourses(ActionEvent e) {
        SceneManager.enrolledCoursesScene(StudentScene.studentID, false);
    }

    public void removeCourses(ActionEvent e) {
        SceneManager.enrolledCoursesScene(StudentScene.studentID, true);
    }

    public void addCourses(ActionEvent e) {
        SceneManager.addCoursesScene(StudentScene.studentID);
    }

    public void goBack(ActionEvent e) {
        SceneManager.goBackScene();
    }

}
