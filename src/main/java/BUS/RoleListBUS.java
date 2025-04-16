package BUS;

import DAO.RoleListDAO;
import MODEL.RoleList;
import java.util.List;

public class RoleListBUS {
    private static RoleListBUS instance;
    private final RoleListDAO roleListDAO;

    // Constructor
    private RoleListBUS() {
        roleListDAO = RoleListDAO.getInstance();
    }

    // Singleton pattern
    public static RoleListBUS getInstance() {
        if (instance == null) {
            instance = new RoleListBUS();
        }
        return instance;
    }

    //------------------- CRUD Operations -------------------

    // Get all roles
    public List<RoleList> getAllRoles() {
        return roleListDAO.getAll();
    }

    // Find role by ID
    public RoleList getRoleById(int roleId) {
        return roleListDAO.findById(roleId);
    }

    // Find role by Name
    public RoleList getRoleByName(String roleName) {
        return roleListDAO.findByName(roleName);
    }

    // Phương thức trong RoleListBUS để lấy roleId từ tên role
    public int getRoleIdByName(String roleName) {
        RoleList role = getRoleByName(roleName); // Lấy đối tượng RoleList theo tên
        if (role != null) {
            return role.getRoleId(); // Trả về roleId
        }
        return -1; // Trả về -1 nếu không tìm thấy role
    }


    // Update role
    public boolean updateRole(RoleList role) {
        if (isValidRole(role)) {
            try {
                return roleListDAO.update(role);
            } catch (Exception e) {
                logError("Error updating role: " + e.getMessage());
            }
        }
        return false;
    }

    // Insert role
    public boolean insertRole(RoleList role) {
        if (isValidRole(role)) {
            try {
                return roleListDAO.insert(role);
            } catch (Exception e) {
                logError("Error inserting role: " + e.getMessage());
            }
        }
        return false;
    }

    // Delete role
    public boolean deleteRole(int roleId) {
        try {
            return roleListDAO.delete(roleId);
        } catch (Exception e) {
            logError("Error deleting role: " + e.getMessage());
        }
        return false;
    }

    //------------------- COMBOBOX ----------------------

    // Get list of role names for combo box
    public List<String> getRoleNamesForComboBox() {
        try {
            return roleListDAO.getAll().stream()
                    .map(RoleList::getRoleName)
                    .toList();
        } catch (Exception e) {
            logError("Error fetching role names: " + e.getMessage());
        }
        return null;
    }

    //------------------- Helper Methods -------------------

    // Validate if a role is valid
    private boolean isValidRole(RoleList role) {
        return role != null && role.getRoleName() != null && !role.getRoleName().isEmpty();
    }

    // Log errors to the console
    private void logError(String message) {
        System.err.println(message);
    }
}
