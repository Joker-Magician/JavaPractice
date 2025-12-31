package Java课程实验FX;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    /**
     * 得到数据库连接
     */
    public Connection getConnection() {
        try {
            // 1. 加载配置文件
            Properties prop = new Properties();
            // getResourceAsStream 会自动去 classpath (即 resources 目录) 下找文件
            InputStream is = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");

            if (is == null) {
                throw new RuntimeException("找不到 db.properties 文件，请确保它在 src/main/resources 下！");
            }
            prop.load(is);
            // 2. 注册驱动 (从配置文件读取)
            String driverClass = prop.getProperty("driver");
            // 也可以保留硬编码: Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName(driverClass);
            // 获得数据库连接
            // 3. 获得数据库连接 (从配置文件读取 URL, User, Password)
            String url = prop.getProperty("url");
            String user = prop.getProperty("username");
            String password = prop.getProperty("password");

            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 返回连接
        return conn;
    }
    /**
     * 释放资源
     */
    public void closeAll() {
        // 如果rs不空，关闭rs
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 如果pstmt不空，关闭pstmt
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 如果conn不空，关闭conn
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 执行SQL语句，可以进行查询
     */
    public ResultSet executeQuery(String sql, String[] param) {
        try {
            // 得到PreparedStatement对象
            pstmt = conn.prepareStatement(sql);
            if (param != null && param.length>0) {
                for (int i = 0; i < param.length; i++) {
                    // 为预编译sql设置参数
                    pstmt.setString(i + 1, param[i]);
                }
            }
            // 执行SQL语句
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            // 处理SQLException异常
            e.printStackTrace();
        }
        return rs;
    }
    /**
     * 执行SQL语句，可以进行增、删、改的操作，不能执行查询
     */
    public int executeUpdate(String sql, String[] param) {
        int num = 0;
        try {
            // 得到PreparedStatement对象
            pstmt = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    // 为预编译sql设置参数
                    pstmt.setString(i + 1, param[i]);
                }
            }
            // 执行SQL语句
            num = pstmt.executeUpdate();
        } catch (SQLException e) {
            // 处理SQLException异常
            e.printStackTrace();
        }
        return num;
    }
}

