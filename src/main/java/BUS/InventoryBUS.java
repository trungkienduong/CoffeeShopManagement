package BUS;

import DAO.InventoryDAO;
import MODEL.Inventory;
import java.sql.Connection;
import java.util.List;

public class InventoryBUS {

    private InventoryDAO inventoryDAO;

    public InventoryBUS(Connection connection) {
        this.inventoryDAO = new InventoryDAO(connection);
    }

    // Thêm một sản phẩm mới vào kho
    public boolean addInventory(Inventory inventory) {
        // Kiểm tra dữ liệu trước khi thêm vào
        if (inventory.getItemName() == null || inventory.getItemName().isEmpty()) {
            System.out.println("Tên sản phẩm không được để trống.");
            return false;
        }
        if (inventory.getQuantity() < 0) {
            System.out.println("Số lượng phải lớn hơn hoặc bằng 0.");
            return false;
        }
        if (inventory.getCostPrice().compareTo(inventory.getSellPrice()) > 0) {
            System.out.println("Giá bán phải lớn hơn hoặc bằng giá vốn.");
            return false;
        }
        return inventoryDAO.addInventory(inventory);
    }

    // Cập nhật thông tin sản phẩm trong kho
    public boolean updateInventory(Inventory inventory) {
        // Kiểm tra dữ liệu trước khi cập nhật
        if (inventory.getItemName() == null || inventory.getItemName().isEmpty()) {
            System.out.println("Tên sản phẩm không được để trống.");
            return false;
        }
        if (inventory.getQuantity() < 0) {
            System.out.println("Số lượng phải lớn hơn hoặc bằng 0.");
            return false;
        }
        if (inventory.getCostPrice().compareTo(inventory.getSellPrice()) > 0) {
            System.out.println("Giá bán phải lớn hơn hoặc bằng giá vốn.");
            return false;
        }
        return inventoryDAO.updateInventory(inventory);
    }

    // Xóa một sản phẩm khỏi kho
    public boolean deleteInventory(int itemID) {
        return inventoryDAO.deleteInventory(itemID);
    }

    // Lấy tất cả sản phẩm trong kho
    public List<Inventory> getAllInventory() {
        return inventoryDAO.getAllInventory();
    }

    // Lấy thông tin một sản phẩm theo ID
    public Inventory getInventoryById(int itemID) {
        return inventoryDAO.getInventoryById(itemID);
    }

    // Cập nhật số lượng tồn kho khi bán ra
    public boolean updateInventoryQuantity(int itemID, int quantity) {
        if (quantity <= 0) {
            System.out.println("Số lượng bán ra phải lớn hơn 0.");
            return false;
        }
        return inventoryDAO.updateInventoryQuantity(itemID, quantity);
    }
}
