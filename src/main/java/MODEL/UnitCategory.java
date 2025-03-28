package MODEL;

public class UnitCategory {
    private int UnitID;
    private String UnitName;
    private String Description;

    public UnitCategory(int unitID, String unitName, String description) {
        UnitID = unitID;
        UnitName = unitName;
        Description = description;
    }

    // Getters and Setters

    public int getUnitID() {
        return UnitID;
    }

    public void setUnitID(int unitID) {
        UnitID = unitID;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
