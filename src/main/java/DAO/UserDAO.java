package DAO;

import MODEL.User;
import java.sql.*;

public class UserDAO {
    private Connection connection;

    // Constructor để khởi tạo kết nối với cơ sở dữ liệu
    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    // Phương thức thêm người dùng mới
    public boolean addUser(User user) {
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD, ROLE_ID) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword()); // Mật khẩu có thể mã hóa nếu cần
            ps.setInt(3, user.getRoleID());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức lấy thông tin người dùng theo tên đăng nhập
    public User getUserByUsername(String username) {
        User user = null;
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                            rs.getString("USERNAME"),
                            rs.getString("PASSWORD"),
                            rs.getInt("ROLE_ID")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Phương thức cập nhật mật khẩu người dùng
    public boolean updatePassword(String username, String newPassword) {
        String sql = "UPDATE USERS SET PASSWORD = ? WHERE USERNAME = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newPassword); // Cập nhật mật khẩu mới
            ps.setString(2, username);    // Tìm người dùng theo username
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức xóa người dùng theo tên đăng nhập
    public boolean deleteUser(String username) {
        String sql = "DELETE FROM USERS WHERE USERNAME = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
