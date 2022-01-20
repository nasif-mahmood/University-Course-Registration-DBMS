package com.example.courseregistration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseHistoryScene {
    @FXML
    private TableView table;

    @FXML
    private TableColumn cidCol;

    @FXML
    private TableColumn gradeCol;

    @FXML
    private Text studentName;

    @FXML
    private Button quitButton;

    @FXML
    private Button backButton;

    private ObservableList<CourseHistoryEntry> data = FXCollections.observableArrayList();

    private static int student_ID;

    public void showCourseHistory(int studentID) {
        student_ID = studentID;
        studentName.setText("Course History for " + StudentScene.studentName);
        data.removeAll();
        try {
            Application.connection.setAutoCommit(false);

            Statement stmnt = Application.connection.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT * FROM HAS_TAKEN WHERE Student_ID = " + LoginController.id);

            while(rs.next()) {
                String cid = rs.getString("Course_ID");
                int grd = rs.getInt("Grade");
                data.add(new CourseHistoryEntry(cid, grd));
            }

            Application.connection.commit();
            stmnt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        cidCol.setCellValueFactory(new PropertyValueFactory<CourseHistoryEntry, String>("cid"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<CourseHistoryEntry, String>("grade"));

        table.setItems(data);

        table.setPlaceholder(new Label("No previous courses found"));
    }

    public void quit(ActionEvent e) {
        System.exit(1);
    }

    public void goBack(ActionEvent e) {
        SceneManager.goBackScene();
    }

}
