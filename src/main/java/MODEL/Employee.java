package MODEL;

import java.util.Date;

public class Employee {
    private int employeeId;
    private String username; // tên đăng nhập
    private String fullname; // tên nhân viên
    private char gender;
    private String cccd;
    private Date dateOfBirth;
    private String phone;
    private String address;
    private double salary;
    private Date joinDate;
    private String image; // đường dẫn ảnh
    
    // Đối tượng liên kết
    private User user;
    private EmployeePosition position;

    // Default constructor
    public Employee() {
    }

    public Employee(int employeeId, String username, String fullname, char gender, String cccd, Date dateOfBirth, String phone, String address, double salary, Date joinDate, String image, User user, EmployeePosition position) {
        this.employeeId = employeeId;
        this.username = username;
        this.fullname = fullname;
        this.gender = gender;
        this.cccd = cccd;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.salary = salary;
        this.joinDate = joinDate;
        this.image = image;
        this.user = user;
        this.position = position;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
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
                ", salary=" + salary +
                ", joinDate=" + joinDate +
                ", image='" + image + '\'' +
                ", user=" + user +
                ", position=" + position +
                '}';
    }
}
