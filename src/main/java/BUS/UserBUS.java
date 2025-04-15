package BUS;

import DAO.UserDAO;
import MODEL.User;
import java.util.List;

public class UserBUS {
    private UserDAO userDAO;
    private static UserBUS instance;

    private UserBUS() {
        userDAO = UserDAO.getInstance();
    }

    public static UserBUS getInstance() {
        if (instance == null) {
            instance = new UserBUS();
        }
        return instance;
    }

    public List<User> getAll() {
        return userDAO.selectAll();
    }

    public boolean insert(User user) {
        return userDAO.insert(user);
    }

    public boolean update(User user) {
        return userDAO.update(user);
    }

    public boolean delete (String username) {
        return userDAO.delete(username);
    }

    public User findByName(String username) {
        return userDAO.findByName(username);
    }

    public boolean checkLogin (String username, String password) {
        return userDAO.checkLogin(username, password);
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }
}
