package MODEL;

import java.math.BigDecimal;
import java.util.Date;

public class Employee {
    private int Employee_ID;
    private String FullName;
    private String Gender;
    private String CCCD;
    private Date Date_Of_Birth;
    private String Phone;
    private String Email;
    private String Address;
    private int Position_ID;
    private BigDecimal Salary;
    private Date JoinDate;


    // Constructor với tất cả các tham số


    public Employee(int employee_ID, String fullName, String gender, String CCCD,
                    Date date_Of_Birth, String phone, String email, String address,
                    int position_ID, BigDecimal salary, Date joinDate) {
        Employee_ID = employee_ID;
        FullName = fullName;
        Gender = gender;
        this.CCCD = CCCD;
        Date_Of_Birth = date_Of_Birth;
        Phone = phone;
        Email = email;
        Address = address;
        Position_ID = position_ID;
        Salary = salary;
        JoinDate = joinDate;
    }

    public int getEmployee_ID() {
        return Employee_ID;
    }

    public void setEmployee_ID(int employee_ID) {
        Employee_ID = employee_ID;
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

    public Date getDate_Of_Birth() {
        return Date_Of_Birth;
    }

    public void setDate_Of_Birth(Date date_Of_Birth) {
        Date_Of_Birth = date_Of_Birth;
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

    public int getPosition_ID() {
        return Position_ID;
    }

    public void setPosition_ID(int position_ID) {
        Position_ID = position_ID;
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
                "Employee_ID=" + Employee_ID +
                ", FullName='" + FullName + '\'' +
                ", Gender='" + Gender + '\'' +
                ", CCCD='" + CCCD + '\'' +
                ", Date_Of_Birth=" + Date_Of_Birth +
                ", Phone='" + Phone + '\'' +
                ", Email='" + Email + '\'' +
                ", Address='" + Address + '\'' +
                ", Position_ID=" + Position_ID +
                ", Salary=" + Salary +
                ", JoinDate=" + JoinDate +
                '}';
    }
}
