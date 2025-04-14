package MODEL;

public class EmployeePosition {
    private int positionId;
    private String positionName;

    // Default constructor
    public EmployeePosition() {
    }

    // Parameterized constructor
    public EmployeePosition(int positionId, String positionName) {
        this.positionId = positionId;
        this.positionName = positionName;
    }

    // Constructor with only name (for creating new positions)
    public EmployeePosition(String positionName) {
        this.positionName = positionName;
    }

    // Getters and Setters
    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "EmployeePosition{" +
                "positionId=" + positionId +
                ", positionName='" + positionName + '\'' +
                '}';
    }
}
