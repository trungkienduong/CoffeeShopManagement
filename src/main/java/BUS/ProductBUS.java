package BUS;

import DAO.ProductDAO;
import MODEL.Product;
import java.sql.Connection;
import java.util.List;

public class ProductBUS {
    private ProductDAO productDAO;

    public ProductBUS(Connection connection) {
        this.productDAO = new ProductDAO(connection);
    }

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    // Lấy thông tin sản phẩm theo ID
    public Product getProductById(int productId) {
        return productDAO.getProductById(productId);
    }

    // Thêm sản phẩm mới
    public boolean addProduct(Product product) {
        if (product == null || product.getName().isEmpty() || product.getCategoryID() <= 0 || product.getCostPrice().compareTo(product.getSellPrice()) >= 0) {
            System.out.println("Thông tin sản phẩm không hợp lệ.");
            return false;
        }
        return productDAO.addProduct(product);
    }

    // Cập nhật thông tin sản phẩm
    public boolean updateProduct(Product product) {
        if (product == null || product.getProductID() <= 0 || product.getName().isEmpty() || product.getCategoryID() <= 0 || product.getCostPrice().compareTo(product.getSellPrice()) >= 0) {
            System.out.println("Dữ liệu sản phẩm không hợp lệ.");
            return false;
        }
        return productDAO.updateProduct(product);
    }

    // Xóa sản phẩm theo ID
    public boolean deleteProduct(int productId) {
        if (productId <= 0) {
            System.out.println("ID sản phẩm không hợp lệ.");
            return false;
        }
        return productDAO.deleteProduct(productId);
    }
}
