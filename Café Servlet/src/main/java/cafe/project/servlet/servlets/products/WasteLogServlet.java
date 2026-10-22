package cafe.project.servlet.servlets.products;

import cafe.project.servlet.repositories.WasteLogRepository;
import cafe.project.servlet.repositories.entities.WasteLog;
import cafe.project.servlet.repositories.entities.WasteReason;
import cafe.project.servlet.utils.MyConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet("/manager/wastelogs")
public class WasteLogServlet extends HttpServlet {
    private WasteLogRepository wasteLogRepository;

    @Override
    public void init() {
        wasteLogRepository = new WasteLogRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        String branchId = (String) req.getSession().getAttribute("branchId");
        if (branchId == null) {
            branchId = "1"; // Default fallback branch if session is not configured yet
        }

        try {
            switch (action) {
                case "new" -> showNewForm(req, resp, branchId);
                case "edit" -> showEditForm(req, resp, branchId);
                case "delete" -> deleteLog(req, resp, branchId);
                default -> listLogs(req, resp, branchId);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error in WasteLogServlet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String branchId = (String) req.getSession().getAttribute("branchId");
        if (branchId == null) branchId = "1";

        try {
            if ("update".equals(action)) {
                updateLog(req, resp, branchId);
            } else {
                createLog(req, resp, branchId);
            }
        } catch (SQLException e) {
            throw new ServletException("Failed to persist WasteLog", e);
        }
    }

    private void listLogs(HttpServletRequest req, HttpServletResponse resp, String branchId) 
            throws SQLException, ServletException, IOException {
        List<WasteLog> logs = wasteLogRepository.findAllByBranch(branchId);
        req.setAttribute("logs", logs);
        req.getRequestDispatcher("/WEB-INF/views/wastelogs/list.jsp").forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp, String branchId) 
            throws SQLException, ServletException, IOException {
        loadFormDependencies(req, branchId);
        req.getRequestDispatcher("/WEB-INF/views/wastelogs/form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp, String branchId) 
            throws SQLException, ServletException, IOException {
        String id = req.getParameter("id");
        WasteLog existing = wasteLogRepository.findById(id);
        req.setAttribute("log", existing);
        loadFormDependencies(req, branchId);
        req.getRequestDispatcher("/WEB-INF/views/wastelogs/form.jsp").forward(req, resp);
    }

    private void createLog(HttpServletRequest req, HttpServletResponse resp, String branchId) 
            throws SQLException, IOException {
        WasteLog log = new WasteLog();
        log.setWasteId(UUID.randomUUID().toString());
        log.setBatchId(req.getParameter("batchId"));
        log.setWasteReasonId(req.getParameter("wasteReasonId"));
        log.setQuantityLost(new BigDecimal(req.getParameter("quantityLost")));
        log.setFinancialLoss(new BigDecimal(req.getParameter("financialLoss")));
        
        String employeeId = (String) req.getSession().getAttribute("employeeId");
        log.setEmployeeId(employeeId != null ? employeeId : "manager-1");
        log.setNotes(req.getParameter("notes"));
        log.setBranchId(branchId);

        wasteLogRepository.insert(log);
        resp.sendRedirect(req.getContextPath() + "/manager/wastelogs?status=created");
    }

    private void updateLog(HttpServletRequest req, HttpServletResponse resp, String branchId) 
            throws SQLException, IOException {
        WasteLog log = new WasteLog();
        log.setWasteId(req.getParameter("wasteId"));
        log.setBatchId(req.getParameter("batchId"));
        log.setWasteReasonId(req.getParameter("wasteReasonId"));
        log.setQuantityLost(new BigDecimal(req.getParameter("quantityLost")));
        log.setFinancialLoss(new BigDecimal(req.getParameter("financialLoss")));
        log.setNotes(req.getParameter("notes"));
        log.setBranchId(branchId);

        wasteLogRepository.update(log);
        resp.sendRedirect(req.getContextPath() + "/manager/wastelogs?status=updated");
    }

    private void deleteLog(HttpServletRequest req, HttpServletResponse resp, String branchId) 
            throws SQLException, IOException {
        String id = req.getParameter("id");
        wasteLogRepository.softDelete(id, branchId);
        resp.sendRedirect(req.getContextPath() + "/manager/wastelogs?status=deleted");
    }

    private void loadFormDependencies(HttpServletRequest req, String branchId) throws SQLException {
        List<WasteReason> reasons = new ArrayList<>();
        List<String> batches = new ArrayList<>();

        try (Connection con = MyConnection.getConnection()) {
            // Load waste reasons
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM waste_reasons WHERE waste_reason_id != 'deleted'");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reasons.add(new WasteReason(rs.getString("waste_reason_id"), rs.getString("reason_name")));
                }
            }

            // Load active batch IDs for this branch
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT batch_id FROM ingredient_batches WHERE isdeleted = 0 AND branch_id = ?")) {
                ps.setString(1, branchId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        batches.add(rs.getString("batch_id"));
                    }
                }
            }
        }
        req.setAttribute("reasons", reasons);
        req.setAttribute("batches", batches);
    }
}