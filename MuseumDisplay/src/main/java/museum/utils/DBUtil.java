package museum.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
    private static DBUtil instance;
    private Connection connection;

    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String dbDriver;

    private DBUtil() {
        try {
            // 2. 加载配置文件
            Properties prop = new Properties();
            InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");

            if (is == null) {
                throw new RuntimeException("在resources目录下找不到db.properties文件！");
            }
            prop.load(is);

            // 3. 将配置文件中的值赋值给变量
            this.dbUrl = prop.getProperty("url");
            this.dbUser = prop.getProperty("username");
            this.dbPassword = prop.getProperty("password");
            this.dbDriver = prop.getProperty("driver");

            // 4. 连接数据库
            Class.forName(dbDriver);
            this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // 初始化表结构
            initializeDatabase();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据库初始化失败：" + e.getMessage());
        }

    }

    public static synchronized DBUtil getInstance() {
        if (instance == null) {         // 检查 instance 变量是否已经被初始化
            instance = new DBUtil();    // 如果还没有初始化（即第一次调用时），创建一个新的 DBUtil 对象
        }                               // 如果已经初始化过了，直接返回现有的那个对象
        return instance;
    }

    public Connection getConnection() {
        try{
            if(connection == null || connection.isClosed()){
                // 5. 使用读取到的变量重新连接
                connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    private void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            // 创建Users表
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "user_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "username VARCHAR(50) NOT NULL UNIQUE," +
                    "password VARCHAR(255) NOT NULL," +
                    "email VARCHAR(100)," +
                    "role VARCHAR(20) DEFAULT 'user'," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            stmt.execute(createUsersTable);

            // 创建Heritage表
            String createHeritageTable = "CREATE TABLE IF NOT EXISTS heritage (" +
                    "heritage_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(200) NOT NULL," +
                    "category VARCHAR(100)," +
                    "region VARCHAR(100)," +
                    "description TEXT," +
                    "image_path VARCHAR(500)," +
                    "year_recognized INT," +
                    "created_by INT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (created_by) REFERENCES users(user_id)" +
                    ")";
            stmt.execute(createHeritageTable);

            // 创建Architecture表
            String createArchitectureTable = "CREATE TABLE IF NOT EXISTS architecture (" +
                    "architecture_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(200) NOT NULL," +
                    "dynasty VARCHAR(50)," +
                    "location VARCHAR(200)," +
                    "type VARCHAR(50)," +
                    "description TEXT," +
                    "image_path VARCHAR(500)," +
                    "year_built INT," +
                    "created_by INT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (created_by) REFERENCES users(user_id)" +
                    ")";
            stmt.execute(createArchitectureTable);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
}
