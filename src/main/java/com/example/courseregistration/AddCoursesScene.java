package com.example.courseregistration;

import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static com.example.courseregistration.EnrolledCoursesScene.coursesPerStudent;
import static com.example.courseregistration.EnrolledCoursesScene.listEnrolledCoursesSQL;

public class AddCoursesScene {
    @FXML
    private TableView availCourses;

    @FXML
    private TableColumn availCourse_ID;

    @FXML
    private TableColumn availCourse;

    @FXML
    private TableColumn availSection;

    @FXML
    private TableColumn availProf;

    @FXML
    private TableColumn availMaxStudents;

    @FXML
    private TableColumn availCurrStudents;

    @FXML
    private TableView enrollCourses;

    @FXML
    private TableColumn enrollCourse_ID;

    @FXML
    private TableColumn enrollCourseName;

    @FXML
    private TableColumn enrollProf;

    @FXML
    private TextField addSecIDField;

    @FXML
    private Text transMessage;

    @FXML
    private Button addSecIDButton;

    @FXML
    private Button quitButton;

    @FXML
    private Button backButton;

    private static double availTableCellHeight = 25;
    private static double availTableColNameHeight = 27;
    private final static Tooltip requisites = new Tooltip();
    private int prevRowSelected = -1;


    private EventHandler<MouseEvent> handler = event -> {
        String coreqs = "Co-requisites: ";
        String prereqs = "Prerequisites: ";
        Boolean hasPrereq = false;
        Boolean lastNull = false;
        String courseSel = "";

        double heightMouseRelToTable = event.getY() - availTableColNameHeight;
        int rowSelected = heightMouseRelToTable < 0 ? -1
                : (int)(heightMouseRelToTable / availTableCellHeight);

        if(prevRowSelected == rowSelected) {
            return;
        }

        prevRowSelected = rowSelected;
        courseSel = (String)availCourse.getCellData(rowSelected);

        if(courseSel == null) {
            availCourses.setTooltip(null);
            return;
        }

        availCourses.setTooltip(requisites);

        try {
            Application.connection.setAutoCommit(false);
            Statement stmnt = Application.connection.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT Prerequisite_ID FROM PREREQUISITES" +
                    " WHERE Course_ID = '" + courseSel + "'");

            while(rs.next()) {
                hasPrereq = true;
                prereqs += rs.getString("Prerequisite_ID") + ", ";
            }

            prereqs = hasPrereq ? prereqs.substring(0, prereqs.length() - 2) : prereqs;

            stmnt = Application.connection.createStatement();
            rs = stmnt.executeQuery("SELECT Corequisite_ID FROM COURSE" +
                    " WHERE Course_ID = '" + courseSel + "'");

            while(rs.next()) {
                String str = rs.getString("Corequisite_ID");
                if(str == null) {
                    lastNull = true;
                } else {
                    coreqs += str + ", ";
                    lastNull = false;
                }
            }

            coreqs = lastNull ? coreqs : coreqs.substring(0, coreqs.length() - 2);

            stmnt.close();
            Application.connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        requisites.setText(courseSel + "\n\n• " + prereqs + "\n• " + coreqs);
    };

    private ObservableList<EnrolledCoursesEntry> enrolledEntries = FXCollections.observableArrayList();
    private ObservableList<AvailCoursesEntry> availEntries = FXCollections.observableArrayList();

    private static int student_ID;

    public void addCourses(int studentID) {
        requisites.setShowDelay(Duration.ZERO);
        transMessage.setVisible(false);
        student_ID = studentID;
        showEnrolledCourses(studentID);
        listAvailCourses();
    }

    public void showEnrolledCourses(int studentID) {
        this.enrolledEntries.clear();

        listEnrolledCoursesSQL(studentID, this.enrolledEntries);

        enrollCourse_ID.setCellValueFactory(new PropertyValueFactory<CourseHistoryEntry, String>("course"));
        enrollCourseName.setCellValueFactory(new PropertyValueFactory<CourseHistoryEntry, String>("courseName"));
        enrollProf.setCellValueFactory(new PropertyValueFactory<CourseHistoryEntry, String>("professor"));

        enrollCourses.setItems(enrolledEntries);

        enrollCourses.setPlaceholder(new Label("No enrolled courses found"));
    }

    public void listAvailCourses() {
        this.availEntries.clear();
        listAvailCoursesSQL(this.availEntries);

        availCourse_ID.setCellValueFactory(new PropertyValueFactory<AvailCoursesEntry, String>("cid"));
        availCourse.setCellValueFactory(new PropertyValueFactory<AvailCoursesEntry, String>("course"));
        availSection.setCellValueFactory(new PropertyValueFactory<AvailCoursesEntry, String>("section"));
        availCurrStudents.setCellValueFactory(new PropertyValueFactory<AvailCoursesEntry, String>("currentStudents"));
        availMaxStudents.setCellValueFactory(new PropertyValueFactory<AvailCoursesEntry, String>("maxStudents"));
        availProf.setCellValueFactory(new PropertyValueFactory<AvailCoursesEntry, String>("professor"));

        availCourses.setItems(this.availEntries);

        availCourses.setPlaceholder(new Label("No available courses found"));

    }

