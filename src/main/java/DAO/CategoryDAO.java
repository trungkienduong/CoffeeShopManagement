package DAO;

import MODEL.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private static CategoryDAO instance;

    private CategoryDAO() {

    }

    public static CategoryDAO getInstance() {
        if (instance == null) {
            instance = new CategoryDAO();
        }
        return instance;
    }

    // ---------------------- INSERT ----------------------
    public boolean insert(Category category) {
        String sql = "INSERT INTO CATEGORY (CATEGORY_NAME, CATEGORY_TYPE, DESCRIPTION) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, category.getCategoryName()); // gán giá trị cho ? thứ nhất
            pst.setString(2, String.valueOf(category.getCategoryType())); // gán giá trị cho ? thứ hai
            pst.setString(3, category.getDescription()); // gán giá trị cho ? thứ ba

            // thực thi câu lệnh
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //---------------------- UPDATE ----------------------
    public boolean update(Category category) {
        String sql = "UPDATE CATEGORY SET CATEGORY_NAME = ?, CATEGORY_TYPE = ?, DESCRIPTION = ? WHERE CATEGORY_ID = ?"; // gán giá trị cho ? thứ tư

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, category.getCategoryName()); // gán giá trị cho ? thứ nhất
            pst.setString(2, String.valueOf(category.getCategoryType())); // gán giá trị cho ? thứ hai
            pst.setString(3, category.getDescription()); // gán giá trị cho ? thứ ba
            pst.setInt(4, category.getCategoryId()); // gán giá trị cho ? thứ tư

            // thực thi câu lệnh
            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //---------------------- DELETE ----------------------
    public boolean delete(int categoryId) {
        String sql = "DELETE FROM CATEGORY WHERE CATEGORY_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, categoryId);
            int result = pst.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- GET ALL ----------------------
    public List<Category> getAll() {
        String sql = "SELECT * FROM CATEGORY";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("CATEGORY_ID"));
                category.setCategoryName(rs.getString("CATEGORY_NAME"));
                category.setCategoryType(rs.getString("CATEGORY_TYPE").charAt(0)); // Chuyển từ String sang char nếu cần
                category.setDescription(rs.getString("DESCRIPTION"));
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------- GET BY NAME ----------------------
    public Category findByName(String categoryName) {
        String sql = "SELECT * FROM CATEGORY WHERE CATEGORY_NAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, categoryName); // gán giá trị cho ? thứ nhất
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("CATEGORY_ID"));
                category.setCategoryName(rs.getString("CATEGORY_NAME"));
                category.setCategoryType(rs.getString("CATEGORY_TYPE").charAt(0)); // Chuyển từ String sang char nếu cần
                category.setDescription(rs.getString("DESCRIPTION"));
                return category;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------- GET BY ID ----------------------
    public Category findById(int categoryId) {
        String sql = "SELECT * FROM CATEGORY WHERE CATEGORY_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, categoryId); // gán giá trị cho ? thứ nhất
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("CATEGORY_ID"));
                category.setCategoryName(rs.getString("CATEGORY_NAME"));
                category.setCategoryType(rs.getString("CATEGORY_TYPE").charAt(0)); // Chuyển từ String sang char nếu cần
                category.setDescription(rs.getString("DESCRIPTION"));
                return category;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------- GET BY TYPE (I: Inventory, P: Product)----------------------
    public List<Category> findByType(char categoryType) {
        String sql = "SELECT * FROM CATEGORY WHERE CATEGORY_TYPE = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, String.valueOf(categoryType)); // gán giá trị cho ? thứ nhất
            ResultSet rs = pst.executeQuery();
            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("CATEGORY_ID"));
                category.setCategoryName(rs.getString("CATEGORY_NAME"));
                category.setCategoryType(rs.getString("CATEGORY_TYPE").charAt(0)); // Chuyển từ String sang char nếu cần
                category.setDescription(rs.getString("DESCRIPTION"));
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------- SEARCH BY NAME ----------------------
    public List<Category> searchByName(String keyword) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM CATEGORY WHERE CATEGORY_NAME LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("CATEGORY_ID"));
                category.setCategoryName(rs.getString("CATEGORY_NAME"));
                category.setCategoryType(rs.getString("CATEGORY_TYPE").charAt(0)); // Chuyển từ String sang char nếu cần
                category.setDescription(rs.getString("DESCRIPTION"));
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
