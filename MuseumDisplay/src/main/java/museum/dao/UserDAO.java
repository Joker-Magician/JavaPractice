package museum.dao;

import museum.entity.User;
import museum.utils.DBUtil;

import java.sql.SQLException;

public interface UserDAO {
    public boolean register(User user) throws Exception;

    public boolean isUsernameExists(String username) throws Exception;

    public User login(String username,String password) throws Exception;



}
