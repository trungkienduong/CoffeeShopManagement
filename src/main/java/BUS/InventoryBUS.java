package BUS;

import DAO.InventoryDAO;
import MODEL.Inventory;
import java.util.List;

public class InventoryBUS {
    private InventoryDAO inventoryDAO;

    public InventoryBUS() {
        this.inventoryDAO = new InventoryDAO();
    }

    // Lấy danh sách tất cả nguyên liệu trong kho
    public List<Inventory> getAllInventory() {
        return inventoryDAO.getAllInventory();
    }

    // Thêm nguyên liệu mới
    public boolean addInventory(Inventory item) {
        return inventoryDAO.addInventory(item);
    }

    // Cập nhật thông tin nguyên liệu
    public boolean updateInventory(Inventory item) {
        return inventoryDAO.updateInventory(item);
    }

    // Xóa nguyên liệu theo ID
    public boolean deleteInventory(int itemId) {
        return inventoryDAO.deleteInventory(itemId);
    }

    // Tìm kiếm nguyên liệu theo tên hoặc loại
    public List<Inventory> searchInventory(String keyword) {
        return inventoryDAO.searchInventory(keyword);
    }
}
