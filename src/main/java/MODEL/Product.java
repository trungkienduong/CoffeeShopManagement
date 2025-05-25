package MODEL;

import java.math.BigDecimal;

public class Product {
    private int productId;
    private String productName;
    private Category category;  // giữ đối tượng Category
    private BigDecimal sellPrice;
    private String imagePath;

    public Product() {
    }

    public Product(int productId, String productName, Category category, BigDecimal sellPrice, String imagePath) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.sellPrice = sellPrice;
        this.imagePath = imagePath;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", category=" + (category != null ? category.getCategoryName() : "null") +
                ", sellPrice=" + sellPrice +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
