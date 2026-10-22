package cafe.project.servlet.repositories.entities;

public class WasteReason {
    private String wasteReasonId;
    private String reasonName;

    public WasteReason() {}

    public WasteReason(String wasteReasonId, String reasonName) {
        this.wasteReasonId = wasteReasonId;
        this.reasonName = reasonName;
    }

    public String getWasteReasonId() { return wasteReasonId; }
    public void setWasteReasonId(String wasteReasonId) { this.wasteReasonId = wasteReasonId; }

    public String getReasonName() { return reasonName; }
    public void setReasonName(String reasonName) { this.reasonName = reasonName; }
}