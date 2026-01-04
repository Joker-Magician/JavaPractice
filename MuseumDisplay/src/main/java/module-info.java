module com.example.museumdisplay {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.example.museumdisplay;


    opens com.example.museumdisplay to javafx.fxml;
    exports com.example.museumdisplay;
}