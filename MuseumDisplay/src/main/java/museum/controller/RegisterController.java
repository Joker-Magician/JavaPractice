package museum.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import museum.MainApp;
import museum.dao.UserDAO;
import museum.entity.User;
import museum.utils.AlertUtil;

public class RegisterController {

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    private UserDAO userDAO;

    public RegisterController() { this.userDAO = new UserDAO(); }

    @FXML
    void handleBackToLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String email = emailField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            AlertUtil.showWarning("提示", "请填写所有必填字段");
            return;
        }

        if (!password.equals(confirmPassword)) {
            AlertUtil.showError("错误", "两次输入的密码不一致");
            return;
        }

        if (userDAO.isUsernameExists(username)) {
            AlertUtil.showError("错误", "用户名已存在，请选择其他用户名");
            return;
        }

        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setRole("user");

        if(userDAO.register(newUser)) {
            AlertUtil.showInfo("成功", "注册成功！请登录。");
            MainApp.showLoginScreen();
        } else {
            AlertUtil.showError("错误", "注册失败，请稍后重试");
        }
    }

    @FXML
    void handleRegister(ActionEvent event) {
        MainApp.showLoginScreen();
    }

}
