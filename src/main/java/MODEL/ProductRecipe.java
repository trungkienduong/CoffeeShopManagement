package MODEL;

public class ProductRecipe {
   /* private int productId;
    private int itemId;
    private double quantityUsed;
    
    // Đối tượng liên kết
    private Product product;
    private Inventory item;

    // Default constructor
    public ProductRecipe() {
    }

    // Parameterized constructor without linked objects
    public ProductRecipe(int productId, int itemId, double quantityUsed) {
        this.productId = productId;
        this.itemId = itemId;
        this.quantityUsed = quantityUsed;
    }

    // Constructor with linked objects
    public ProductRecipe(Product product, Inventory item, double quantityUsed) {
        this.productId = product.getProductId();
        this.product = product;
        this.itemId = item.getItemId();
        this.item = item;
        this.quantityUsed = quantityUsed;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(double quantityUsed) {
        if (quantityUsed <= 0) {
            throw new IllegalArgumentException("Quantity used must be positive");
        }
        this.quantityUsed = quantityUsed;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.productId = product.getProductId();
    }

    public Inventory getItem() {
        return item;
    }

    public void setItem(Inventory item) {
        this.item = item;
        this.itemId = item.getItemId();
    }

    // Helper method để kiểm tra có đủ nguyên liệu không
    public boolean hasEnoughIngredient() {
        if (item == null) return false;
        return item.getQuantity() >= this.quantityUsed;
    }

    // Helper method để trừ nguyên liệu khi làm món
    public void useIngredient() {
        if (item == null) {
            throw new IllegalStateException("No inventory item linked");
        }
        if (!hasEnoughIngredient()) {
            throw new IllegalStateException("Not enough ingredient in inventory");
        }
        item.subtractQuantity(this.quantityUsed);
    }

    @Override
    public String toString() {
        return "ProductRecipe{" +
                "productId=" + productId +
                ", itemId=" + itemId +
                ", quantityUsed=" + quantityUsed +
                ", product=" + (product != null ? product.getProductName() : "null") +
                ", item=" + (item != null ? item.getItemName() : "null") +
                '}';
    }*/
}
