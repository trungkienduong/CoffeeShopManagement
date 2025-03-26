package DAO;

import MODEL.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM [USER]";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("ROLE")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách người dùng: " + e.getMessage());
        }
        return userList;
    }

    // Thêm tài khoản mới
    public boolean addUser(User user) {
        String query = "INSERT INTO [USER] (USERNAME, PASSWORD, ROLE) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm tài khoản: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật mật khẩu
    public boolean updatePassword(String username, String newPassword) {
        String query = "UPDATE [USER] SET PASSWORD = ? WHERE USERNAME = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật mật khẩu: " + e.getMessage());
            return false;
        }
    }

    // Xóa tài khoản
    public boolean deleteUser(String username) {
        String query = "DELETE FROM [USER] WHERE USERNAME = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa tài khoản: " + e.getMessage());
            return false;
        }
    }

    // Tìm người dùng theo tên đăng nhập
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM [USER] WHERE USERNAME = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("ROLE")
                );
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm tài khoản: " + e.getMessage());
        }
        return null;
    }
}
