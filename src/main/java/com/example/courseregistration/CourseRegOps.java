package com.example.courseregistration;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.courseregistration.SceneManager.*;

public class CourseRegOps {
    public static void findUser(int userID) {
        String queryAdmins = "SELECT * FROM ADMINISTRATOR";
        String queryCouns = "SELECT * FROM COUNSELOR";
        String queryDeans = "SELECT * FROM DEAN";
        String queryProfs = "SELECT * FROM PROFESSOR";
        String queryStudent = "SELECT * FROM STUDENT";
        String typeUser = "";

        try {
            Application.connection.setAutoCommit(false);

            Statement stmnt = Application.connection.createStatement();
            ResultSet rs = stmnt.executeQuery(queryAdmins);

            while (rs.next()) {
                int admin_id = rs.getInt("Admin_ID");
                if (userID == admin_id) {
                    typeUser = "admin";
                }
            }

            stmnt = Application.connection.createStatement();
            rs = stmnt.executeQuery(queryCouns);

            while (rs.next()) {
                int counselor_id = rs.getInt("Counselor_ID");
                if (userID == counselor_id) {
                    typeUser = "counselor";
                }
            }

            stmnt = Application.connection.createStatement();
            rs = stmnt.executeQuery(queryDeans);

            while (rs.next()) {
                int dean_id = rs.getInt("Dean_ID");
                if (userID == dean_id) {
                    typeUser = "dean";
                }
            }

            stmnt = Application.connection.createStatement();
            rs = stmnt.executeQuery(queryProfs);

            while (rs.next()) {
                int prof_id = rs.getInt("Professor_ID");
                if (userID == prof_id) {
                    typeUser = "professor";
                }
            }

            stmnt = Application.connection.createStatement();
            rs = stmnt.executeQuery(queryStudent);

            while (rs.next()) {
                int student_id = rs.getInt("Student_ID");
                if (userID == student_id) {
                    typeUser = "student";
                }
            }

            Application.connection.commit();
            stmnt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        switchUserScene(typeUser);
    }

}