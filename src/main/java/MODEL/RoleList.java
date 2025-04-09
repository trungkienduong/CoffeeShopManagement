package MODEL;

public class RoleList {
     private int Role_ID;
     private String Role_Name;

    public RoleList(int role_ID, String role_Name) {
        Role_ID = role_ID;
        Role_Name = role_Name;
    }

    public int getRole_ID() {
        return Role_ID;
    }

    public void setRole_ID(int role_ID) {
        Role_ID = role_ID;
    }

    public String getRole_Name() {
        return Role_Name;
    }

    public void setRole_Name(String role_Name) {
        Role_Name = role_Name;
    }

    @Override
    public String toString() {
        return "RoleList{" +
                "Role_ID=" + Role_ID +
                ", Role_Name='" + Role_Name + '\'' +
                '}';
    }
}
