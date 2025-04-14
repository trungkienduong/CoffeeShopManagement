package MODEL;

public class Inventory {
    private int itemId;
    private String itemName;
    private int categoryId;
    private double quantity;
    private int unitId;
    private double costPrice;
    
    // Đối tượng liên kết
    private Category category;
    private UnitCategory unit;

    // Default constructor
    public Inventory() {
    }

    // Parameterized constructor without linked objects
    public Inventory(int itemId, String itemName, int categoryId, double quantity, 
                    int unitId, double costPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.categoryId = categoryId;
        this.quantity = quantity;
        this.unitId = unitId;
        this.costPrice = costPrice;
    }

    // Constructor with linked objects
    public Inventory(int itemId, String itemName, Category category, double quantity, 
                    UnitCategory unit, double costPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.categoryId = category.getCategoryId();
        this.category = category;
        this.quantity = quantity;
        this.unitId = unit.getUnitId();
        this.unit = unit;
        this.costPrice = costPrice;
    }

    // Constructor without ID (for creating new items)
    public Inventory(String itemName, Category category, double quantity, 
                    UnitCategory unit, double costPrice) {
        this.itemName = itemName;
        this.categoryId = category.getCategoryId();
        this.category = category;
        this.quantity = quantity;
        this.unitId = unit.getUnitId();
        this.unit = unit;
        this.costPrice = costPrice;
    }

    // Getters and Setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        if (costPrice < 0) {
            throw new IllegalArgumentException("Cost price cannot be negative");
        }
        this.costPrice = costPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        if (!category.isInventoryCategory()) {
            throw new IllegalArgumentException("Category must be an Inventory category (type 'I')");
        }
        this.category = category;
        this.categoryId = category.getCategoryId();
    }

    public UnitCategory getUnit() {
        return unit;
    }

    public void setUnit(UnitCategory unit) {
        this.unit = unit;
        this.unitId = unit.getUnitId();
    }

    // Helper methods
    public void addQuantity(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to add must be positive");
        }
        this.quantity += amount;
    }

    public void subtractQuantity(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to subtract must be positive");
        }
        if (amount > this.quantity) {
            throw new IllegalArgumentException("Not enough quantity in inventory");
        }
        this.quantity -= amount;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", categoryId=" + categoryId +
                ", quantity=" + quantity +
                ", unitId=" + unitId +
                ", costPrice=" + costPrice +
                ", category=" + (category != null ? category.getCategoryName() : "null") +
                ", unit=" + (unit != null ? unit.getUnitName() : "null") +
                '}';
    }
}
