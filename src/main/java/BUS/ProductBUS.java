package BUS;

import DAO.ProductDAO;
import MODEL.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ProductBUS {
    private final ProductDAO dao = new ProductDAO();

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return dao.getAllProducts();
    }

    // ObservableList cho TableView hoặc ComboBox
    public ObservableList<Product> getObservableProducts() {
        return FXCollections.observableArrayList(dao.getAllProducts());
    }

    // Thêm sản phẩm
    public boolean addProduct(Product product) {
        if (isValidProduct(product)) {
            return dao.addProduct(product);
        }
        return false;
    }

    // Cập nhật sản phẩm
    public boolean updateProduct(Product product) {
        if (isValidProduct(product)) {
            return dao.updateProduct(product);
        }
        return false;
    }

    // Xóa sản phẩm
    public boolean deleteProduct(int productId) {
        return dao.deleteProduct(productId);
    }

    // Tìm kiếm sản phẩm
    public List<Product> searchProducts(String keyword) {
        return dao.searchProducts(keyword);
    }

    // Kiểm tra sản phẩm hợp lệ
    private boolean isValidProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            System.err.println("Tên sản phẩm không được để trống.");
            return false;
        }
        if (product.getCostPrice() == null || product.getSellPrice() == null) {
            System.err.println("Giá không được null.");
            return false;
        }
        if (product.getSellPrice().compareTo(product.getCostPrice()) < 0) {
            System.err.println("Giá bán không thể thấp hơn giá gốc.");
            return false;
        }
        return true;
    }
}
