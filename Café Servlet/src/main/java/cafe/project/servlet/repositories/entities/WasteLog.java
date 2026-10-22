package cafe.project.servlet.repositories.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class WasteLog {
    private String wasteId;
    private String batchId;
    private String wasteReasonId;
    private BigDecimal quantityLost;
    private BigDecimal financialLoss;
    private String employeeId;
    private Timestamp loggedAt;
    private String notes;
    private boolean isDeleted;
    private String branchId;
    
    // Additional fields for display/joins
    private String reasonName;
    private String employeeName;

    public WasteLog() {}

    public String getWasteId() { return wasteId; }
    public void setWasteId(String wasteId) { this.wasteId = wasteId; }

    public String getBatchId() { return batchId; }
    public void setBatchId(String batchId) { this.batchId = batchId; }

    public String getWasteReasonId() { return wasteReasonId; }
    public void setWasteReasonId(String wasteReasonId) { this.wasteReasonId = wasteReasonId; }

    public BigDecimal getQuantityLost() { return quantityLost; }
    public void setQuantityLost(BigDecimal quantityLost) { this.quantityLost = quantityLost; }

    public BigDecimal getFinancialLoss() { return financialLoss; }
    public void setFinancialLoss(BigDecimal financialLoss) { this.financialLoss = financialLoss; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public Timestamp getLoggedAt() { return loggedAt; }
    public void setLoggedAt(Timestamp loggedAt) { this.loggedAt = loggedAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }

    public String getReasonName() { return reasonName; }
    public void setReasonName(String reasonName) { this.reasonName = reasonName; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
}
