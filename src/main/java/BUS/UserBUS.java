package BUS;

import DAO.UserDAO;
import MODEL.User;
import java.util.List;
import java.util.stream.Collectors;

public class UserBUS {
    private static UserBUS instance;
    private UserDAO userDAO;
    private User currentUser;

    private UserBUS() {
        userDAO = UserDAO.getInstance();
    }

    public static synchronized UserBUS getInstance() {
        if (instance == null) {
            instance = new UserBUS();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void logout() {
        currentUser = null;
    }

    public List<User> selectAll() {
        return userDAO.selectAll();
    }

    public boolean checkLogin(String username, String password) {
        try {
            if (userDAO.checkLogin(username, password)) {
                currentUser = findByName(username);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error checking login: " + e.getMessage());
            return false;
        }
    }

    public User findByName(String username) {
        return userDAO.findByName(username);
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public boolean insertUser(User user) {
        if (user == null) {
            System.out.println("User is null.");
            return false;
        }

        if (!isValidEmail(user.getEmail())) {
            System.out.println("Invalid email format.");
            return false;
        }

        if (!isEmailUnique(user.getEmail())) {
            System.out.println("Email already exists.");
            return false;
        }

        if (!isUsernameUnique(user.getUsername())) {
            System.out.println("Username already exists.");
            return false;
        }

        try {
            return userDAO.insert(user);
        } catch (Exception e) {
            System.err.println("Error inserting user: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(User user) {
        try {
            return userDAO.update(user);
        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(String username) {
        try {
            return userDAO.delete(username);
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    public boolean isUsernameUnique(String username) {
        return userDAO.findByName(username) == null;
    }

    public boolean isEmailUnique(String email) {
        return userDAO.findByEmail(email) == null;
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public List<String> getAllEmails() {
        List<User> users = userDAO.selectAll();
        return users.stream()
                .map(User::getEmail)
                .filter(email -> email != null && !email.isEmpty())
                .collect(Collectors.toList());
    }
}