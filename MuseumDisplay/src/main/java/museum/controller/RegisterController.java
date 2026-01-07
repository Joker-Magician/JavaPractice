package museum.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import museum.MainApp;
import museum.exception.DatabaseException; 
import museum.exception.ServiceException; 
import museum.exception.ValidationException;  
import museum.service.UserService;  
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

    private UserService userService;  // [MODIFIED] 使用 UserService 替代 UserDAO

    public RegisterController() {
        this.userService = new UserService();  // [MODIFIED] 初始化 UserService
    }

    @FXML
    void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String email = emailField.getText();

        // [MODIFIED] 使用 Service 层处理所有业务逻辑（验证、检查重复、创建用户）
        try {
            // Service 内部会处理所有验证逻辑
            userService.register(username, password, confirmPassword, email);
            
            // 注册成功
            AlertUtil.showInfo("成功", "注册成功！请登录。");
            MainApp.showLoginScreen();
            
        } catch (ValidationException e) {
            // 验证异常（如：密码不一致、用户名已存在等）
            AlertUtil.showError("错误", e.getMessage());
        } catch (DatabaseException e) {
            // 数据库异常
            AlertUtil.showError("错误", e.getMessage());
        } catch (ServiceException e) {
            // 其他业务异常
            AlertUtil.showError("错误", "注册失败: " + e.getMessage());
        }
    }

    @FXML
    void handleBackToLogin() {
        MainApp.showLoginScreen();
    }

}
