package BUS;

import DAO.InventoryDAO;
import MODEL.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class InventoryBUS {
    private final InventoryDAO dao = new InventoryDAO();

    // Lấy tất cả mặt hàng trong kho
    public List<Inventory> getAllInventoryItems() {
        return dao.getAllInventoryItems();
    }

    // ObservableList cho TableView hoặc ComboBox
    public ObservableList<Inventory> getObservableInventoryItems() {
        return FXCollections.observableArrayList(dao.getAllInventoryItems());
    }

    // Thêm mặt hàng
    public boolean addInventoryItem(Inventory item) {
        if (isValidInventory(item)) {
            return dao.addInventoryItem(item);
        }
        return false;
    }

    // Cập nhật mặt hàng
    public boolean updateInventoryItem(Inventory item) {
        if (isValidInventory(item)) {
            return dao.updateInventoryItem(item);
        }
        return false;
    }

    // Xoá mặt hàng
    public boolean deleteInventoryItem(int itemId) {
        return dao.deleteInventoryItem(itemId);
    }

    // Tìm kiếm
    public List<Inventory> searchInventoryItems(String keyword) {
        return dao.searchInventoryItems(keyword);
    }

    // Kiểm tra hợp lệ đơn giản
    private boolean isValidInventory(Inventory item) {
        if (item.getItem_Name() == null || item.getItem_Name().trim().isEmpty()) {
            System.err.println("Tên mặt hàng không được để trống.");
            return false;
        }
        if (item.getQuantity() < 0) {
            System.err.println("Số lượng không hợp lệ.");
            return false;
        }
        if (item.getCostPrice() == null || item.getSellPrice() == null) {
            System.err.println("Giá không được null.");
            return false;
        }
        if (item.getCostPrice().compareTo(item.getSellPrice()) > 0) {
            System.err.println("Giá bán không được thấp hơn giá gốc.");
            return false;
        }
        return true;
    }
}
