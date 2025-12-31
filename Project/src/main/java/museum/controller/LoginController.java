package museum.controller;

import museum.MainApp;
import museum.dao.UserDAO;
import museum.model.User;
import museum.util.AlertUtil;
import museum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserDAO userDAO;

    public LoginController() {
        this.userDAO = new UserDAO();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AlertUtil.showWarning("提示", "请输入用户名和密码");
            return;
        }

        User user = userDAO.login(username, password);

        if (user != null) {
            SessionManager.getInstance().setCurrentUser(user);
            AlertUtil.showInfo("成功", "欢迎回来, " + user.getUsername() + "!");
            
            if (user.isAdmin()) {
                MainApp.showAdminDashboard();
            } else {
                MainApp.showMainDashboard();
            }
        } else {
            AlertUtil.showError("错误", "用户名或密码不正确");
        }
    }

    @FXML
    private void handleRegisterLink() {
        MainApp.showRegisterScreen();
    }
}
