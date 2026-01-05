package museum.exception;

/**
 * 验证异常
 * 输入数据验证失败时抛出
 */
public class ValidationException extends ServiceException {
    
    /**
     * 构造函数
     * @param message 异常消息（如："用户名不能为空"）
     */
    public ValidationException(String message) {
        super(message);
    }
}
