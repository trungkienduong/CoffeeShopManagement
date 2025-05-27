package MODEL;

import java.math.BigDecimal;

public class Product {
    private int productId;
    private String productName;
    private int categoryId;        // Dùng categoryId thay vì Category object
    private BigDecimal sellPrice;
    private String imagePath;
    private String description;   // Thêm trường mô tả sản phẩm

    public Product() {
    }

    public Product(int productId, String productName, int categoryId, BigDecimal sellPrice, String imagePath, String description) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.sellPrice = sellPrice;
        this.imagePath = imagePath;
        this.description = description;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                ", sellPrice=" + sellPrice +
                ", imagePath='" + imagePath + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
