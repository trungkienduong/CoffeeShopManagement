package MODEL;

public class ProductCategory {
    private int Category_ID;
    private String Category_Name;
    private String Description;

    public ProductCategory(int category_ID, String category_Name, String description) {
        Category_ID = category_ID;
        Category_Name = category_Name;
        Description = description;
    }

    public int getCategory_ID() {
        return Category_ID;
    }

    public void setCategory_ID(int category_ID) {
        Category_ID = category_ID;
    }

    public String getCategory_Name() {
        return Category_Name;
    }

    public void setCategory_Name(String category_Name) {
        Category_Name = category_Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "Category_ID=" + Category_ID +
                ", Category_Name='" + Category_Name + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
