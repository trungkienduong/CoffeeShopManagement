package MODEL;

public class Supplier {
    private int supplierId;
    private String supplierName;
    private String phone;
    private String address;
    private String email;

    // Constructors
    public Supplier() {
    }

    public Supplier(int supplierId, String supplierName, String phone, String address, String email) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    // Getters and setters
    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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
}
