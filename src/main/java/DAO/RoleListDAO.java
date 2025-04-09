package DAO;

import MODEL.RoleList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleListDAO {

    // Lấy tất cả quyền từ bảng ROLE_LIST
    public List<RoleList> getAllRoles() {
        List<RoleList> roleList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT ROLE_ID, ROLE_NAME FROM ROLE_LIST";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ROLE_ID");
                String name = rs.getString("ROLE_NAME");
                roleList.add(new RoleList(id, name));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách quyền: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return roleList;
    }

    // Lấy quyền theo ID
    public RoleList getRoleById(int roleId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        RoleList role = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT ROLE_ID, ROLE_NAME FROM ROLE_LIST WHERE ROLE_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, roleId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                role = new RoleList(rs.getInt("ROLE_ID"), rs.getString("ROLE_NAME"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy quyền theo ID: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, rs);
        }

        return role;
    }

    // Thêm quyền mới
    public boolean addRole(RoleList role) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO ROLE_LIST (ROLE_NAME) VALUES (?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, role.getRole_Name());

            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm quyền: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

        return success;
    }

    // Cập nhật quyền
    public boolean updateRole(RoleList role) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE ROLE_LIST SET ROLE_NAME = ? WHERE ROLE_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, role.getRole_Name());
            stmt.setInt(2, role.getRole_ID());

            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật quyền: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

        return success;
    }

    // Xóa quyền theo ID
    public boolean deleteRole(int roleId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM ROLE_LIST WHERE ROLE_ID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, roleId);

            success = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa quyền: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }

        return success;
    }

    // Hàm tiện ích đóng kết nối
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        DatabaseConnection.closeConnection(conn);
    }
}
