package MODEL;

public class Inventory {
    private int itemId;
    private String itemName;
    private String imagePath;
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

    public Inventory(int itemId, String itemName, String imagePath, int categoryId, double quantity, int unitId, double costPrice, Category category, UnitCategory unit) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
        this.quantity = quantity;
        this.unitId = unitId;
        this.costPrice = costPrice;
        this.category = category;
        this.unit = unit;
    }

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
        this.costPrice = costPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public UnitCategory getUnit() {
        return unit;
    }

    public void setUnit(UnitCategory unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", categoryId=" + categoryId +
                ", quantity=" + quantity +
                ", unitId=" + unitId +
                ", costPrice=" + costPrice +
                ", category=" + category +
                ", unit=" + unit +
                '}';
    }
}
