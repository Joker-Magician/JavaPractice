module com.example.museumdisplay {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.base;
    requires javafx.graphics;


    opens com.example.museumdisplay to javafx.fxml;
    opens museum.controller to javafx.fxml;
    exports museum;
}