package MODEL;

public class Category {
    private int categoryId;
    private String categoryName;
    private String description;
    private char categoryType; // 'I' cho Inventory (nguyên liệu), 'P' cho Product (sản phẩm)
    private String imagePath;

    // Default constructor
    public Category() {
    }

    public Category(int categoryId, String categoryName, String description, char categoryType, String imagePath) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.categoryType = categoryType;
        this.imagePath = imagePath;
    }

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
        this.categoryType = categoryType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", categoryType=" + categoryType +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
