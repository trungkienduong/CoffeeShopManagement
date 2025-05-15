package MODEL;

public class UnitCategory {
    private int unitId;
    private String unitName;
    private String description;

    // Default constructor
    public UnitCategory() {
    }

    public UnitCategory(int unitId, String unitName, String description) {
        this.unitId = unitId;
        this.unitName = unitName;
        this.description = description;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UnitCategory{" +
                "unitId=" + unitId +
                ", unitName='" + unitName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
