package DAO;

import MODEL.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private static CategoryDAO instance;

    private CategoryDAO() {}

    public static CategoryDAO getInstance() {
        if (instance == null) {
            instance = new CategoryDAO();
        }
        return instance;
    }

    // INSERT
    public boolean insert(Category category) {
        String sql = "INSERT INTO CATEGORY (CATEGORY_NAME) VALUES (?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, category.getCategoryName());
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean update(Category category) {
        String sql = "UPDATE CATEGORY SET CATEGORY_NAME = ? WHERE CATEGORY_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, category.getCategoryName());
            pst.setInt(2, category.getCategoryId());
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public boolean delete(int categoryId) {
        String sql = "DELETE FROM CATEGORY WHERE CATEGORY_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, categoryId);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET ALL
    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM CATEGORY";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("CATEGORY_ID"));
                category.setCategoryName(rs.getString("CATEGORY_NAME"));
                list.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // FIND BY ID
    public Category findById(int id) {
        String sql = "SELECT * FROM CATEGORY WHERE CATEGORY_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Category(rs.getInt("CATEGORY_ID"), rs.getString("CATEGORY_NAME"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // FIND BY NAME
    public Category findByName(String name) {
        String sql = "SELECT * FROM CATEGORY WHERE CATEGORY_NAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Category(rs.getInt("CATEGORY_ID"), rs.getString("CATEGORY_NAME"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // SEARCH BY NAME (LIKE)
    public List<Category> searchByName(String keyword) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM CATEGORY WHERE CATEGORY_NAME LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("CATEGORY_ID"));
                category.setCategoryName(rs.getString("CATEGORY_NAME"));
                list.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
