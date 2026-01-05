package museum.exception;

/**
 * 认证异常
 * 用户登录验证失败时抛出
 */
public class AuthenticationException extends ServiceException {
    
    /**
     * 构造函数
     * @param message 异常消息（如："用户名或密码不正确"）
     */
    public AuthenticationException(String message) {
        super(message);
    }
}
