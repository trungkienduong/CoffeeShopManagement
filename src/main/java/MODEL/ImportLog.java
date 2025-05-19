package MODEL;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ImportLog {
    private int importId;                   // IMPORT_ID
    private String supplierName;           // SUPPLIER_NAME
    private LocalDate importDate;          // IMPORT_DATE
    private BigDecimal quantity;           // QUANTITY
    private BigDecimal unitPrice;          // UNIT_PRICE
    private String note;                   // NOTE

    private Inventory item;                // ITEM_ID (khóa ngoại tới bảng INVENTORY)
    private Employee employee;             // EMPLOYEE_ID (khóa ngoại tới bảng EMPLOYEE)

    public ImportLog() {
    }

    public ImportLog(int importId, String supplierName, LocalDate importDate,
                     BigDecimal quantity, BigDecimal unitPrice, String note,
                     Inventory item, Employee employee) {
        this.importId = importId;
        this.supplierName = supplierName;
        this.importDate = importDate;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.note = note;
        this.item = item;
        this.employee = employee;
    }

    public int getImportId() {
        return importId;
    }

    public void setImportId(int importId) {
        this.importId = importId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Inventory getItem() {
        return item;
    }

    public void setItem(Inventory item) {
        this.item = item;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Tính tổng tiền (totalPrice) từ quantity * unitPrice.
     * Không cần trường riêng vì SQL đã tính toán cột này.
     */
    public BigDecimal getTotalPrice() {
        if (quantity != null && unitPrice != null) {
            return quantity.multiply(unitPrice);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return "ImportLog{" +
                "importId=" + importId +
                ", supplierName='" + supplierName + '\'' +
                ", importDate=" + importDate +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + getTotalPrice() +
                ", note='" + note + '\'' +
                ", item=" + item +
                ", employee=" + employee +
                '}';
    }
}
