module com.example.test10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.test10 to javafx.fxml;
    exports com.example.test10;
    
    opens Java课程实验FX to javafx.fxml;
    exports Java课程实验FX;
}