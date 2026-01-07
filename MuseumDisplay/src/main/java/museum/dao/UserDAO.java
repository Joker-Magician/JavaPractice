package museum.dao;

import museum.entity.User;
import museum.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private DBUtil dbManager;

    public UserDAO() {
        this.dbManager = DBUtil.getInstance();
    }

    public boolean register(User user){

        String query = "insert into users (username,password,email,role) values (?,?,?,?)";
        
        // [MODIFIED] Used try-with-resources to automatically close Connection and PreparedStatement
        try(Connection conn = dbManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getRole());

            int rows = pstmt.executeUpdate();
            return (rows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User login(String username,String password){
        String query = "select * from users where username=? and password=?";
        
        // [MODIFIED] Added try-with-resources to prevent memory leaks (Connection was not closed previously)
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));

                return user;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  null;
    }

    public boolean isUsernameExists(String username) {
        String query = "select count(*) from users where username=?";
        
        // [MODIFIED] Added try-with-resources for connection management
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  false;
    }
}
