package museum.exception;

/**
 * 数据库异常
 * 数据库操作失败时抛出
 */
public class DatabaseException extends ServiceException {
    
    /**
     * 构造函数
     * @param message 异常消息
     * @param cause 原始异常
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
