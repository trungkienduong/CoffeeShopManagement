package BUS;

import DAO.RoleListDAO;
import MODEL.RoleList;
import java.sql.Connection;
import java.util.List;

public class RoleListBUS {
    private RoleListDAO roleListDAO;

    public RoleListBUS(Connection connection) {
        this.roleListDAO = new RoleListDAO(connection);
    }

    // Lấy tất cả các quyền
    public List<RoleList> getAllRoles() {
        return roleListDAO.getAllRoles();
    }

    // Lấy thông tin quyền theo ROLE_ID
    public RoleList getRoleByID(int roleID) {
        return roleListDAO.getRoleByID(roleID);
    }

    // Thêm quyền mới
    public boolean addRole(RoleList role) {
        if (role == null || role.getRoleName().isEmpty()) {
            System.out.println("Thông tin quyền không hợp lệ.");
            return false;
        }
        return roleListDAO.addRole(role);
    }

    // Cập nhật thông tin quyền
    public boolean updateRole(RoleList role) {
        if (role == null || role.getRoleID() <= 0 || role.getRoleName().isEmpty()) {
            System.out.println("Dữ liệu quyền không hợp lệ.");
            return false;
        }
        return roleListDAO.updateRole(role);
    }

    // Xóa quyền theo ROLE_ID
    public boolean deleteRole(int roleID) {
        if (roleID <= 0) {
            System.out.println("ID quyền không hợp lệ.");
            return false;
        }
        return roleListDAO.deleteRole(roleID);
    }
}
