package MODEL;

import java.math.BigDecimal;

public class Inventory {
    private int ItemID;
    private String ItemName;
    private String Category;
    private int Quantity;
    private String Unit;
    private double SellPrice;

    public Inventory(int ItemID, String ItemName, String Category, int Quantity, String Unit, double SellPrice) {
        this.ItemID = ItemID;
        this.ItemName = ItemName;
        this.Category = Category;
        this.Quantity = Quantity;
        this.Unit = Unit;
        this.SellPrice = SellPrice;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public double getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(double sellPrice) {
        SellPrice = sellPrice;
    }
}
