package Java课程实验FX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpacecraftApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test10/Spacecraft.fxml"));
        Parent root = loader.load();
        
        // Set up the scene
        Scene scene = new Scene(root, 850, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("神舟飞船管理系统");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
