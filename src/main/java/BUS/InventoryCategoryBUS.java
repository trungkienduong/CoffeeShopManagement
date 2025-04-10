package BUS;

import DAO.InventoryCategoryDAO;
import MODEL.InventoryCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class InventoryCategoryBUS {
    private final InventoryCategoryDAO dao = new InventoryCategoryDAO();

    // Lấy danh sách tất cả danh mục
    public List<InventoryCategory> getAllInventoryCategories() {
        return dao.getAllInventoryCategories();
    }

    // ObservableList để dùng cho TableView hoặc ComboBox
    public ObservableList<InventoryCategory> getObservableInventoryCategories() {
        return FXCollections.observableArrayList(dao.getAllInventoryCategories());
    }

    // Thêm danh mục
    public boolean addInventoryCategory(InventoryCategory category) {
        if (isValidCategory(category)) {
            return dao.addInventoryCategory(category);
        }
        return false;
    }

    // Cập nhật danh mục
    public boolean updateInventoryCategory(InventoryCategory category) {
        if (isValidCategory(category)) {
            return dao.updateInventoryCategory(category);
        }
        return false;
    }

    // Xoá danh mục
    public boolean deleteInventoryCategory(int categoryId) {
        return dao.deleteInventoryCategory(categoryId);
    }

    // Tìm kiếm
    public List<InventoryCategory> searchInventoryCategories(String keyword) {
        return dao.searchInventoryCategories(keyword);
    }

    // Kiểm tra hợp lệ
    private boolean isValidCategory(InventoryCategory category) {
        if (category.getCategory_Name() == null || category.getCategory_Name().trim().isEmpty()) {
            System.err.println("Tên danh mục không được để trống.");
            return false;
        }
        return true;
    }
}
