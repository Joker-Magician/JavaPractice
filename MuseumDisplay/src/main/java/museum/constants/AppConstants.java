package museum.constants;

/**
 * 应用程序常量类
 * 集中管理所有硬编码的常量值，提高可维护性
 */
public class AppConstants {
    
    // ==================== 窗口尺寸 ====================
    /** 登录窗口宽度 */
    public static final int LOGIN_WIDTH = 800;
    
    /** 登录窗口高度 */
    public static final int LOGIN_HEIGHT = 600;
    
    /** 仪表板窗口宽度 */
    public static final int DASHBOARD_WIDTH = 1024;
    
    /** 仪表板窗口高度 */
    public static final int DASHBOARD_HEIGHT = 768;
    
    // ==================== 颜色常量 ====================
    /** 中国红 (用于非遗项目) */
    public static final String IMPERIAL_RED = "#8B0000";
    
    /** 深灰绿色 (用于古建筑项目) */
    public static final String DARK_SLATE_GRAY = "#2F4F4F";
    
    /** 金色 */
    public static final String GOLD = "#FFD700";
    
    // ==================== FXML 文件路径 ====================
    /** 登录界面 FXML */
    public static final String FXML_LOGIN = "/com/Login.fxml";
    
    /** 注册界面 FXML */
    public static final String FXML_REGISTER = "/com/Register.fxml";
    
    /** 主仪表板 FXML */
    public static final String FXML_MAIN_DASHBOARD = "/com/MainDashboard.fxml";
    
    /** 管理员仪表板 FXML */
    public static final String FXML_ADMIN_DASHBOARD = "/com/AdminDashboard.fxml";
    
    /** 非遗详情 FXML */
    public static final String FXML_HERITAGE_DETAIL = "/com/HeritageDetail.fxml";
    
    /** 古建筑详情 FXML */
    public static final String FXML_ARCHITECTURE_DETAIL = "/com/ArchitectureDetail.fxml";
    
    // ==================== CSS 文件路径 ====================
    /** 主样式表 */
    public static final String CSS_MAIN = "/css/styles.css";
    
    // ==================== 样式字符串 ====================
    /** 非遗项目名称标签样式 */
    public static final String STYLE_NAME_LABEL_HERITAGE = 
        "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #8B0000;";
    
    /** 古建筑项目名称标签样式 */
    public static final String STYLE_NAME_LABEL_ARCHITECTURE = 
        "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2F4F4F;";
    
    // 私有构造函数，防止实例化
    private AppConstants() {
        throw new AssertionError("常量类不应该被实例化");
    }
}
