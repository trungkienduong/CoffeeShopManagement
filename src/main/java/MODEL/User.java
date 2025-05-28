package MODEL;

public class User {
    private String username;
    private String password;
    private String email;
    private RoleList role; // chứa ROLE_ID và ROLE_NAME

    public User() {}

    public User(String username, String password, String email, RoleList role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Getter/Setter
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

    public RoleList getRole() {
        return role;
    }

    public void setRole(RoleList role) {
        this.role = role;
    }

    // Helper method để so sánh quyền tiện lợi
    public boolean hasRole(String roleName) {
        return role != null && role.getRoleName().equalsIgnoreCase(roleName);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
