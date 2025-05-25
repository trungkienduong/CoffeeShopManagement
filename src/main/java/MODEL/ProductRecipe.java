package MODEL;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductRecipe {
    // Khóa chính composite: productId + itemName được biểu diễn qua product và item
    private Product product;    // Liên kết đến Product (chứa productId)
    private Inventory item;     // Liên kết đến Inventory (chứa itemName)

    private BigDecimal quantityUsed;

    public ProductRecipe() {
    }

    public ProductRecipe(Product product, Inventory item, BigDecimal quantityUsed) {
        this.product = product;
        this.item = item;
        this.quantityUsed = quantityUsed;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Inventory getItem() {
        return item;
    }

    public void setItem(Inventory item) {
        this.item = item;
    }

    public BigDecimal getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(BigDecimal quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    @Override
    public String toString() {
        return "ProductRecipe{" +
                "productId=" + (product != null ? product.getProductId() : "null") +
                ", itemName=" + (item != null ? item.getItemName() : "null") +
                ", quantityUsed=" + quantityUsed +
                '}';
    }

    // Override equals và hashCode theo khóa chính để dễ quản lý collection

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductRecipe)) return false;
        ProductRecipe that = (ProductRecipe) o;
        return Objects.equals(product != null ? product.getProductId() : null, that.product != null ? that.product.getProductId() : null)
                && Objects.equals(item != null ? item.getItemName() : null, that.item != null ? that.item.getItemName() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product != null ? product.getProductId() : null, item != null ? item.getItemName() : null);
    }
}
