package MODEL;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Model đại diện cho bảng IMPORT_LOG trong cơ sở dữ liệu.
 */
public class ImportLog {
    private Integer importId;           // IMPORT_ID: Mã phiếu nhập (nullable, tự tăng từ DB)
    private String itemName;            // ITEM_NAME: Tên nguyên liệu
    private int ingredientCategoryId;  // INGREDIENT_CATEGORY_ID: Loại nguyên liệu
    private int unitId;                // UNIT_ID: Đơn vị tính
    private int supplierId;            // SUPPLIER_ID: Nhà cung cấp
    private LocalDate importDate;      // IMPORT_DATE: Ngày nhập
    private BigDecimal quantity;       // QUANTITY: Số lượng nhập
    private BigDecimal unitPrice;      // UNIT_PRICE: Giá nhập trên đơn vị
    private Integer employeeId;        // EMPLOYEE_ID: Mã nhân viên nhập (nullable)
    private String note;               // NOTE: Ghi chú nhập hàng (nullable)

    public ImportLog() {
        // Constructor mặc định để sử dụng khi khởi tạo rỗng
    }


    // Constructor dùng khi tạo mới ImportLog để insert (không có importId)
    public ImportLog(String itemName, int ingredientCategoryId, int unitId, int supplierId,
                     LocalDate importDate, BigDecimal quantity, BigDecimal unitPrice,
                     Integer employeeId, String note) {
        this.itemName = itemName;
        this.ingredientCategoryId = ingredientCategoryId;
        this.unitId = unitId;
        this.supplierId = supplierId;
        this.importDate = (importDate != null) ? importDate : LocalDate.now();
        setQuantity(quantity);
        setUnitPrice(unitPrice);
        this.employeeId = employeeId;
        this.note = note;
    }

    // Constructor đầy đủ dùng khi lấy dữ liệu từ DB (có importId)
    public ImportLog(Integer importId, String itemName, int ingredientCategoryId, int unitId, int supplierId,
                     LocalDate importDate, BigDecimal quantity, BigDecimal unitPrice,
                     Integer employeeId, String note) {
        this.importId = importId;
        this.itemName = itemName;
        this.ingredientCategoryId = ingredientCategoryId;
        this.unitId = unitId;
        this.supplierId = supplierId;
        this.importDate = (importDate != null) ? importDate : LocalDate.now();
        setQuantity(quantity);
        setUnitPrice(unitPrice);
        this.employeeId = employeeId;
        this.note = note;
    }

    // Getters và Setters

    public Integer getImportId() {
        return importId;
    }

    public void setImportId(Integer importId) {
        this.importId = importId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getIngredientCategoryId() {
        return ingredientCategoryId;
    }

    public void setIngredientCategoryId(int ingredientCategoryId) {
        this.ingredientCategoryId = ingredientCategoryId;
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
        this.importDate = (importDate != null) ? importDate : LocalDate.now();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Đơn giá phải >= 0");
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
     * Tổng tiền = quantity * unitPrice
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
                ", ingredientCategoryId=" + ingredientCategoryId +
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
