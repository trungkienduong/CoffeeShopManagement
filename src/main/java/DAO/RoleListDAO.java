package DAO;

import MODEL.RoleList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RoleListDAO {
    private static RoleListDAO instance;

    public static RoleListDAO getInstance() {
        if (instance == null) {
            instance = new RoleListDAO();
        }
        return instance;
    }

    public boolean insert(RoleList role) {
        String sql = "INSERT INTO [ROLE_LIST] (ROLE_ID, ROLE_NAME) VALUES (?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, role.getRoleId());
            pst.setString(2, role.getRoleName());

            int result = pst.executeUpdate();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<RoleList> getAll() {
        List<RoleList> roles = new ArrayList<>();
        String sql = "SELECT * FROM [ROLE_LIST]";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()){

            while (rs.next()) {
                RoleList role = new RoleList();
                role.setRoleId(rs.getInt("ROLE_ID"));
                role.setRoleName(rs.getString("ROLE_NAME"));
                roles.add(role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    public RoleList findById(int roleId) {
        String sql = "SELECT * FROM [ROLE_LIST] WHERE ROLE_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, roleId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                RoleList role = new RoleList();
                role.setRoleId(rs.getInt("ROLE_ID"));
                role.setRoleName(rs.getString("ROLE_NAME"));
                return role;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public RoleList findByName(String roleName) {
        String sql = "SELECT * FROM [ROLE_LIST] WHERE ROLE_NAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, roleName);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                RoleList role = new RoleList();
                role.setRoleId(rs.getInt("ROLE_ID"));
                role.setRoleName(rs.getString("ROLE_NAME"));
                return role;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean update(RoleList role) {
        String sql = "UPDATE [ROLE_LIST] SET ROLE_NAME = ? WHERE ROLE_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, role.getRoleName());
            pst.setInt(2, role.getRoleId());

            int result = pst.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int roleId) {
        String sql = "DELETE FROM [ROLE_LIST] WHERE ROLE_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, roleId);
            int result = pst.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
