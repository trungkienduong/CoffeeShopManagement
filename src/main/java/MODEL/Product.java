package MODEL;

public class Product {
    private int ProductID;
    private String Name;
    private String Category;
    private double CostPrice;
    private double SellPrice;

    public Product (int ProductID, String Name, String Category, double CostPrice, double SellPrice) {

            this.ProductID = ProductID;
            this.Name = Name;
            this. Category = Category;
            this. CostPrice = CostPrice;
            this.SellPrice = SellPrice;
    }

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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public double getCostPrice() {
        return CostPrice;
    }

    public void setCostPrice(double costPrice) {
        CostPrice = costPrice;
    }

    public double getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(double sellPrice) {
        SellPrice = sellPrice;
    }
}
