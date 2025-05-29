package DAO;

import MODEL.User;
import java.sql.Connection; // Dùng để kết nối tới cơ sở dữ liệu
import java.sql.PreparedStatement; // Dùng để tạo các câu lệnh SQL có tham số
import java.sql.ResultSet; // Dùng để đọc dữ liệu trả về từ câu lệnh SELECT
import java.sql.SQLException; // Xử lý các lỗi liên quan đến SQL
import java.util.ArrayList; // Dùng để lưu danh sách các User
import java.util.List; // Interface danh sách chung

public class UserDAO {
    private static UserDAO instance;
    private RoleListDAO roleListDAO;

    private UserDAO() {
        roleListDAO = RoleListDAO.getInstance();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public boolean insert(User user) {
        String sql = "INSERT INTO [USERS] (USERNAME, PASSWORD, EMAIL, ROLE_ID) VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getEmail());
            pst.setInt(4, user.getRole().getRoleId());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(User user) {
        String sql = "UPDATE [USERS] SET PASSWORD = ?, EMAIL = ?, ROLE_ID = ? WHERE USERNAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, user.getPassword());
            pst.setString(2, user.getEmail());
            pst.setInt(3, user.getRole().getRoleId());
            pst.setString(4, user.getUsername());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String username) {
        String sql = "DELETE FROM [USERS] WHERE USERNAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username);

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> selectAll() {
        String sql = "SELECT * FROM [USERS]";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setEmail(rs.getString("EMAIL"));

                int roleId = rs.getInt("ROLE_ID");
                user.setRole(RoleListDAO.getInstance().findById(roleId));

                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User findByName(String username) {
        String sql = "SELECT * FROM [USERS] WHERE USERNAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setEmail(rs.getString("EMAIL"));

                int roleId = rs.getInt("ROLE_ID");
                user.setRole(RoleListDAO.getInstance().findById(roleId));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM [USERS] WHERE EMAIL = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, email);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setEmail(rs.getString("EMAIL"));

                int roleId = rs.getInt("ROLE_ID");
                user.setRole(RoleListDAO.getInstance().findById(roleId));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM [USERS] WHERE USERNAME = ? AND PASSWORD = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}