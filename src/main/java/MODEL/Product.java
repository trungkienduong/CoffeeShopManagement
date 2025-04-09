package MODEL;

import java.math.BigDecimal;

public class Product {
    private int Product_ID;
    private String Name;
    private int Category_ID;
    private BigDecimal CostPrice;
    private BigDecimal SellPrice;

    // Constructor với tất cả các tham số


    public Product(int product_ID, String name, int category_ID,
                   BigDecimal costPrice, BigDecimal sellPrice) {
        Product_ID = product_ID;
        Name = name;
        Category_ID = category_ID;
        CostPrice = costPrice;
        SellPrice = sellPrice;
    }

    public int getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(int product_ID) {
        Product_ID = product_ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCategory_ID() {
        return Category_ID;
    }

    public void setCategory_ID(int category_ID) {
        Category_ID = category_ID;
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
        return "Product{" +
                "Product_ID=" + Product_ID +
                ", Name='" + Name + '\'' +
                ", Category_ID=" + Category_ID +
                ", CostPrice=" + CostPrice +
                ", SellPrice=" + SellPrice +
                '}';
    }
}
