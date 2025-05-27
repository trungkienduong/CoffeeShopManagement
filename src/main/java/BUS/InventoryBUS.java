package BUS;

import DAO.InventoryDAO;
import MODEL.Inventory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventoryBUS {
    private static final Logger LOGGER = Logger.getLogger(InventoryBUS.class.getName());
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

    // INSERT
    public boolean insertItem(Inventory item) {
        if (!isValidItem(item)) return false;
        try {
            return inventoryDAO.insert(item);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inserting inventory item", e);
            return false;
        }
    }

    // UPDATE
    public boolean updateItem(Inventory item) {
        if (!isValidItem(item)) return false;
        try {
            return inventoryDAO.update(item);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating inventory item", e);
            return false;
        }
    }

    // UPDATE QUANTITY
    public boolean updateQuantity(String itemName, BigDecimal newQuantity) {
        if (itemName == null || itemName.trim().isEmpty() || newQuantity == null)
            return false;

        try {
            return inventoryDAO.updateQuantity(itemName, newQuantity);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating inventory quantity", e);
            return false;
        }
    }

    // DELETE
    public boolean deleteItem(String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) return false;

        try {
            return inventoryDAO.delete(itemName);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting inventory item", e);
            return false;
        }
    }

    // GET ALL
    public List<Inventory> getAll() {
        try {
            List<Inventory> list = inventoryDAO.getAll();
            return list != null ? list : Collections.emptyList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching all inventory items", e);
            return Collections.emptyList();
        }
    }


    // FIND BY NAME
    public Inventory findByName(String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) return null;

        try {
            return inventoryDAO.findByName(itemName);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding inventory item by name", e);
            return null;
        }
    }

    // SEARCH BY NAME
    public List<Inventory> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return Collections.emptyList();

        try {
            List<Inventory> list = inventoryDAO.searchByName(keyword);
            return list != null ? list : Collections.emptyList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error searching inventory items by name", e);
            return Collections.emptyList();
        }
    }

    // CHECK QUANTITY
    public boolean checkQuantity(String itemName, BigDecimal quantity) {
        if (itemName == null || itemName.trim().isEmpty() || quantity == null) return false;

        try {
            return inventoryDAO.checkQuantity(itemName, quantity);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error checking inventory quantity", e);
            return false;
        }
    }

    // VALIDATE ITEM
    private boolean isValidItem(Inventory item) {
        if (item == null) {
            LOGGER.warning("Item is null.");
            return false;
        }
        if (item.getItemName() == null || item.getItemName().trim().isEmpty()) {
            LOGGER.warning("Item name is required.");
            return false;
        }
        if (item.getQuantity() == null || item.getQuantity().compareTo(BigDecimal.ZERO) < 0) {
            LOGGER.warning("Quantity must be non-negative.");
            return false;
        }
        if (item.getCostPrice() == null || item.getCostPrice().compareTo(BigDecimal.ZERO) < 0) {
            LOGGER.warning("Cost price must be non-negative.");
            return false;
        }
        if (item.getIngredientCategory() == null) {
            LOGGER.warning("Ingredient category is required.");
            return false;
        }
        if (item.getUnit() == null) {
            LOGGER.warning("Unit category is required.");
            return false;
        }
        return true;
    }
}
