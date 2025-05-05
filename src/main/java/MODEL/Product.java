package MODEL;

public class Product {
    /*private int productId;
    private String productName;
    private int categoryId;
    private double sellPrice;
    
    // Đối tượng liên kết
    private Category category;

    // Default constructor
    public Product() {
    }

    // Parameterized constructor without category object
    public Product(int productId, String productName, int categoryId, double sellPrice) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.sellPrice = sellPrice;
    }

    // Constructor with category object
    public Product(int productId, String productName, Category category, double sellPrice) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = category.getCategoryId();
        this.category = category;
        this.sellPrice = sellPrice;
    }

    // Constructor without ID (for creating new products)
    public Product(String productName, Category category, double sellPrice) {
        this.productName = productName;
        this.categoryId = category.getCategoryId();
        this.category = category;
        this.sellPrice = sellPrice;
    }

    // Getters and Setters
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
