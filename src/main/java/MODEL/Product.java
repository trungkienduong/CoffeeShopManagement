package MODEL;

public class Product {
   /* private int productId;
    private String productName;
    private int categoryId;
    private double sellPrice;
    
    // Đối tượng liên kết
    private Category category;

    // Default constructor
    public Product() {
    }

    public Product(int productId, String productName, int categoryId, double sellPrice, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        if (sellPrice < 0) {
            throw new IllegalArgumentException("Sell price cannot be negative");
        }
        this.sellPrice = sellPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        if (!category.isProductCategory()) {
            throw new IllegalArgumentException("Category must be a Product category (type 'P')");
        }
        this.category = category;
        this.categoryId = category.getCategoryId();
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                ", sellPrice=" + sellPrice +
                ", category=" + (category != null ? category.getCategoryName() : "null") +
                '}';
    }*/
}
