package MODEL;

public class User {
    private String username;
    private String password;
    private String email;
    private int roleId;
    private RoleList role; // Đối tượng RoleList để lưu thông tin quyền

    // Default constructor
    public User() {
    }

    // Parameterized constructor without role object
    public User(String username, String password, String email, int roleId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
    }

    // Parameterized constructor with role object
    public User(String username, String password, String email, RoleList role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleId = role.getRoleId();
        this.role = role;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public RoleList getRole() {
        return role;
    }

    public void setRole(RoleList role) {
        this.role = role;
        this.roleId = role.getRoleId();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                ", role=" + (role != null ? role.getRoleName() : "null") +
                '}';
    }
}
