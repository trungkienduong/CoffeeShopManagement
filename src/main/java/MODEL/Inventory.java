package MODEL;

import java.math.BigDecimal;

public class Inventory {

    private String itemName;
    private BigDecimal quantity;
    private BigDecimal costPrice;

    // Đối tượng liên kết
    private Category category;
    private UnitCategory unit;

    // Default constructor
    public Inventory() {
    }

    public Inventory(String itemName, BigDecimal quantity, BigDecimal costPrice, Category category, UnitCategory unit) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.category = category;
        this.unit = unit;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
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
                "itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", costPrice=" + costPrice +
                ", category=" + category +
                ", unit=" + unit +
                '}';
    }
}
