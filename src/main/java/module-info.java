module com.example.courseregistration {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.courseregistration to javafx.fxml;
    exports com.example.courseregistration;
}