package DAO;

import MODEL.RoleList;

import MODEL.RoleList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RoleListDAO {
    // Singleton pattern: Chỉ tạo 1 instance duy nhất
    private static RoleListDAO instance;

    // lấy instance duy nhất (nếu chưa có thì tạo mới)
    public static RoleListDAO getInstance() {
        if (instance == null) {
            instance = new RoleListDAO();
        }
        return instance;
    }

    // ---------------------- CREATE ----------------------
    public boolean insert(RoleList role) {
        // chèn dữ liệu ( thêm quyền mới)
        String sql = "INSERT INTO RoleList (ROLE_ID, ROLE_NAME) VALUES (?, ?)";

        try (Connection con = DatabaseConnection.getConnection(); // mở kết nối
             PreparedStatement pst = con.prepareStatement(sql)) { // tạo lệnh sql chuẩn bị

            pst.setInt(1, role.getRoleId()); // gán giá trị cho ? thứ nhất
            pst.setString(2, role.getRoleName()); // gán giá trị cho ? thứ hai

            int result = pst.executeUpdate(); // thực thi câu lệnh ( trả về số dòng bị ảnh hưởng)

            return result > 0; // nếu có ít nhất một dòng bị ảnh hưởng => thêm thành công

        } catch (Exception e) {
            e.printStackTrace(); // in lỗi nếu có
            return false;
        }

    }

    // ---------------------- READ ALL ----------------------
    public List<RoleList> getAll() {
        List<RoleList> roles = new ArrayList<>();
        String sql = "SELECT * FROM ROLE_LIST";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()){

            while (rs.next()) {
                RoleList role = new RoleList();
                role.setRoleId(rs.getInt("ROLE_ID")); // lấy giá trị cột ROLE_ID
                role.setRoleName(rs.getString("ROLE_NAME")); // lấy giá trị cót ROLE_NAME
                roles.add(role); // thêm vào danh sách
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    // ---------------------- READ BY ID ----------------------
    public RoleList findById(int roleId) {
        String sql = "SELECT * FROM ROLE_LIST WHERE ROLE_ID = ?";

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

        return null; // không tìm thấy
    }

    // ---------------------- READ BY ID ----------------------
    public boolean update(RoleList role) {
        String sql = "UPDATE ROLE_LIST SET ROLE_NAME = ? WHERE ROLE_ID = ?";

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

    // ---------------------- DELETE ----------------------
    public boolean delete(int roleId) {
        String sql = "DELETE FROM ROLE_LIST WHERE ROLE_ID = ?";

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

    // ---------------------- MAPPING ----------------------
    private RoleList mapResultToRole(ResultSet rs) throws SQLException {
        RoleList role = new RoleList();
        role.setRoleId(rs.getInt("ROLE_ID"));
        role.setRoleName(rs.getString("ROLE_NAME"));
        return role;
    }
}
