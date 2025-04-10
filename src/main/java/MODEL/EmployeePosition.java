package MODEL;

public class EmployeePosition {
    private int Position_ID;
    private String Position_Name;

    public EmployeePosition(int position_ID, String position_Name) {
        Position_ID = position_ID;
        Position_Name = position_Name;
    }

    public int getPosition_ID() {
        return Position_ID;
    }

    public void setPosition_ID(int position_ID) {
        Position_ID = position_ID;
    }

    public String getPosition_Name() {
        return Position_Name;
    }

    public void setPosition_Name(String position_Name) {
        Position_Name = position_Name;
    }

    @Override
    public String toString() {
        return Position_Name; // Chỉ hiển thị tên chức vụ, tiện cho ComboBox
    }
}
