package MODEL;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ImportLog {
    private int importId;                     // IMPORT_ID (PK)
    private Inventory item;                   // ITEM_ID (FK -> INVENTORY)
    private String supplierName;              // SUPPLIER_NAME
    private LocalDate importDate;             // IMPORT_DATE
    private BigDecimal quantity;              // QUANTITY
    private BigDecimal unitPrice;             // UNIT_PRICE
    private BigDecimal totalPrice;            // TOTAL_PRICE (tính từ quantity * unitPrice)
    private Employee employee;                // EMPLOYEE_ID (FK -> EMPLOYEE)
    private String note;

}
