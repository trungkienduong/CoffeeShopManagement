package MODEL;

import java.math.BigDecimal;

public class ProductRecipe {

    private BigDecimal quantityUsed;
    
    // Đối tượng liên kết
    private Product product;
    private Inventory item;

    // Default constructor
    public ProductRecipe() {
    }

    public ProductRecipe(BigDecimal quantityUsed, Product product, Inventory item) {
        this.quantityUsed = quantityUsed;
        this.product = product;
        this.item = item;
    }

    public BigDecimal getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(BigDecimal quantityUsed) {
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

    @Override
    public String toString() {
        return "ProductRecipe{" +
                "quantityUsed=" + quantityUsed +
                ", product=" + product +
                ", item=" + item +
                '}';
    }
}
