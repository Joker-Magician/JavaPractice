package museum.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    // 1. 改为实例变量，不再是 static final 常量
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String dbDriver;

    private DatabaseManager() {
        try {
            // 2. 加载配置文件逻辑
            Properties prop = new Properties();
            // 这里的路径不需要写 "src/main/resources"，直接写文件名即可
            InputStream is = DatabaseManager.class.getClassLoader().getResourceAsStream("db.properties");

            if (is == null) {
                throw new RuntimeException("在 resources 目录下找不到 db.properties 文件！");
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
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("数据库初始化失败: " + e.getMessage());
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // 5. 这里使用读取到的变量重新连接
                connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            // Create Users table (MySQL syntax)
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "user_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "username VARCHAR(50) NOT NULL UNIQUE," +
                    "password VARCHAR(255) NOT NULL," +
                    "email VARCHAR(100)," +
                    "role VARCHAR(20) DEFAULT 'user'," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            stmt.execute(createUsersTable);

            // Create default admin user if not exists
            // Password is 'admin123' (In a real app, this should be hashed)
            // For this project, we'll store plain text for simplicity unless specified otherwise, 
            // but the plan mentioned hashing. Let's start basic and can upgrade. 
            // Wait, the plan said "User registration with password hashing", let's assume we'll do hashing in DAO.
            // But for simple initialization let's just create table.

            // Create Heritage table (MySQL syntax)
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

            // Create Architecture table (MySQL syntax)
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
