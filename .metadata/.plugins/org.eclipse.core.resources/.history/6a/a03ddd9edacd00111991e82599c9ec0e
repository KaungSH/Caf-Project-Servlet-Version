package cafe.project.servlet.repositories;

import cafe.project.servlet.repositories.entities.WasteLog;
import cafe.project.servlet.utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WasteLogRepository {

    public List<WasteLog> findAllByBranch(String branchId) throws SQLException {
        List<WasteLog> list = new ArrayList<>();
        String sql = "SELECT w.*, r.reason_name, e.name AS employee_name " +
                     "FROM waste_logs w " +
                     "LEFT JOIN waste_reasons r ON w.waste_reason_id = r.waste_reason_id " +
                     "LEFT JOIN employees e ON w.employee_id = e.employee_id " +
                     "WHERE w.isdeleted = 0 AND w.branch_id = ? " +
                     "ORDER BY w.logged_at DESC";

        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, branchId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    WasteLog log = mapRow(rs);
                    log.setReasonName(rs.getString("reason_name"));
                    log.setEmployeeName(rs.getString("employee_name"));
                    list.add(log);
                }
            }
        }
        return list;
    }

    public WasteLog findById(String wasteId) throws SQLException {
        String sql = "SELECT * FROM waste_logs WHERE waste_id = ? AND isdeleted = 0";
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, wasteId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public boolean insert(WasteLog log) throws SQLException {
        String sql = "INSERT INTO waste_logs (waste_id, batch_id, waste_reason_id, quantity_lost, " +
                     "financial_loss, employee_id, notes, isdeleted, branch_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, 0, ?)";
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, log.getWasteId());
            ps.setString(2, log.getBatchId());
            ps.setString(3, log.getWasteReasonId());
            ps.setBigDecimal(4, log.getQuantityLost());
            ps.setBigDecimal(5, log.getFinancialLoss());
            ps.setString(6, log.getEmployeeId());
            ps.setString(7, log.getNotes());
            ps.setString(8, log.getBranchId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(WasteLog log) throws SQLException {
        String sql = "UPDATE waste_logs SET batch_id = ?, waste_reason_id = ?, quantity_lost = ?, " +
                     "financial_loss = ?, notes = ? WHERE waste_id = ? AND branch_id = ?";
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, log.getBatchId());
            ps.setString(2, log.getWasteReasonId());
            ps.setBigDecimal(3, log.getQuantityLost());
            ps.setBigDecimal(4, log.getFinancialLoss());
            ps.setString(5, log.getNotes());
            ps.setString(6, log.getWasteId());
            ps.setString(7, log.getBranchId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean softDelete(String wasteId, String branchId) throws SQLException {
        String sql = "UPDATE waste_logs SET isdeleted = 1 WHERE waste_id = ? AND branch_id = ?";
        try (Connection con = MyConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, wasteId);
            ps.setString(2, branchId);
            return ps.executeUpdate() > 0;
        }
    }

    private WasteLog mapRow(ResultSet rs) throws SQLException {
        WasteLog log = new WasteLog();
        log.setWasteId(rs.getString("waste_id"));
        log.setBatchId(rs.getString("batch_id"));
        log.setWasteReasonId(rs.getString("waste_reason_id"));
        log.setQuantityLost(rs.getBigDecimal("quantity_lost"));
        log.setFinancialLoss(rs.getBigDecimal("financial_loss"));
        log.setEmployeeId(rs.getString("employee_id"));
        log.setLoggedAt(rs.getTimestamp("logged_at"));
        log.setNotes(rs.getString("notes"));
        log.setDeleted(rs.getBoolean("isdeleted"));
        log.setBranchId(rs.getString("branch_id"));
        return log;
    }
}