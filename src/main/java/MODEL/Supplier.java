package MODEL;

public class Supplier {
    private int supperlierId;
    private String supperlierName;
    private String phone;
    private String address;
    private String email;


    public Supplier() {
    }

    public Supplier(int supperlierId, String supperlierName, String phone, String address, String email) {
        this.supperlierId = supperlierId;
        this.supperlierName = supperlierName;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public int getSupperlierId() {
        return supperlierId;
    }

    public void setSupperlierId(int supperlierId) {
        this.supperlierId = supperlierId;
    }

    public String getSupperlierName() {
        return supperlierName;
    }

    public void setSupperlierName(String supperlierName) {
        this.supperlierName = supperlierName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supperlierId=" + supperlierId +
                ", supperlierName='" + supperlierName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
