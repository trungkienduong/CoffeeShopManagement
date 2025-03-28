package MODEL;

import java.math.BigDecimal;

public class Inventory {
    private int ItemID;
    private String ItemName;
    private int CategoryID;
    private int Quantity;
    private int UnitID;
    private BigDecimal CostPrice;
    private BigDecimal SellPrice;


    // Constructor với tất cả các tham số


    public Inventory(int itemID, String itemName, int categoryID, int quantity, int unitID, BigDecimal costPrice, BigDecimal sellPrice) {
        ItemID = itemID;
        ItemName = itemName;
        CategoryID = categoryID;
        Quantity = quantity;
        UnitID = unitID;
        CostPrice = costPrice;
        SellPrice = sellPrice;
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

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getUnitID() {
        return UnitID;
    }

    public void setUnitID(int unitID) {
        UnitID = unitID;
    }

    public BigDecimal getCostPrice() {
        return CostPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        CostPrice = costPrice;
    }

    public BigDecimal getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        SellPrice = sellPrice;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "ItemID=" + ItemID +
                ", ItemName='" + ItemName + '\'' +
                ", CategoryID=" + CategoryID +
                ", Quantity=" + Quantity +
                ", UnitID=" + UnitID +
                ", CostPrice=" + CostPrice +
                ", SellPrice=" + SellPrice +
                '}';
    }
}
