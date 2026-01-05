package museum;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import museum.constants.AppConstants; // [ADDED] 导入常量类

import java.io.IOException;

public class MainApp extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("中华古建筑/非遗数字博物馆");
        showLoginScreen();
    }

    public static void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(AppConstants.FXML_LOGIN)); // [MODIFIED] 使用常量
            Parent root = loader.load();
            Scene scene = new Scene(root, AppConstants.LOGIN_WIDTH, AppConstants.LOGIN_HEIGHT); // [MODIFIED] 使用常量
            scene.getStylesheets().add(MainApp.class.getResource(AppConstants.CSS_MAIN).toExternalForm()); // [MODIFIED] 使用常量
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRegisterScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(AppConstants.FXML_REGISTER)); // [MODIFIED] 使用常量
            Parent root = loader.load();
            Scene scene = new Scene(root, AppConstants.LOGIN_WIDTH, AppConstants.LOGIN_HEIGHT); // [MODIFIED] 使用常量
            scene.getStylesheets().add(MainApp.class.getResource(AppConstants.CSS_MAIN).toExternalForm()); // [MODIFIED] 使用常量
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMainDashboard(){
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(AppConstants.FXML_MAIN_DASHBOARD)); // [MODIFIED] 使用常量
            Parent root = loader.load();
            Scene scene = new Scene(root, AppConstants.DASHBOARD_WIDTH, AppConstants.DASHBOARD_HEIGHT); // [MODIFIED] 使用常量
            scene.getStylesheets().add(MainApp.class.getResource(AppConstants.CSS_MAIN).toExternalForm()); // [MODIFIED] 使用常量
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAdminDashboard(){
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(AppConstants.FXML_ADMIN_DASHBOARD)); // [MODIFIED] 使用常量
            Parent root = loader.load();
            Scene scene = new Scene(root, AppConstants.DASHBOARD_WIDTH, AppConstants.DASHBOARD_HEIGHT); // [MODIFIED] 使用常量
            scene.getStylesheets().add(MainApp.class.getResource(AppConstants.CSS_MAIN).toExternalForm()); // [MODIFIED] 使用常量
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
