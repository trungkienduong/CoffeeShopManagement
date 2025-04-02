package BUS;

import DAO.InventoryCategoryDAO;
import MODEL.InventoryCategory;
import java.sql.Connection;
import java.util.List;

public class InventoryCategoryBUS {

    private InventoryCategoryDAO inventoryCategoryDAO;

    public InventoryCategoryBUS(Connection connection) {
        this.inventoryCategoryDAO = new InventoryCategoryDAO(connection);
    }

    // Thêm một danh mục nguyên liệu mới
    public boolean addInventoryCategory(InventoryCategory inventoryCategory) {
        // Kiểm tra dữ liệu trước khi thêm vào
        if (inventoryCategory.getCategoryName() == null || inventoryCategory.getCategoryName().isEmpty()) {
            System.out.println("Tên danh mục không được để trống.");
            return false;
        }
        if (inventoryCategory.getDescription() == null || inventoryCategory.getDescription().isEmpty()) {
            System.out.println("Mô tả không được để trống.");
            return false;
        }
        return inventoryCategoryDAO.addInventoryCategory(inventoryCategory);
    }

    // Lấy tất cả danh mục nguyên liệu
    public List<InventoryCategory> getAllInventoryCategories() {
        return inventoryCategoryDAO.getAllInventoryCategories();
    }

    // Cập nhật thông tin danh mục nguyên liệu
    public boolean updateInventoryCategory(InventoryCategory inventoryCategory) {
        // Kiểm tra dữ liệu trước khi cập nhật
        if (inventoryCategory.getCategoryName() == null || inventoryCategory.getCategoryName().isEmpty()) {
            System.out.println("Tên danh mục không được để trống.");
            return false;
        }
        if (inventoryCategory.getDescription() == null || inventoryCategory.getDescription().isEmpty()) {
            System.out.println("Mô tả không được để trống.");
            return false;
        }
        return inventoryCategoryDAO.updateInventoryCategory(inventoryCategory);
    }

    // Xóa danh mục nguyên liệu
    public boolean deleteInventoryCategory(int categoryID) {
        return inventoryCategoryDAO.deleteInventoryCategory(categoryID);
    }

    // Lấy thông tin danh mục nguyên liệu theo ID
    public InventoryCategory getInventoryCategoryByID(int categoryID) {
        return inventoryCategoryDAO.getInventoryCategoryByID(categoryID);
    }
}
