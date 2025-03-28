package MODEL;

import java.math.BigDecimal;

public class Employee {
    private int EmployeeID;
    private String FullName;
    private String Position;
    private BigDecimal Salary;
    private String Phone;
    private String Address;

    public Employee(int EmployeeID, String FullName, String Position, BigDecimal Salary, String Phone, String Address) {
        this.EmployeeID = EmployeeID;
        this.FullName = FullName;
        this.Position = Position;
        this.Salary = Salary;
        this.Phone = Phone;
        this.Address = Address;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public BigDecimal getSalary() {
        return Salary;
    }

    public void setSalary(BigDecimal salary) {
        Salary = salary;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
