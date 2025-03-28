package MODEL;

import java.math.BigDecimal;
import java.util.Date;

public class Employee {
    private int EmployeeID;
    private String FullName;
    private String Gender;
    private String CCCD;
    private Date DateOfBirth;
    private String Phone;
    private String Email;
    private String Address;
    private int PositionID;
    private BigDecimal Salary;
    private Date JoinDate;


    // Constructor với tất cả các tham số


    public Employee(int employeeID, String fullName, String gender, String CCCD, Date dateOfBirth, String phone, String email, String address, int positionID, BigDecimal salary, Date joinDate) {
        EmployeeID = employeeID;
        FullName = fullName;
        Gender = gender;
        this.CCCD = CCCD;
        DateOfBirth = dateOfBirth;
        Phone = phone;
        Email = email;
        Address = address;
        PositionID = positionID;
        Salary = salary;
        JoinDate = joinDate;
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

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getPositionID() {
        return PositionID;
    }

    public void setPositionID(int positionID) {
        PositionID = positionID;
    }

    public BigDecimal getSalary() {
        return Salary;
    }

    public void setSalary(BigDecimal salary) {
        Salary = salary;
    }

    public Date getJoinDate() {
        return JoinDate;
    }

    public void setJoinDate(Date joinDate) {
        JoinDate = joinDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "EmployeeID=" + EmployeeID +
                ", FullName='" + FullName + '\'' +
                ", Gender='" + Gender + '\'' +
                ", CCCD='" + CCCD + '\'' +
                ", DateOfBirth=" + DateOfBirth +
                ", Phone='" + Phone + '\'' +
                ", Email='" + Email + '\'' +
                ", Address='" + Address + '\'' +
                ", PositionID=" + PositionID +
                ", Salary=" + Salary +
                ", JoinDate=" + JoinDate +
                '}';
    }
}
