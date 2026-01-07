package museum.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import museum.MainApp;
import museum.entity.User;
import museum.exception.AuthenticationException; 
import museum.exception.ServiceException;  
import museum.exception.ValidationException;  
import museum.service.UserService;  
import museum.utils.AlertUtil;
import museum.utils.SessionManager;

public class LoginController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    private UserService userService;  // [MODIFIED] 使用 UserService 替代 UserDAO

    public LoginController() {
        this.userService = new UserService();  // [MODIFIED] 初始化 UserService
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // [MODIFIED] 使用 Service 层处理业务逻辑，统一异常处理
        try {
            // 调用 Service 进行登录验证（Service 内部处理验证逻辑）
            User user = userService.login(username, password);
            
            // 登录成功，设置会话
            SessionManager.getInstance().setCurrentUser(user);
            AlertUtil.showInfo("成功", "欢迎回来，" + user.getUserName() + "!");

            // 根据用户角色跳转到不同界面
            if(user.isAdmin()){
                MainApp.showAdminDashboard();
            } else {
                MainApp.showMainDashboard();
            }
            
        } catch (ValidationException e) {
            // 验证异常（如：用户名或密码为空）
            AlertUtil.showWarning("提示", e.getMessage());
        } catch (AuthenticationException e) {
            // 认证异常（如：用户名或密码错误）
            AlertUtil.showError("错误", e.getMessage());
        } catch (ServiceException e) {
            // 其他业务异常
            AlertUtil.showError("错误", "登录失败: " + e.getMessage());
        }
    }

    @FXML
    private void handleRegisterLink() {
        MainApp.showRegisterScreen();
    }
}
