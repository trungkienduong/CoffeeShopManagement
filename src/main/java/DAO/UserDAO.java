package DAO;

import MODEL.User;
import java.sql.*;

public class UserDAO {

    // Đăng nhập: lấy User theo tên đăng nhập và mật khẩu
    public User getUserByLogin(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            if (rs.next()) {
                String userName = rs.getString("USERNAME");
                String passWord = rs.getString("PASSWORD");
                int roleId = rs.getInt("ROLE_ID");

                user = new User(userName, passWord, roleId);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn user: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }

        return user;
    }

    // Đăng ký: thêm user mới vào database
    public boolean insertUser(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO USERS (USERNAME, PASSWORD, ROLE_ID) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassWord());
            stmt.setInt(3, user.getRole_ID());

            int rows = stmt.executeUpdate();
            success = rows > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm user mới: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
            }
        }

        return success;
    }
}
