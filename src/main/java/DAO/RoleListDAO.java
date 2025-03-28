package DAO;

import MODEL.RoleList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleListDAO {

    private Connection connection;

    public RoleListDAO(Connection connection) {
        this.connection = connection;
    }

    // Thêm một quyền mới vào bảng ROLE_LIST
    public boolean addRole(RoleList role) {
        String sql = "INSERT INTO ROLE_LIST (ROLE_NAME) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role.getRoleName());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả các quyền trong bảng ROLE_LIST
    public List<RoleList> getAllRoles() {
        List<RoleList> roles = new ArrayList<>();
        String sql = "SELECT * FROM ROLE_LIST";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int roleID = resultSet.getInt("ROLE_ID");
                String roleName = resultSet.getString("ROLE_NAME");
                RoleList role = new RoleList(roleID, roleName);
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    // Cập nhật thông tin quyền
    public boolean updateRole(RoleList role) {
        String sql = "UPDATE ROLE_LIST SET ROLE_NAME = ? WHERE ROLE_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role.getRoleName());
            statement.setInt(2, role.getRoleID());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa quyền theo ROLE_ID
    public boolean deleteRole(int roleID) {
        String sql = "DELETE FROM ROLE_LIST WHERE ROLE_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleID);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy thông tin quyền theo ROLE_ID
    public RoleList getRoleByID(int roleID) {
        String sql = "SELECT * FROM ROLE_LIST WHERE ROLE_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, roleID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String roleName = resultSet.getString("ROLE_NAME");
                    return new RoleList(roleID, roleName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
