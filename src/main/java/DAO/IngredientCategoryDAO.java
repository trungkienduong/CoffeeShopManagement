package DAO;

import MODEL.IngredientCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO xử lý các thao tác với bảng INGREDIENT_CATEGORY.
 */
public class IngredientCategoryDAO {
    private static IngredientCategoryDAO instance;

    private IngredientCategoryDAO() {}

    public static IngredientCategoryDAO getInstance() {
        if (instance == null) {
            instance = new IngredientCategoryDAO();
        }
        return instance;
    }

    /**
     * Thêm mới một loại nguyên liệu.
     */
    public boolean insert(IngredientCategory category) {
        String sql = "INSERT INTO INGREDIENT_CATEGORY (INGREDIENT_CATEGORY_NAME) VALUES (?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, category.getIngredientCategoryName());
            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) return false;

            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    category.setIngredientCategoryId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Insert IngredientCategory failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cập nhật loại nguyên liệu.
     */
    public boolean update(IngredientCategory category) {
        String sql = "UPDATE INGREDIENT_CATEGORY SET INGREDIENT_CATEGORY_NAME = ? WHERE INGREDIENT_CATEGORY_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, category.getIngredientCategoryName());
            pst.setInt(2, category.getIngredientCategoryId());

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update IngredientCategory failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Xóa loại nguyên liệu theo ID.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM INGREDIENT_CATEGORY WHERE INGREDIENT_CATEGORY_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete IngredientCategory failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lấy danh sách toàn bộ loại nguyên liệu.
     */
    public List<IngredientCategory> selectAll() {
        List<IngredientCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM INGREDIENT_CATEGORY";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                IngredientCategory category = new IngredientCategory();
                category.setIngredientCategoryId(rs.getInt("INGREDIENT_CATEGORY_ID"));
                category.setIngredientCategoryName(rs.getString("INGREDIENT_CATEGORY_NAME"));
                list.add(category);
            }
        } catch (SQLException e) {
            System.err.println("Select all IngredientCategory failed: " + e.getMessage());
        }

        return list;
    }

    /**
     * Tìm loại nguyên liệu theo ID.
     */
    public IngredientCategory findById(int id) {
        String sql = "SELECT * FROM INGREDIENT_CATEGORY WHERE INGREDIENT_CATEGORY_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    IngredientCategory category = new IngredientCategory();
                    category.setIngredientCategoryId(rs.getInt("INGREDIENT_CATEGORY_ID"));
                    category.setIngredientCategoryName(rs.getString("INGREDIENT_CATEGORY_NAME"));
                    return category;
                }
            }
        } catch (SQLException e) {
            System.err.println("Find IngredientCategory by ID failed: " + e.getMessage());
        }
        return null;
    }
}
