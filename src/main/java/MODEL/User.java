package MODEL;

public class User {
    private String UserName;
    private String Password;
    private String Role;

    public User(String UserName, String Password, String Role) {
        this.UserName = UserName;
        this.Password = Password;
        this.Role = Role;
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

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
