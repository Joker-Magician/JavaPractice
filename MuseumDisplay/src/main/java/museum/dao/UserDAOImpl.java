package museum.dao;

import museum.entity.User;
import museum.utils.DBUtil;

public class UserDAOImpl {
    private DBUtil dbManager;

    public boolean register(User user){
        return  false;
    }

    public User login(String username,String password){
        return  null;
    }

    public boolean isUsernameExists(String username) {
        return  false;
    }
}
