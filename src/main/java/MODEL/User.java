package MODEL;

public class User {
    private String UserName;
    private String Password;
    private int RoleID;

    // Constructor mặc định
    public User() {
    }

    public User(String userName, String password, int roleID) {
        UserName = userName;
        Password = password;
        RoleID = roleID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getRoleID() {
        return RoleID;
    }

    public void setRoleID(int roleID) {
        RoleID = roleID;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", RoleID=" + RoleID +
                '}';
    }
}
