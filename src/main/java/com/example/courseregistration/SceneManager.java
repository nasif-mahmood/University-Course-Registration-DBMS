package com.example.courseregistration;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Stack;

public class SceneManager {

    private static Stage stage;
    private static Scene scene;
    private static Parent root;

    protected static Stack<String> previousScenes = new Stack<>();

    private static String user;
    private static int studentID;
    private static boolean canEdit;

    private SceneManager () {}

    public static void switchUserScene(String user) {
        String sceneFile = user + "scene.fxml";
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(sceneFile));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SceneManager.user = user;
        //stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage = Application.mainStage;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        previousScenes.push("switchuser");

        switch (user) {
            case "admin":
                ((AdminScene)loader.getController()).initAdminScene();
                break;
            case "counselor":
                ((CounselorScene)loader.getController()).initCounsScene();
                break;
            case "dean":
                ((DeanScene)loader.getController()).initDeanScene();
                break;
            case "professor":
                ((ProfessorScene)loader.getController()).initProfScene();
                break;
            case "student":
                ((StudentScene)loader.getController()).initStudentScene();
        }


    }

    public static void courseHistoryScene(int studentID) {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("coursehistory.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        SceneManager.studentID = studentID;

        CourseHistoryScene chsctrller = loader.getController();
        chsctrller.showCourseHistory(studentID);

        //stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage = Application.mainStage;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        previousScenes.push("coursehistory");
    }

    public static void enrolledCoursesScene(int studentID, boolean canEdit) {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("enrolledcourses.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        SceneManager.studentID = studentID;
        SceneManager.canEdit = canEdit;

        EnrolledCoursesScene ecctrller = loader.getController();
        ecctrller.showEnrolledCourses(studentID, canEdit);

        //stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage = Application.mainStage;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        previousScenes.push("enrolledcourses");
    }

    public static void addCoursesScene(int studentID) {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("addcourses.fxml"));
        try {
            root = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        SceneManager.studentID = studentID;

        AddCoursesScene addcctrller = loader.getController();
        addcctrller.addCourses(studentID);

        //stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage = Application.mainStage;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        previousScenes.push("addcourses");
    }

    public static void goBackScene() {
        previousScenes.pop();
        String backToScene = previousScenes.pop();

        switch (backToScene) {
            case "addcourses":
                SceneManager.addCoursesScene(SceneManager.studentID);
                break;
            case "enrollcourses":
                SceneManager.enrolledCoursesScene(SceneManager.studentID, SceneManager.canEdit);
                break;
            case "coursehistory":
                SceneManager.courseHistoryScene(SceneManager.studentID);
                break;
            case "switchuser":
                SceneManager.switchUserScene(SceneManager.user);
                break;
            case "login":
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("loginscene.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(scene);
                stage.show();
                SceneManager.previousScenes.push("login");
        }
    }
}
