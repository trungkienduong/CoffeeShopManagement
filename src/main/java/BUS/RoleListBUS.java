package BUS;

import DAO.RoleListDAO;
import MODEL.RoleList;
import java.util.List;

public class RoleListBUS {
    private static RoleListBUS instance;
    private final RoleListDAO roleListDAO;

    private RoleListBUS() {
        roleListDAO = RoleListDAO.getInstance();
    }

    public static RoleListBUS getInstance() {
        if (instance == null) {
            instance = new RoleListBUS();
        }
        return instance;
    }

    public List<RoleList> getAllRoles() {
        return roleListDAO.getAll();
    }

    public RoleList getRoleById(int roleId) {
        return roleListDAO.findById(roleId);
    }

    public RoleList getRoleByName(String roleName) {
        return roleListDAO.findByName(roleName);
    }

    public int getRoleIdByName(String roleName) {
        RoleList role = getRoleByName(roleName);
        if (role != null) {
            return role.getRoleId();
        }
        return -1;
    }

    public boolean updateRole(RoleList role) {
        if (isValidRole(role)) {
            try {
                return roleListDAO.update(role);
            } catch (Exception e) {
            }
        }
        return false;
    }

    public boolean insertRole(RoleList role) {
        if (isValidRole(role)) {
            try {
                return roleListDAO.insert(role);
            } catch (Exception e) {
            }
        }
        return false;
    }

    public boolean deleteRole(int roleId) {
        try {
            return roleListDAO.delete(roleId);
        } catch (Exception e) {
        }
        return false;
    }

    public List<String> getRoleNamesForComboBox() {
        try {
            return roleListDAO.getAll().stream()
                    .map(RoleList::getRoleName)
                    .toList();
        } catch (Exception e) {
        }
        return null;
    }

    private boolean isValidRole(RoleList role) {
        return role != null && role.getRoleName() != null && !role.getRoleName().isEmpty();
    }
}
