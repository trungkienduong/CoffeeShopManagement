package BUS;

import DAO.UserDAO;
import MODEL.User;
import java.util.List;

public class UserBUS {
    private UserDAO userDAO;

    public UserBUS() {
        this.userDAO = new UserDAO();
    }

    // Lấy danh sách tất cả tài khoản
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    // Thêm tài khoản mới
    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }

    // Cập nhật mật khẩu tài khoản
    public boolean updatePassword(String username, String newPassword) {
        return userDAO.updatePassword(username, newPassword);
    }

    // Xóa tài khoản
    public boolean deleteUser(String username) {
        return userDAO.deleteUser(username);
    }

    // Tìm người dùng theo tên đăng nhập
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }
}
