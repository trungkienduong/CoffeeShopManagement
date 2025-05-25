package BUS;

import DAO.InventoryDAO;
import MODEL.Inventory;

import java.math.BigDecimal;
import java.util.List;

public class InventoryBUS {
    private static InventoryBUS instance;
    private final InventoryDAO inventoryDAO;

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
            }
        } else {
            System.out.println("Invalid item.");
        }
        return false;
    }

    // -------------------- UPDATE --------------------
    public boolean updateItem(Inventory item) {
        if (item != null && isValidItem(item)) {
            try {
                return inventoryDAO.update(item);
            } catch (Exception e) {
                System.err.println("Error updating inventory item: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid item.");
        }
        return false;
    }

    // -------------------- UPDATE QUANTITY --------------------
    public boolean updateQuantity(String itemName, BigDecimal newQuantity) {
        try {
            return inventoryDAO.updateQuantity(itemName, newQuantity);
        } catch (Exception e) {
            System.err.println("Error updating inventory quantity: " + e.getMessage());
            return false;
        }
    }

    // -------------------- DELETE --------------------
    public boolean deleteItem(String itemName) {
        try {
            return inventoryDAO.delete(itemName);
        } catch (Exception e) {
            System.err.println("Error deleting inventory item: " + e.getMessage());
            return false;
        }
    }

    // -------------------- GET ALL --------------------
    public List<Inventory> getAll() {
        try {
            return inventoryDAO.getAll();
        } catch (Exception e) {
            System.err.println("Error fetching all inventory items: " + e.getMessage());
            return null;
        }
    }

    // -------------------- FIND BY NAME --------------------
    public Inventory findByName(String itemName) {
        try {
            return inventoryDAO.findByName(itemName);
        } catch (Exception e) {
            System.err.println("Error finding inventory item by name: " + e.getMessage());
            return null;
        }
    }

    // -------------------- FIND BY CATEGORY --------------------
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
    public boolean checkQuantity(String itemName, BigDecimal quantity) {
        try {
            return inventoryDAO.checkQuantity(itemName, quantity);
        } catch (Exception e) {
            System.err.println("Error checking inventory quantity: " + e.getMessage());
            return false;
        }
    }

    // -------------------- VALIDATE ITEM --------------------
    private boolean isValidItem(Inventory item) {
        if (item.getItemName() == null || item.getItemName().trim().isEmpty()) {
            System.out.println("Item name is required.");
            return false;
        }
        if (item.getQuantity() == null || item.getQuantity().compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Quantity cannot be negative.");
            return false;
        }
        if (item.getCostPrice() == null || item.getCostPrice().compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Cost price cannot be negative.");
            return false;
        }
        return true;
    }
}
