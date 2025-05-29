package MODEL;

public class IngredientCategory {
    private int ingredientCategoryId;
    private String ingredientCategoryName;
    public IngredientCategory() {}

    public IngredientCategory(int ingredientCategoryId, String ingredientCategoryName) {
        this.ingredientCategoryId = ingredientCategoryId;
        this.ingredientCategoryName = ingredientCategoryName;
    }

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
        return ingredientCategoryName;
    }
}
