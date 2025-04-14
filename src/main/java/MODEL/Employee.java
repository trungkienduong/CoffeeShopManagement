package MODEL;

import java.util.Date;

public class Employee {
    private int employeeId;
    private String username;
    private String fullname;
    private char gender;
    private String cccd;
    private Date dateOfBirth;
    private String phone;
    private String address;
    private int positionId;
    private double salary;
    private Date joinDate;
    
    // Đối tượng liên kết
    private User user;
    private EmployeePosition position;

    // Default constructor
    public Employee() {
    }

    // Parameterized constructor without linked objects
    public Employee(int employeeId, String username, String fullname, char gender, 
                   String cccd, Date dateOfBirth, String phone, String address, 
                   int positionId, double salary, Date joinDate) {
        this.employeeId = employeeId;
        this.username = username;
        this.fullname = fullname;
        this.gender = gender;
        this.cccd = cccd;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.positionId = positionId;
        this.salary = salary;
        this.joinDate = joinDate;
    }

    // Constructor with linked objects
    public Employee(int employeeId, User user, String fullname, char gender, 
                   String cccd, Date dateOfBirth, String phone, String address, 
                   EmployeePosition position, double salary, Date joinDate) {
        this.employeeId = employeeId;
        this.username = user.getUsername();
        this.user = user;
        this.fullname = fullname;
        this.gender = gender;
        this.cccd = cccd;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.positionId = position.getPositionId();
        this.position = position;
        this.salary = salary;
        this.joinDate = joinDate;
    }

    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.username = user.getUsername();
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
        this.positionId = position.getPositionId();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", gender=" + gender +
                ", cccd='" + cccd + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", positionId=" + positionId +
                ", salary=" + salary +
                ", joinDate=" + joinDate +
                ", position=" + (position != null ? position.getPositionName() : "null") +
                '}';
    }
}
