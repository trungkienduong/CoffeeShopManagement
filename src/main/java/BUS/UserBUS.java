package BUS;

import DAO.UserDAO;
import MODEL.User;
import java.util.List;

public class UserBUS {
    private static UserBUS instance;
    private UserDAO userDAO;

    private UserBUS() {
        userDAO = UserDAO.getInstance();
    }

    public static UserBUS getInstance() {
        if (instance == null) {
            instance = new UserBUS();
        }
        return instance;
    }

    // -------------------- SELECT ALL --------------------
    public List<User> selectAll() {
        return userDAO.selectAll();
    }

    // -------------------- Check Login --------------------
    public boolean checkLogin(String username, String password) {
        try {
            return userDAO.checkLogin(username, password);
        } catch (Exception e) {
            System.err.println("Error checking login: " + e.getMessage());
            return false;
        }
    }

    // -------------------- Find User By Name --------------------
    public User FindByName(String username) {
        return userDAO.findByName(username);
    }

    // -------------------- Insert User --------------------
    public boolean insertUser(User user) {
        if (user != null && isValidEmail(user.getEmail())) {
            if (isEmailUnique(user.getEmail())) {
                try {
                    return userDAO.insert(user);
                } catch (Exception e) {
                    System.err.println("Error inserting user: " + e.getMessage());
                    return false;
                }
            } else {
                System.out.println("Email already exists.");
                return false;
            }
        }
        System.out.println("Invalid email or user.");
        return false;
    }

    // -------------------- Update User --------------------
    public boolean updateUser(User user) {
        try {
            return userDAO.update(user);
        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    // -------------------- Delete User --------------------
    public boolean deleteUser(String username) {
        try {
            return userDAO.delete(username);
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    // -------------------- Find User By Email --------------------
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }


    // ---------------------- CHECK USERNAME ----------------------
    public boolean isUsernameUnique(String username) {
        return userDAO.findByName(username) == null;
    }
    // ---------------------- CHECK EMAIL ----------------------
    public boolean isEmailUnique(String email) {
        return userDAO.findByEmail(email) == null;
    }

    public static boolean isValidEmail(String email) {
        if (email.isEmpty()) {
            return false; // Nếu email rỗng, trả về false
        }

        // Regex kiểm tra định dạng email thông thường
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex); // Kiểm tra email có khớp với regex không
    }
}
