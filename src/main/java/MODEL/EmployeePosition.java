package MODEL;

public class EmployeePosition {
    private int PositionID;
    private String PositionName;

    public EmployeePosition(int positionID, String positionName) {
        PositionID = positionID;
        PositionName = positionName;
    }

    public int getPositionID() {
        return PositionID;
    }

    public void setPositionID(int positionID) {
        PositionID = positionID;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String positionName) {
        PositionName = positionName;
    }
}
