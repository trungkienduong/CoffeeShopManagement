package MODEL;

public class UnitCategory {
    private int unitId;
    private String unitName;

    // Default constructor
    public UnitCategory() {
    }

    public UnitCategory(int unitId, String unitName) {
        this.unitId = unitId;
        this.unitName = unitName;
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

    @Override
    public String toString() {
        return unitName;
    }

}