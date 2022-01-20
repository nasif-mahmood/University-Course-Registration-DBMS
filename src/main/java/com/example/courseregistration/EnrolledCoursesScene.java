package com.example.courseregistration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class EnrolledCoursesScene {
    @FXML
    private TableView table;

    @FXML
    private TableColumn courseWSec;

    @FXML
    private TableColumn courseName;

    @FXML
    private TableColumn professor;

    @FXML
    private Text studentName;

    @FXML
    private TextField courseToRemove;

    @FXML
    private Button removeCourse;

    @FXML
    private Text courseNotFound;

    @FXML
    private Button quitButton;

    @FXML
    private Button backButton;

    private ObservableList<EnrolledCoursesEntry> data = FXCollections.observableArrayList();

    protected static Map<Integer, ArrayList<String>> coursesPerStudent = new HashMap<>();
    private static int student_ID;

    public void showEnrolledCourses(int studentID, boolean canEdit) {
        studentName.setText("Enrolled Courses for " + StudentScene.studentName);
        student_ID = studentID;
        data.clear();

        if(canEdit) {
            removeCourse.setVisible(true);
            courseToRemove.setVisible(true);
            removeCourse.setDisable(false);
            courseToRemove.setDisable(false);
        } else {
            removeCourse.setVisible(false);
            courseToRemove.setVisible(false);
            removeCourse.setDisable(true);
            courseToRemove.setDisable(true);
        }

        listEnrolledCoursesSQL(studentID, this.data);

        courseWSec.setCellValueFactory(new PropertyValueFactory<CourseHistoryEntry, String>("course"));
        courseName.setCellValueFactory(new PropertyValueFactory<CourseHistoryEntry, String>("courseName"));
        professor.setCellValueFactory(new PropertyValueFactory<CourseHistoryEntry, String>("professor"));

        table.setItems(data);

        table.setPlaceholder(new Label("No enrolled courses found"));
    }

    public void removeCourse(ActionEvent e) {
        AtomicReference<Boolean> courseRemoved = new AtomicReference<>(false);
        AtomicReference<String> courseToRem = new AtomicReference<>(courseToRemove.getText());
        if(courseToRem.get().contains(".")) {
            courseToRemove.setText(courseToRemove.getText().substring(0, courseToRemove.getText().indexOf(".")));
        }
        if(courseToRem.get().isEmpty()) {
            courseNotFound.setFill(Color.RED);
            courseNotFound.setText("Enter a course");
            courseNotFound.setVisible(true);
            return;
        }
        coursesPerStudent.forEach
                ((sid, courses) -> {
                    if(sid == student_ID){
                        for(String course: courses){
                            if(course.replace(" ", "").toLowerCase().compareTo
                                    (courseToRem.get().replace(" ", "").toLowerCase()) == 0)
                            {
                                try {
                                    Application.connection.setAutoCommit(false);
                                    Statement stmnt = Application.connection.createStatement();

                                    stmnt.executeUpdate("SET @section = " +
                                            "(SELECT EC.Section_ID FROM ENROLLED_COURSES EC," +
                                            " SECTION S WHERE EC.Student_ID = " + student_ID +
                                            " AND EC.Section_ID = S.Section_ID " +
                                            " AND S.Course_ID = '" + course + "')");

                                    int rowsMod =  stmnt.executeUpdate("DELETE FROM ENROLLED_COURSES EC" +
                                            " WHERE EC.Section_ID = @section" );

                                    Application.connection.commit();
                                    stmnt.close();

                                    if(rowsMod > 0) {
                                        courseRemoved.set(true);
                                    }
                                    return;
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                });
        courseNotFound.setVisible(true);
        if(courseRemoved.get()) {
            courseNotFound.setFill(Color.GREEN);
            courseNotFound.setText("Course removed");
            showEnrolledCourses(student_ID, true);
        } else {
            courseNotFound.setFill(Color.RED);
            courseNotFound.setText("Course not found");
        }
    }

    public static void listEnrolledCoursesSQL(int studentID,  ObservableList<EnrolledCoursesEntry> data) {
        try {
            Application.connection.setAutoCommit(false);
            coursesPerStudent.clear();
            Statement stmnt = Application.connection.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT S.Course_ID, S.Section_Number, U.FName, U.MName, U.LName, C.Name " +
                    "FROM ENROLLED_COURSES  EC, SECTION S, USER U, COURSE C " +
                    "WHERE EC.Section_ID = S.Section_ID " +
                    "AND EC.Student_ID = " + studentID +
                    " AND U.User_ID = S.Professor_ID" +
                    " AND S.Course_ID = C.Course_ID");

            while(rs.next()) {
                String Course_ID = rs.getString("S.Course_ID");
                String Sec_No = rs.getString("S.Section_Number");
                String CourseName = rs.getString("C.Name");
                String FName = rs.getString("U.FName");
                String MName = rs.getString("U.MName");
                String LName = rs.getString("U.LName");

                String course = Course_ID + '.' + Sec_No;
                String profName = FName + (MName == null ? " " : " " + MName + " " ) + LName;

                addToStudentCourseMap(student_ID, Course_ID);

                data.add(new EnrolledCoursesEntry(course, CourseName, profName));
            }

            Application.connection.commit();
            stmnt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void addToStudentCourseMap(int key, String val) {
        if(coursesPerStudent.containsKey(key)) {
            coursesPerStudent.get(key).add(val);
        } else {
            ArrayList<String> courses = new ArrayList<>();
            courses.add(val);
            coursesPerStudent.put(key, courses);
        }
    }

    public void quit(ActionEvent e) {
        System.exit(1);
    }

    public void goBack(ActionEvent e) {
        SceneManager.goBackScene();
    }


}
