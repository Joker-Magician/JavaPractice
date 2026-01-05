package museum.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import museum.MainApp;
import museum.dao.UserDAO;
import museum.entity.User;
import museum.utils.AlertUtil;
import museum.utils.SessionManager;

import java.awt.event.ActionEvent;

public class LoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    private UserDAO userDAO;

    public LoginController() { this.userDAO = new UserDAO(); }

    @FXML
    private void loginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AlertUtil.showWarning("提示", "请输入用户名和密码");
            return;
        }

        User user = userDAO.login(username, password);

        if(user != null){
            SessionManager.getInstance().setCurrentUser(user);
            AlertUtil.showInfo("成功", "欢迎回来，" + user.getUserName() + "!");

            if(user.isAdmin()){
                MainApp.showAdminDashboard();
            }else {
                MainApp.showMainDashboard();
            }
        }else{
            AlertUtil.showError("错误", "用户名或密码不正确");
        }
    }

    @FXML
    private void handleRegisterLink() { MainApp.showRegisterScreen(); }
}
