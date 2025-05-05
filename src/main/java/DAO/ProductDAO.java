package DAO;

import MODEL.Category;
import MODEL.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
  /*private static ProductDAO instance;
  private CategoryDAO categoryDAO;

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
        String sql = "INSERT INTO [PRODUCT] (PRODUCT_ID, PRODUCT_NAME, CATEGORY_ID, SELL_PRICE) VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, product.getProductId());
            pst.setString(2, product.getProductName());
            pst.setInt(3, product.getCategory().getCategoryId());
            pst.setDouble(4, product.getSellPrice());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- UPDATE ----------------------
    public boolean update(Product product) {
        String sql = "UPDATE [PRODUCT] SET PRODUCT_NAME = ?, CATEGORY_ID = ?, SELL_PRICE = ? WHERE PRODUCT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, product.getProductName());
            pst.setInt(2, product.getCategory().getCategoryId());
            pst.setDouble(3, product.getSellPrice());
            pst.setInt(4, product.getProductId());

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- DELETE ----------------------
    public boolean delete(int productId) {
        String sql = "DELETE FROM [PRODUCT] WHERE PRODUCT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, productId);

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------------- GET ALL ----------------------
    public List<Product> getAll() {
        String sql = "SELECT * FROM [PRODUCT]";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<Product> list = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("PRODUCT_ID"));
                product.setProductName(rs.getString("PRODUCT_NAME"));
                product.setCategory(categoryDAO.findById(rs.getInt("CATEGORY_ID")));
                product.setSellPrice(rs.getDouble("SELL_PRICE"));
                list.add(product);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------- GET BY ID ----------------------
    public Product findById(int productId) {
        String sql = "SELECT * FROM [PRODUCT] WHERE PRODUCT_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, productId);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("PRODUCT_ID"));
                product.setProductName(rs.getString("PRODUCT_NAME"));
                product.setCategory(categoryDAO.findById(rs.getInt("CATEGORY_ID")));
                product.setSellPrice(rs.getDouble("SELL_PRICE"));
                return product;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------- GET BY CATEGORY ----------------------
    public List<Product> findByCategory(Category category) {
        String sql = "SELECT * FROM [PRODUCT] WHERE CATEGORY_ID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, category.getCategoryId());

            ResultSet rs = pst.executeQuery();
            List<Product> list = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("PRODUCT_ID"));
                product.setProductName(rs.getString("PRODUCT_NAME"));
                product.setCategory(categoryDAO.findById(rs.getInt("CATEGORY_ID")));
                product.setSellPrice(rs.getDouble("SELL_PRICE"));
                list.add(product);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------- SEARCH BY NAME ----------------------
    public List<Product> searchByName(String keyword) {
        String sql = "SELECT * FROM [PRODUCT] WHERE PRODUCT_NAME LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%");

            ResultSet rs = pst.executeQuery();
            List<Product> list = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("PRODUCT_ID"));
                product.setProductName(rs.getString("PRODUCT_NAME"));
                product.setCategory(categoryDAO.findById(rs.getInt("CATEGORY_ID")));
                product.setSellPrice(rs.getDouble("SELL_PRICE"));
                list.add(product);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }*/
}
