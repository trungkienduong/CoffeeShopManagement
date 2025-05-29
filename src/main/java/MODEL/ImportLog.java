package MODEL;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ImportLog {
    private Integer importId;
    private String itemName;
    private int ingredientCategoryId;
    private int unitId;
    private int supplierId;
    private LocalDate importDate;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private Integer employeeId;
    private String note;

    public ImportLog() {
    }

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
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Unit price must be >= 0");
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
