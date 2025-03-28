package MODEL;

import java.math.BigDecimal;

public class Product {
    private int ProductID;
    private String Name;
    private int CategoryID;
    private BigDecimal CostPrice;
    private BigDecimal SellPrice;

    // Constructor với tất cả các tham số


    public Product(int productID, String name, int categoryID, BigDecimal costPrice, BigDecimal sellPrice) {
        ProductID = productID;
        Name = name;
        CategoryID = categoryID;
        CostPrice = costPrice;
        SellPrice = sellPrice;
    }

    // Getter and Setter cho ProductID


    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
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
                "ProductID=" + ProductID +
                ", Name='" + Name + '\'' +
                ", CategoryID=" + CategoryID +
                ", CostPrice=" + CostPrice +
                ", SellPrice=" + SellPrice +
                '}';
    }
}
