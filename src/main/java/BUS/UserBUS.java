package BUS;

import DAO.UserDAO;
import MODEL.User;

public class UserBUS {
    private UserDAO userDAO;

    public UserBUS() {
        userDAO = new UserDAO();
    }

    // Đăng nhập: kiểm tra username và password
    public User login(String username, String password) {
        return userDAO.getUserByLogin(username, password);
    }

    // Đăng ký: thêm user mới
    public boolean register(User user) {
        return userDAO.insertUser(user);
    }
}
