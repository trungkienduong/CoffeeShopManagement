package BUS;

import DAO.UserDAO;
import MODEL.User;
import java.sql.Connection;

public class UserBUS {
    private UserDAO userDAO;

    public UserBUS(Connection connection) {
        this.userDAO = new UserDAO(connection);
    }

    // Lấy thông tin người dùng theo tên đăng nhập
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    // Thêm người dùng mới
    public boolean addUser(User user) {
        if (user == null || user.getUserName().isEmpty() || user.getPassword().isEmpty()) {
            System.out.println("Thông tin người dùng không hợp lệ.");
            return false;
        }
        return userDAO.addUser(user);
    }

    // Cập nhật mật khẩu người dùng
    public boolean updatePassword(String username, String newPassword) {
        if (username.isEmpty() || newPassword.isEmpty()) {
            System.out.println("Mật khẩu mới hoặc tên đăng nhập không hợp lệ.");
            return false;
        }
        return userDAO.updatePassword(username, newPassword);
    }

    // Xóa người dùng theo tên đăng nhập
    public boolean deleteUser(String username) {
        if (username.isEmpty()) {
            System.out.println("Tên đăng nhập không hợp lệ.");
            return false;
        }
        return userDAO.deleteUser(username);
    }
}
