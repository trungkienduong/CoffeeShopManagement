package MODEL;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductRecipe {
    private Category category;

    private Inventory item;

    private BigDecimal quantityUsed;

    public ProductRecipe() {
    }

    public ProductRecipe(Category category, Inventory item, BigDecimal quantityUsed) {
        this.category = category;
        this.item = item;
        this.quantityUsed = quantityUsed;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
                "categoryId=" + (category != null ? category.getCategoryId() : "null") +
                ", itemName=" + (item != null ? item.getItemName() : "null") +
                ", quantityUsed=" + quantityUsed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductRecipe)) return false;
        ProductRecipe that = (ProductRecipe) o;
        return Objects.equals(
                category != null ? category.getCategoryId() : null,
                that.category != null ? that.category.getCategoryId() : null)
                &&
                Objects.equals(
                        item != null ? item.getItemName() : null,
                        that.item != null ? that.item.getItemName() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                category != null ? category.getCategoryId() : null,
                item != null ? item.getItemName() : null);
    }
}
