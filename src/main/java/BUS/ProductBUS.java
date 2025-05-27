package BUS;

import DAO.ProductDAO;
import DAO.ProductRecipeDAO;
import DAO.CategoryDAO;
import MODEL.Category;
import MODEL.Product;

import java.util.List;

public class ProductBUS {
    private final ProductDAO productDAO;
    private final ProductRecipeDAO productRecipeDAO;
    private final CategoryDAO categoryDAO;

    private static ProductBUS instance;

    private ProductBUS() {
        productDAO = ProductDAO.getInstance();
        productRecipeDAO = ProductRecipeDAO.getInstance();
        categoryDAO = CategoryDAO.getInstance();
    }

    public static ProductBUS getInstance() {
        if (instance == null) {
            instance = new ProductBUS();
        }
        return instance;
    }

    /**
     * Kiểm tra tên sản phẩm đã tồn tại chưa (để tránh trùng lặp).
     */
    public boolean isProductNameExists(String productName) {
        List<Product> existingProducts = productDAO.searchByName(productName);
        return existingProducts.stream()
                .anyMatch(p -> p.getProductName().equalsIgnoreCase(productName));
    }

    /**
     * Thêm sản phẩm mới nếu tên chưa tồn tại.
     */
    public boolean addProduct(Product product) {
        if (isProductNameExists(product.getProductName())) {
            System.out.println("Sản phẩm đã tồn tại!");
            return false;
        }
        return productDAO.insert(product);
    }

    /**
     * Cập nhật thông tin sản phẩm.
     */
    public boolean updateProduct(Product product) {
        return productDAO.update(product);
    }

    /**
     * Xóa sản phẩm theo ID, đồng thời xóa luôn các công thức liên quan theo categoryId của sản phẩm.
     */
    public boolean deleteProduct(int productId) {
        // Lấy sản phẩm để biết categoryId
        Product product = productDAO.findById(productId);
        if (product == null) {
            System.out.println("Sản phẩm không tồn tại!");
            return false;
        }

        // Xóa công thức liên quan theo categoryId
        int categoryId = product.getCategoryId();
        productRecipeDAO.deleteByCategory(categoryId);

        // Xóa sản phẩm
        return productDAO.delete(productId);
    }

    /**
     * Lấy tất cả sản phẩm.
     */
    public List<Product> getAllProducts() {
        return productDAO.getAll();
    }

    /**
     * Lấy sản phẩm theo ID.
     */
    public Product getProductById(int productId) {
        return productDAO.findById(productId);
    }

    /**
     * Lấy danh sách sản phẩm theo categoryId.
     */
    public List<Product> getProductsByCategoryId(int categoryId) {
        return productDAO.findByCategoryId(categoryId);
    }

    /**
     * Tìm kiếm sản phẩm theo từ khóa tên.
     */
    public List<Product> searchProductsByName(String keyword) {
        return productDAO.searchByName(keyword);
    }

    /**
     * Lấy tên category từ categoryId (tiện để hiển thị ở UI).
     */
    public String getCategoryNameById(int categoryId) {
        Category category = categoryDAO.findById(categoryId);
        return category != null ? category.getCategoryName() : "Unknown";
    }
}
