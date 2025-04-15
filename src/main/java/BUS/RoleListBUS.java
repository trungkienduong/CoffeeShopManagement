package BUS;

import DAO.RoleListDAO;
import MODEL.RoleList;
import java.util.List;

public class RoleListBUS {
    private RoleListDAO roleListDAO;
    private static RoleListBUS instance;

    private RoleListBUS() {
        roleListDAO = RoleListDAO.getInstance();
    }

    public static RoleListBUS getInstance() {
        if (instance == null) {
            instance = new RoleListBUS();
        }
        return instance;
    }

    public List<RoleList> getAll() {
        return roleListDAO.getAll();
    }

    public boolean insert(RoleList role) {
        return roleListDAO.insert(role);
    }

    public boolean update(RoleList role) {
        return roleListDAO.update(role);
    }

    public boolean delete(int roleId) {
        return roleListDAO.delete(roleId);
    }

    public RoleList findById(int roleId) {
        return roleListDAO.findById(roleId);
    }
}
