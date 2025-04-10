package BUS;

import DAO.ProductCategoryDAO;
import MODEL.ProductCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ProductCategoryBUS {
    private final ProductCategoryDAO dao = new ProductCategoryDAO();

    // Lấy danh sách loại sản phẩm
    public List<ProductCategory> getAllCategories() {
        return dao.getAllCategories();
    }

    // Trả về danh sách Observable dùng cho ComboBox, TableView
    public ObservableList<ProductCategory> getObservableCategories() {
        return FXCollections.observableArrayList(dao.getAllCategories());
    }

    // Thêm mới
    public boolean addCategory(ProductCategory category) {
        if (isValidCategory(category)) {
            return dao.addCategory(category);
        }
        return false;
    }

    // Cập nhật
    public boolean updateCategory(ProductCategory category) {
        if (isValidCategory(category)) {
            return dao.updateCategory(category);
        }
        return false;
    }

    // Xóa
    public boolean deleteCategory(int categoryId) {
        return dao.deleteCategory(categoryId);
    }

    // Tìm kiếm
    public List<ProductCategory> searchCategories(String keyword) {
        return dao.searchCategories(keyword);
    }

    // Kiểm tra dữ liệu hợp lệ
    private boolean isValidCategory(ProductCategory category) {
        if (category.getCategory_Name() == null || category.getCategory_Name().trim().isEmpty()) {
            System.err.println("Tên loại sản phẩm không được để trống.");
            return false;
        }
        return true;
    }
}
