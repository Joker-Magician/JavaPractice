package museum.service;

import museum.dao.UserDAO;
import museum.entity.User;
import museum.exception.AuthenticationException;
import museum.exception.DatabaseException;
import museum.exception.ValidationException;

/**
 * 用户服务类
 * 负责用户相关的业务逻辑
 */
public class UserService {
    
    private UserDAO userDAO;
    
    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户对象
     * @throws ValidationException 输入验证失败
     * @throws AuthenticationException 认证失败
     */
    public User login(String username, String password) 
            throws ValidationException, AuthenticationException {
        // 1. 验证输入
        validateLoginInput(username, password);
        
        // 2. 调用 DAO 进行登录
        User user = userDAO.login(username, password);
        
        // 3. 验证结果
        if (user == null) {
            throw new AuthenticationException("用户名或密码不正确");
        }
        
        return user;
    }
    
    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param confirmPassword 确认密码
     * @param email 邮箱
     * @return 注册成功的用户对象
     * @throws ValidationException 验证失败
     * @throws DatabaseException 数据库操作失败
     */
    public User register(String username, String password, 
                        String confirmPassword, String email) 
            throws ValidationException, DatabaseException {
        // 1. 验证输入
        validateRegisterInput(username, password, confirmPassword);
        
        // 2. 检查用户名是否已存在
        if (userDAO.isUsernameExists(username)) {
            throw new ValidationException("用户名已存在，请选择其他用户名");
        }
        
        // 3. 创建用户对象
        User newUser = new User();
        newUser.setUserName(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setRole("user");
        
        // 4. 保存到数据库
        boolean success = userDAO.register(newUser);
        if (!success) {
            throw new DatabaseException("注册失败，请稍后重试", null);
        }
        
        return newUser;
    }
    
    /**
     * 验证登录输入
     */
    private void validateLoginInput(String username, String password) 
            throws ValidationException {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("请输入用户名");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("请输入密码");
        }
    }
    
    /**
     * 验证注册输入
     */
    private void validateRegisterInput(String username, String password, 
                                      String confirmPassword) 
            throws ValidationException {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("请输入用户名");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("请输入密码");
        }
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            throw new ValidationException("请确认密码");
        }
        if (!password.equals(confirmPassword)) {
            throw new ValidationException("两次输入的密码不一致");
        }
    }
}
