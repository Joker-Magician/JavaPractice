module com.example.museumdisplay {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.base;
    requires com.example.museumdisplay;
    requires javafx.graphics;


    opens com.example.museumdisplay to javafx.fxml;
    exports com.example.museumdisplay;
}