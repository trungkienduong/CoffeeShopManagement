package MODEL;

public class Category {
    private int categoryId;
    private String categoryName;
    private String description;
    private char categoryType; // 'I' cho Inventory (nguyên liệu), 'P' cho Product (sản phẩm)

    // Default constructor
    public Category() {
    }

    // Parameterized constructor
    public Category(int categoryId, String categoryName, String description, char categoryType) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.categoryType = categoryType;
    }

    // Constructor without ID (for creating new categories)
    public Category(String categoryName, String description, char categoryType) {
        this.categoryName = categoryName;
        this.description = description;
        this.categoryType = categoryType;
    }

    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public char getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(char categoryType) {
        // Chỉ chấp nhận 'I' hoặc 'P'
        if (categoryType == 'I' || categoryType == 'P') {
            this.categoryType = categoryType;
        } else {
            throw new IllegalArgumentException("Category type must be 'I' for Inventory or 'P' for Product");
        }
    }

    // Helper methods
    public boolean isInventoryCategory() {
        return categoryType == 'I';
    }

    public boolean isProductCategory() {
        return categoryType == 'P';
    }

    public String getCategoryTypeName() {
        return categoryType == 'I' ? "Nguyên liệu" : "Sản phẩm";
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", categoryType=" + getCategoryTypeName() +
                '}';
    }
}
