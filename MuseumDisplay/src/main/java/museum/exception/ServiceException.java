package museum.exception;

/**
 * 业务异常基类
 * 所有业务相关的异常都应该继承此类
 */
public class ServiceException extends Exception {
    
    /**
     * 构造函数
     * @param message 异常消息
     */
    public ServiceException(String message) {
        super(message);
    }
    
    /**
     * 构造函数
     * @param message 异常消息
     * @param cause 原因异常
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
