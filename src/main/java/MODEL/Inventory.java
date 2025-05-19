package MODEL;

public class Inventory {
    private int itemId;
    private String itemName;
    private String imagePath;
    private double quantity;
    private double costPrice;
    
    // Đối tượng liên kết
    private Category category;
    private UnitCategory unit;

    // Default constructor
    public Inventory() {
    }

    public Inventory(int itemId, String itemName, String imagePath, double quantity, double costPrice, Category category, UnitCategory unit) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.imagePath = imagePath;
        this.quantity = quantity;
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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
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
                ", quantity=" + quantity +
                ", costPrice=" + costPrice +
                ", category=" + category +
                ", unit=" + unit +
                '}';
    }
}
