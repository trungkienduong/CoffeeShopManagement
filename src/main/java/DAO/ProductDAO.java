package DAO;

import MODEL.Category;
import MODEL.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static ProductDAO instance;
    private final CategoryDAO categoryDAO;

    private ProductDAO() {
        categoryDAO = CategoryDAO.getInstance();
    }

    public static ProductDAO getInstance() {
        if (instance == null) {
            instance = new ProductDAO();
        }
        return instance;
    }

    // ---------------------- INSERT ----------------------
    public boolean insert(Product product) {
        String sql = "INSERT INTO PRODUCT (PRODUCT_NAME, CATEGORY_ID, SELL_PRICE, IMAGE_PATH) VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, product.getProductName());
            pst.setInt(2, product.getCategory().getCategoryId());
            pst.setBigDecimal(3, product.getSellPrice());
            pst.setString(4, product.getImagePath());

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            // Lấy ID tự sinh gán lại cho product
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setProductId(generatedKeys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- UPDATE ----------------------
    public boolean update(Product product) {
        String sql = "UPDATE PRODUCT SET PRODUCT_NAME = ?, CATEGORY_ID = ?, SELL_PRICE = ?, IMAGE_PATH = ? WHERE PRODUCT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, product.getProductName());
            pst.setInt(2, product.getCategory().getCategoryId());
            pst.setBigDecimal(3, product.getSellPrice());
            pst.setString(4, product.getImagePath());
            pst.setInt(5, product.getProductId());

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- DELETE ----------------------
    public boolean delete(int productId) {
        String sql = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, productId);

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- GET ALL ----------------------
    public List<Product> getAll() {
        String sql = "SELECT * FROM PRODUCT";

        List<Product> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Product product = extractProduct(rs);
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    // ---------------------- FIND BY ID ----------------------
    public Product findById(int productId) {
        String sql = "SELECT * FROM PRODUCT WHERE PRODUCT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, productId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return extractProduct(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------------- FIND BY CATEGORY ----------------------
    public List<Product> findByCategory(Category category) {
        String sql = "SELECT * FROM PRODUCT WHERE CATEGORY_ID = ?";

        List<Product> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, category.getCategoryId());

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Product product = extractProduct(rs);
                    // Gán lại category để tránh gọi thêm DB
                    product.setCategory(category);
                    list.add(product);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ---------------------- SEARCH BY NAME ----------------------
    public List<Product> searchByName(String keyword) {
        String sql = "SELECT * FROM PRODUCT WHERE PRODUCT_NAME LIKE ?";

        List<Product> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Product product = extractProduct(rs);
                    list.add(product);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ---------------------- HELPER ----------------------
    private Product extractProduct(ResultSet rs) throws SQLException {
        Product product = new Product();

        product.setProductId(rs.getInt("PRODUCT_ID"));
        product.setProductName(rs.getString("PRODUCT_NAME"));

        int categoryId = rs.getInt("CATEGORY_ID");
        Category category = categoryDAO.findById(categoryId);
        product.setCategory(category);

        product.setSellPrice(rs.getBigDecimal("SELL_PRICE"));
        product.setImagePath(rs.getString("IMAGE_PATH"));

        return product;
    }
}
