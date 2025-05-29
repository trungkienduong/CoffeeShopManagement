package MODEL;

import java.math.BigDecimal;

public class Inventory {
    private String itemName;
    private BigDecimal quantity;
    private BigDecimal costPrice;

    // Liên kết đúng với bảng INVENTORY
    private IngredientCategory ingredientCategory;
    private UnitCategory unit;

    public Inventory() {}

    public Inventory(String itemName, BigDecimal quantity, BigDecimal costPrice,
                     IngredientCategory ingredientCategory, UnitCategory unit) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.ingredientCategory = ingredientCategory;
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

    public IngredientCategory getIngredientCategory() {
        return ingredientCategory;
    }

    public void setIngredientCategory(IngredientCategory ingredientCategory) {
        this.ingredientCategory = ingredientCategory;
    }

    public UnitCategory getUnit() {
        return unit;
    }

    public void setUnit(UnitCategory unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return this.getItemName();
    }

}
