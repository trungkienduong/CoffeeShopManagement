package MODEL;

public class UnitCategory {
    private int Unit_ID;
    private String Unit_Name;
    private String Description;

    public UnitCategory(int unit_ID, String unit_Name, String description) {
        Unit_ID = unit_ID;
        Unit_Name = unit_Name;
        Description = description;
    }

    public int getUnit_ID() {
        return Unit_ID;
    }

    public void setUnit_ID(int unit_ID) {
        Unit_ID = unit_ID;
    }

    public String getUnit_Name() {
        return Unit_Name;
    }

    public void setUnit_Name(String unit_Name) {
        Unit_Name = unit_Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "UnitCategory{" +
                "Unit_ID=" + Unit_ID +
                ", Unit_Name='" + Unit_Name + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
