package MODEL;

import java.math.BigDecimal;

public class Inventory {
    private int Item_ID;
    private String Item_Name;
    private int Category_ID;
    private int Quantity;
    private int Unit_ID;
    private BigDecimal CostPrice;
    private BigDecimal SellPrice;


    // Constructor với tất cả các tham số


    public Inventory(int item_ID, String item_Name, int category_ID,
                     int quantity, int unit_ID, BigDecimal costPrice,
                     BigDecimal sellPrice) {
        Item_ID = item_ID;
        Item_Name = item_Name;
        Category_ID = category_ID;
        Quantity = quantity;
        Unit_ID = unit_ID;
        CostPrice = costPrice;
        SellPrice = sellPrice;
    }

    public int getItem_ID() {
        return Item_ID;
    }

    public void setItem_ID(int item_ID) {
        Item_ID = item_ID;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public int getCategory_ID() {
        return Category_ID;
    }

    public void setCategory_ID(int category_ID) {
        Category_ID = category_ID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getUnit_ID() {
        return Unit_ID;
    }

    public void setUnit_ID(int unit_ID) {
        Unit_ID = unit_ID;
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
                "Item_ID=" + Item_ID +
                ", Item_Name='" + Item_Name + '\'' +
                ", Category_ID=" + Category_ID +
                ", Quantity=" + Quantity +
                ", Unit_ID=" + Unit_ID +
                ", CostPrice=" + CostPrice +
                ", SellPrice=" + SellPrice +
                '}';
    }
}
