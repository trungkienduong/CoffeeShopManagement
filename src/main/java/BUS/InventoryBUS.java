package BUS;

import DAO.InventoryDAO;
import MODEL.Inventory;

import java.math.BigDecimal;
import java.util.Collections;
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

    public boolean insertItem(Inventory item) {
        if (!isValidItem(item)) return false;
        try {
            return inventoryDAO.insert(item);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateItem(Inventory item) {
        if (!isValidItem(item)) return false;
        try {
            return inventoryDAO.update(item);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateQuantity(String itemName, BigDecimal newQuantity) {
        if (itemName == null || itemName.trim().isEmpty() || newQuantity == null)
            return false;

        try {
            return inventoryDAO.updateQuantity(itemName, newQuantity);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteItem(String itemName) throws Exception {
        if (itemName == null || itemName.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name must not be empty.");
        }

        if (inventoryDAO.isInventoryItemInUse(itemName)) {
            throw new IllegalStateException("Cannot delete: this item is currently used in a recipe.");
        }

        return inventoryDAO.delete(itemName);
    }







    public List<Inventory> getAll() {
        try {
            List<Inventory> list = inventoryDAO.getAll();
            return list != null ? list : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Inventory findByName(String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) return null;

        try {
            return inventoryDAO.findByName(itemName);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Inventory> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return Collections.emptyList();

        try {
            List<Inventory> list = inventoryDAO.searchByName(keyword);
            return list != null ? list : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public boolean checkQuantity(String itemName, BigDecimal quantity) {
        if (itemName == null || itemName.trim().isEmpty() || quantity == null) return false;

        try {
            return inventoryDAO.checkQuantity(itemName, quantity);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidItem(Inventory item) {
        if (item == null) return false;
        if (item.getItemName() == null || item.getItemName().trim().isEmpty()) return false;
        if (item.getQuantity() == null || item.getQuantity().compareTo(BigDecimal.ZERO) < 0) return false;
        if (item.getCostPrice() == null || item.getCostPrice().compareTo(BigDecimal.ZERO) < 0) return false;
        if (item.getIngredientCategory() == null) return false;
        if (item.getUnit() == null) return false;
        return true;
    }

    public Inventory findByItemName(String itemName) {
        for (Inventory inv : getAll()) {
            if (inv.getItemName().equalsIgnoreCase(itemName)) {
                return inv;
            }
        }
        return null;
    }


}