    public static void listAvailCoursesSQL(ObservableList<AvailCoursesEntry> availEntries) {
        try {
            Application.connection.setAutoCommit(false);

            Statement stmnt = Application.connection.createStatement();
            ResultSet rs = stmnt.executeQuery("SELECT S.Section_ID, S.Section_Number, S.Course_ID, S.Max_students, " +
                    "S.Current_students_taking, U.FName, U.MName, U.LName" +
                    " FROM SECTION S, USER U" +
                    " WHERE S.Professor_ID = U.User_ID");

            while(rs.next()) {
                int Course_ID = rs.getInt("S.Section_ID");
                String Course = rs.getString("S.Course_ID");
                String Section = rs.getString("S.Section_Number");
                int MaxStudents = rs.getInt("S.Max_students");
                int CurrentStudents = rs.getInt("S.Current_students_taking");
                String FName = rs.getString("U.FName");
                String MName = rs.getString("U.MName");
                String LName = rs.getString("U.LName");

                String profName = FName + (MName == null ? " " : " " + MName + " " ) + LName;

                availEntries.add(new AvailCoursesEntry(Course_ID, CurrentStudents, MaxStudents, Course, Section, profName ));
            }

            Application.connection.commit();
            stmnt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addCourse(ActionEvent e) {
        String secToAdd = addSecIDField.getText();
        if (!secToAdd.matches("\\d+")) {
            transMessage.setText("Unable to add course");
            transMessage.setFill(Color.RED);
            transMessage.setVisible(true);
            return;
        }

        Statement stmnt = null;
        try {
            Application.connection.setAutoCommit(false);

            stmnt = Application.connection.createStatement();

            int result = stmnt.executeUpdate("INSERT INTO ENROLLED_COURSES VALUES(" +
                    student_ID + "," + secToAdd + ")");

            if(result > 0) {
                transMessage.setText("Successfully added course");
                transMessage.setFill(Color.GREEN);
                transMessage.setVisible(true);
                listAvailCourses();
                showEnrolledCourses(student_ID);
            }

            Application.connection.commit();
            stmnt.close();
        } catch (SQLException s) {
            if(s.getClass() != java.sql.SQLIntegrityConstraintViolationException.class &&
                    !s.getMessage().contains("COURSE_STUDENT_LIMIT")){
                s.printStackTrace();
                try {
                    Application.connection.commit();
                    stmnt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                return;
            }

            transMessage.setFill(Color.RED);
            transMessage.setVisible(true);

            try {
                if(s.getMessage() == null){
                    transMessage.setText("Unable to add course");
                    Application.connection.commit();
                    stmnt.close();
                    return;
                } else if (s.getMessage().contains("foreign key")) {
                    transMessage.setText("Unable to add course:\n• Course does not exist");
                    Application.connection.commit();
                    stmnt.close();
                    return;
                } else {
                    transMessage.setText("Unable to add course:");
                }

                stmnt = Application.connection.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT Current_students_taking, Max_students" +
                        " FROM SECTION WHERE Section_ID = " + secToAdd);
                int currentStudents = 0;
                int maxStudents = 1;
                while(rs.next()) {
                    currentStudents = rs.getInt("Current_students_taking");
                    maxStudents = rs.getInt("Max_students");
                }

                if(currentStudents >= maxStudents) {
                    transMessage.setText(transMessage.getText() + "\n• Section is full");
                }

                stmnt = Application.connection.createStatement();
                rs = stmnt.executeQuery("SELECT * FROM" +
                        " ENROLLED_COURSES EC, SECTION S" +
                        " WHERE EC.Section_ID = S.Section_ID" +
                        " AND EC.Student_ID = " + student_ID +
                        " AND S.Course_ID = " + "(SELECT S.Course_ID FROM SECTION S WHERE S.Section_ID = " + secToAdd + ")");

                Boolean existingCourse = false;

                while(rs.next()) {
                    existingCourse = true;
                }

                if(existingCourse) {
                    transMessage.setText(transMessage.getText() + "\n• Already in course");
                }

                stmnt = Application.connection.createStatement();
                rs = stmnt.executeQuery("SELECT * FROM SECTION S, COURSE C" +
                        " WHERE S.Section_ID = " + secToAdd +
                        " AND S.Course_ID = C.Course_ID" +
                        " AND C.Corequisite_ID NOT IN (SELECT S.Course_ID FROM SECTION S, ENROLLED_COURSES EC" +
                                " WHERE EC.Student_ID = " + student_ID + " AND EC.Section_ID = S.Section_ID)");

                Boolean coreqsNotMet = false;

                while(rs.next()) {
                    coreqsNotMet = true;
                }

                if(coreqsNotMet) {
                    transMessage.setText(transMessage.getText() + "\n• Co-requisites not met");
                }

                stmnt = Application.connection.createStatement();
                rs = stmnt.executeQuery("SELECT * FROM SECTION S, PREREQUISITES P, HAS_TAKEN HT" +
                        " WHERE S.Section_ID = " + secToAdd +
                        " AND S.Course_ID = P.Course_ID" +
                        " AND HT.Student_ID = " + student_ID +
                        " AND P.Prerequisite_ID != HT.Course_ID");

                Boolean prereqsNotMet = false;

                while(rs.next()) {
                    prereqsNotMet = true;
                }

                if(prereqsNotMet) {
                    transMessage.setText(transMessage.getText() + "\n• Prerequisites not met");
                }

                Application.connection.commit();
                stmnt.close();
            } catch(SQLException er) {
                er.printStackTrace();
            }
        }
    }

    public void addMoveWithinTableListener(MouseEvent e) {
        availCourses.addEventHandler(MouseEvent.MOUSE_MOVED, handler);
    }

    public void removeMoveWithinTableListener(MouseEvent e) {
        availCourses.removeEventHandler(MouseEvent.MOUSE_MOVED, handler);
    }

    public void quit(ActionEvent e) {
        System.exit(1);
    }

    public void goBack(ActionEvent e) {
        SceneManager.goBackScene();
    }

}
