package com.example.courseregistration;

import java.sql.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    protected static Connection connection;
    protected static Stage mainStage;

    public static void main(String[] args) {
        connection = DatabaseAPI.makeConnection();
        launch();

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("loginscene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Course Registration");
        stage.setScene(scene);
        mainStage = stage;
        stage.show();

        SceneManager.previousScenes.push("login");
    }
}