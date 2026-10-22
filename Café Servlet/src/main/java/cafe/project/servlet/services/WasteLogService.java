package cafe.project.servlet.services;

import cafe.project.servlet.repositories.WasteLogRepository;
import cafe.project.servlet.repositories.entities.WasteLog;

import java.sql.SQLException;
import java.util.List;

public class WasteLogService {
    private WasteLogRepository wasteLogRepository;

    public WasteLogService() {
        this.wasteLogRepository = new WasteLogRepository();
    }

    // Branch
    public List<WasteLog> getWasteLogsByBranch(String branchId) throws SQLException {
        return wasteLogRepository.findAllByBranch(branchId);
    }

    // ID 
    public WasteLog getWasteLogById(String wasteId) throws SQLException {
        return wasteLogRepository.findById(wasteId);
    }

    // Add Waste Log 
    public boolean addWasteLog(WasteLog wasteLog) throws SQLException {
        return wasteLogRepository.insert(wasteLog);
    }

    // Update Waste Log 
    public boolean updateWasteLog(WasteLog wasteLog) throws SQLException {
        return wasteLogRepository.update(wasteLog);
    }

    // Delete Waste Log 
    public boolean deleteWasteLog(String wasteId, String branchId) throws SQLException {
        return wasteLogRepository.softDelete(wasteId, branchId);
    }
}