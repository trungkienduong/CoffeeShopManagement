package BUS;

import DAO.RoleListDAO;
import MODEL.RoleList;

import java.util.List;

public class RoleListBUS {
    private RoleListDAO roleDAO;

    public RoleListBUS() {
        roleDAO = new RoleListDAO();
    }

    // Lấy tất cả quyền
    public List<RoleList> getAllRoles() {
        return roleDAO.getAllRoles();
    }

    // Lấy quyền theo ID
    public RoleList getRoleById(int roleId) {
        return roleDAO.getRoleById(roleId);
    }

    // Thêm quyền mới
    public boolean addRole(RoleList role) {
        return roleDAO.addRole(role);
    }

    // Cập nhật quyền
    public boolean updateRole(RoleList role) {
        return roleDAO.updateRole(role);
    }

    // Xóa quyền theo ID
    public boolean deleteRole(int roleId) {
        return roleDAO.deleteRole(roleId);
    }
}
