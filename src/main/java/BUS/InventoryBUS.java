package BUS;

import DAO.InventoryDAO;
import MODEL.Inventory;
import java.util.List;

public class InventoryBUS {
    private static InventoryBUS instance;
    private InventoryDAO inventoryDAO;

    private InventoryBUS() {
        inventoryDAO = InventoryDAO.getInstance();
    }

    public static InventoryBUS getInstance() {
        if (instance == null) {
            instance = new InventoryBUS();
        }
        return instance;
    }

    // -------------------- INSERT --------------------
    public boolean insertItem(Inventory item) {
        if (item != null && isValidItem(item)) {
            try {
                return inventoryDAO.insert(item);
            } catch (Exception e) {
                System.err.println("Error inserting inventory item: " + e.getMessage());
                return false;
            }
        }
        System.out.println("Invalid item.");
        return false;
    }

    // -------------------- UPDATE --------------------
    public boolean updateItem(Inventory item) {
        if (item != null && isValidItem(item)) {
            try {
                return inventoryDAO.update(item);
            } catch (Exception e) {
                System.err.println("Error updating inventory item: " + e.getMessage());
                return false;
            }
        }
        System.out.println("Invalid item.");
        return false;
    }

    // -------------------- UPDATE QUANTITY --------------------
    public boolean updateQuantity(int itemId, double newQuantity) {
        try {
            return inventoryDAO.updateQuantity(itemId, newQuantity);
        } catch (Exception e) {
            System.err.println("Error updating inventory quantity: " + e.getMessage());
            return false;
        }
    }

    // -------------------- DELETE --------------------
    public boolean deleteItem(int itemId) {
        try {
            return inventoryDAO.delete(itemId);
        } catch (Exception e) {
            System.err.println("Error deleting inventory item: " + e.getMessage());
            return false;
        }
    }

    // -------------------- GET ALL --------------------
    public List<Inventory> GetAll() {
        try {
            return inventoryDAO.getAll();
        } catch (Exception e) {
            System.err.println("Error fetching all inventory items: " + e.getMessage());
            return null;
        }
    }

    // -------------------- GET BY ID --------------------
    public Inventory FindById(int itemId) {
        try {
            return inventoryDAO.findById(itemId);
        } catch (Exception e) {
            System.err.println("Error finding inventory item by ID: " + e.getMessage());
            return null;
        }
    }

    // -------------------- GET BY CATEGORY --------------------
    public List<Inventory> findByCategory(int categoryId) {
        try {
            return inventoryDAO.findByCategory(categoryId);
        } catch (Exception e) {
            System.err.println("Error finding inventory items by category: " + e.getMessage());
            return null;
        }
    }

    // -------------------- SEARCH BY NAME --------------------
    public List<Inventory> searchByName(String keyword) {
        try {
            return inventoryDAO.searchByName(keyword);
        } catch (Exception e) {
            System.err.println("Error searching inventory items by name: " + e.getMessage());
            return null;
        }
    }

    // -------------------- CHECK QUANTITY --------------------
    public boolean checkQuantity(int itemId, double quantity) {
        try {
            return inventoryDAO.checkQuantity(itemId, quantity);
        } catch (Exception e) {
            System.err.println("Error checking inventory quantity: " + e.getMessage());
            return false;
        }
    }

    // -------------------- VALIDATE ITEM --------------------
    private boolean isValidItem(Inventory item) {
        if (item.getItemName() == null || item.getItemName().isEmpty()) {
            System.out.println("Item name is required.");
            return false;
        }
        if (item.getQuantity() < 0) {
            System.out.println("Quantity cannot be negative.");
            return false;
        }
        if (item.getCostPrice() < 0) {
            System.out.println("Cost price cannot be negative.");
            return false;
        }
        return true;
    }
}
