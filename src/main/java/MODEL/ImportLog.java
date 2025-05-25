package MODEL;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Model đại diện cho bảng IMPORT_LOG trong database.
 */
public class ImportLog {
    private int importId;          // IMPORT_ID
    private String itemName;       // ITEM_NAME
    private int categoryId;        // CATEGORY_ID
    private int unitId;            // UNIT_ID
    private int supplierId;        // SUPPLIER_ID
    private LocalDate importDate;  // IMPORT_DATE
    private BigDecimal quantity;   // QUANTITY
    private BigDecimal unitPrice;  // UNIT_PRICE
    // totalPrice là trường tính toán trên SQL, không cần lưu trong model
    private Integer employeeId;    // EMPLOYEE_ID có thể null
    private String note;           // NOTE

    // Constructor không tham số
    public ImportLog() {
        this.importDate = LocalDate.now(); // mặc định ngày hiện tại
    }

    // Constructor đầy đủ tham số (trừ totalPrice vì là trường tính toán)
    public ImportLog(int importId, String itemName, int categoryId, int unitId, int supplierId,
                     LocalDate importDate, BigDecimal quantity, BigDecimal unitPrice,
                     Integer employeeId, String note) {
        this.importId = importId;
        this.itemName = itemName;
        this.categoryId = categoryId;
        this.unitId = unitId;
        this.supplierId = supplierId;
        this.importDate = importDate;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.employeeId = employeeId;
        this.note = note;
    }

    // Getters và Setters
    public int getImportId() {
        return importId;
    }

    public void setImportId(int importId) {
        this.importId = importId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
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
        if (quantity != null && quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        if (unitPrice != null && unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Unit price must be non-negative");
        }
        this.unitPrice = unitPrice;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Tính tổng tiền (tương tự TOTAL_PRICE trong DB).
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
                ", itemName='" + itemName + '\'' +
                ", categoryId=" + categoryId +
                ", unitId=" + unitId +
                ", supplierId=" + supplierId +
                ", importDate=" + importDate +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + getTotalPrice() +
                ", employeeId=" + employeeId +
                ", note='" + note + '\'' +
                '}';
    }
}
