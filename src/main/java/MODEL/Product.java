package MODEL;

public class Product {
    private int productId;
    private String productName;
    private double sellPrice;

    // Đối tượng liên kết
    private Category category;

    // Default constructor
    public Product() {
    }

    public Product(int productId, String productName, double sellPrice, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.sellPrice = sellPrice;
        this.category = category;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", sellPrice=" + sellPrice +
                ", category=" + category +
                '}';
    }
}
