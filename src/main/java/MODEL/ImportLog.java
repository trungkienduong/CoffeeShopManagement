package MODEL;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ImportLog {
    private int importId;
    private Supplier supplier;             // Thay cho supplierName, liên kết khóa ngoại SUPPLIER_ID
    private LocalDate importDate;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private String note;

    private Inventory item;
    private Employee employee;

    public ImportLog() {
    }

    public ImportLog(int importId, Supplier supplier, LocalDate importDate,
                     BigDecimal quantity, BigDecimal unitPrice, String note,
                     Inventory item, Employee employee) {
        this.importId = importId;
        this.supplier = supplier;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
                ", supplier=" + (supplier != null ? supplier.getSupplierName() : "null") +
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
