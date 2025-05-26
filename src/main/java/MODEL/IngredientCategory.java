package MODEL;

/**
 * Model đại diện cho bảng INGREDIENT_CATEGORY trong cơ sở dữ liệu.
 */
public class IngredientCategory {
    private int ingredientCategoryId;      // INGREDIENT_CATEGORY_ID
    private String ingredientCategoryName; // INGREDIENT_CATEGORY_NAME

    // Constructor không tham số
    public IngredientCategory() {}

    // Constructor đầy đủ
    public IngredientCategory(int ingredientCategoryId, String ingredientCategoryName) {
        this.ingredientCategoryId = ingredientCategoryId;
        this.ingredientCategoryName = ingredientCategoryName;
    }

    // Getter & Setter
    public int getIngredientCategoryId() {
        return ingredientCategoryId;
    }

    public void setIngredientCategoryId(int ingredientCategoryId) {
        this.ingredientCategoryId = ingredientCategoryId;
    }

    public String getIngredientCategoryName() {
        return ingredientCategoryName;
    }

    public void setIngredientCategoryName(String ingredientCategoryName) {
        this.ingredientCategoryName = ingredientCategoryName;
    }

    @Override
    public String toString() {
        return ingredientCategoryName; // tiện cho hiển thị trong ComboBox
    }
}
