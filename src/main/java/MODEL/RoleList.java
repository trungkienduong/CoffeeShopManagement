package MODEL;

public class RoleList {
    private int RoleID;
    private String RoleName;

    public RoleList(int roleID, String roleName) {
        RoleID = roleID;
        RoleName = roleName;
    }

    public int getRoleID() {
        return RoleID;
    }

    public void setRoleID(int roleID) {
        RoleID = roleID;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }
}
