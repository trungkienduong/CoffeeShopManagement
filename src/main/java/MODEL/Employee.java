package MODEL;

import java.sql.Date;
import java.time.LocalDate;

public class Employee {

    public enum Gender {
        MALE('M'), FEMALE('F');

        private final char code;

        Gender(char code) {
            this.code = code;
        }

        public char getCode() {
            return code;
        }

        public String toLowerString() {
            return name().toLowerCase();
        }

        public static Gender fromCode(char code) {
            return switch (Character.toUpperCase(code)) {
                case 'M' -> MALE;
                case 'F' -> FEMALE;
                default -> null;
            };
        }
    }


    private int employeeId;
    private String username;
    private String fullName;
    private Gender gender;
    private String cccd;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private double salary;
    private LocalDate joinDate;
    private String imagePath;

    private int positionId;
    private EmployeePosition position;
    private User user;

    public Employee() {}

    public Employee(int employeeId, String username, String fullName, Gender gender, String cccd,
                    LocalDate dateOfBirth, String phone, String address, double salary,
                    LocalDate joinDate, String imagePath, int positionId) {
        this.employeeId = employeeId;
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.cccd = cccd;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.salary = salary;
        this.joinDate = joinDate;
        this.imagePath = imagePath;
        this.positionId = positionId;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGenderFromCode(char code) {
        this.gender = Gender.fromCode(code);
    }

    public char getGenderCode() {
        return gender != null ? gender.getCode() : ' ';
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateOfBirthSql() {
        return dateOfBirth != null ? Date.valueOf(dateOfBirth) : null;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfBirth(Date date) {
        this.dateOfBirth = date != null ? date.toLocalDate() : null;
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

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public Date getJoinDateSql() {
        return joinDate != null ? Date.valueOf(joinDate) : null;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public void setJoinDate(Date date) {
        this.joinDate = date != null ? date.toLocalDate() : null;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
        if (position != null) {
            this.positionId = position.getPositionId();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            this.username = user.getUsername();
        }
    }

    @Override
    public String toString() {
        return fullName + " - " + (position != null ? position.getPositionName() : "");
    }
}
