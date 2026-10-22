package cafe.project.servlet.repositories;
import cafe.project.servlet.models.DailyRegisterEntryDto;
import cafe.project.servlet.models.DailyRegisterListDto;
import cafe.project.servlet.utils.MyConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyRegisterRepository {

	public List<DailyRegisterListDto> getAll() {
		List<DailyRegisterListDto> registers = new ArrayList<>();
		String sql = "SELECT dr.register_id, b.name AS branch_name, e.name AS employee_name, \"\r\n"
				+ "				+ \"rs.name AS status_name, dr.date, dr.opened_at, dr.closed_at \" + \"FROM daily_registers dr \"\r\n"
				+ "				+ \"INNER JOIN branches b ON dr.branch_id = b.branch_id \"\r\n"
				+ "				+ \"INNER JOIN employees e ON dr.employee_id = e.employee_id \"\r\n"
				+ "				+ \"INNER JOIN register_statuses rs ON dr.register_status_id = rs.register_status_id \"\r\n"
				+ "				+ \"WHERE dr.isdeleted = 0 \" + \"ORDER BY dr.date DESC, dr.opened_at DESC";

		try (Connection con = MyConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				String registerId = rs.getString("register_id");
				String branchName = rs.getString("branch_name");
				String employeeName = rs.getString("employee_name");
				String statusName = rs.getString("status_name");
				LocalDate date = null;
				if (rs.getDate("date") != null) {
					date = rs.getDate("date").toLocalDate();
				}
				Time openedAt = rs.getTime("opened_at");
				Time closedAt = rs.getTime("closed_at");

				DailyRegisterListDto dto = new DailyRegisterListDto(registerId, branchName, employeeName, statusName,
						date, openedAt, closedAt);

				registers.add(dto);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return registers;
	}

	public DailyRegisterEntryDto getById(String id) {
		String sql = "SELECT * FROM daily_registers WHERE register_id = ? AND isdeleted = 0";
		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					DailyRegisterEntryDto dto = new DailyRegisterEntryDto();
					dto.setRegister_id(rs.getString("register_id"));
					dto.setBranch_id(rs.getString("branch_id"));
					dto.setEmployee_id(rs.getString("employee_id"));
					dto.setRegister_status_id(rs.getString("register_status_id"));
					if (rs.getDate("date") != null) {
						dto.setDate(rs.getDate("date").toLocalDate());
					}
					dto.setOpened_at(rs.getTime("opened_at"));
					dto.setClosed_at(rs.getTime("closed_at"));

					return dto;
				}
			}
		} catch (SQLException ex) {
			System.out.println("Error in DailyRegister getById: " + ex.getMessage());
		}
		return null;
	}

	public void add(DailyRegisterEntryDto dto) {
		String sql = "INSERT INTO daily_registers (register_id, branch_id, employee_id, register_status_id, date, opened_at, closed_at, isedited, isdeleted) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, 0, 0)";
		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, dto.getRegister_id());
			ps.setString(2, dto.getBranch_id());
			ps.setString(3, dto.getEmployee_id());
			ps.setString(4, dto.getRegister_status_id());
			ps.setDate(5, dto.getDate() != null ? Date.valueOf(dto.getDate()) : null);
			ps.setTime(6, dto.getOpened_at());
			ps.setTime(7, dto.getClosed_at());

			ps.executeUpdate();
			System.out.println("Insert DailyRegister Successful.");
		} catch (SQLException ex) {
			System.out.println("Error in DailyRegister add: " + ex.getMessage());
		}
	}

	public void edit(String id, DailyRegisterEntryDto dto) {
		String sql = "UPDATE daily_registers SET branch_id = ?, employee_id = ?, register_status_id = ?, date = ?, opened_at = ?, closed_at = ?, isedited = 1 WHERE register_id = ?";

		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, dto.getBranch_id());
			ps.setString(2, dto.getEmployee_id());
			ps.setString(3, dto.getRegister_status_id());
			ps.setDate(4, dto.getDate() != null ? Date.valueOf(dto.getDate()) : null);
			ps.setTime(5, dto.getOpened_at());
			ps.setTime(6, dto.getClosed_at());
			ps.setString(7, id);

			ps.executeUpdate();
			System.out.println("Update DailyRegister Successful.");
		} catch (SQLException ex) {
			System.out.println("Error in DailyRegister edit: " + ex.getMessage());
		}
	}

	public void delete(String id) {
		String sql = "UPDATE daily_registers SET isdeleted = 1 WHERE register_id = ?";
		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, id);
			ps.executeUpdate();
			System.out.println("Delete DailyRegister Successful.");
		} catch (SQLException ex) {
			System.out.println("Error in DailyRegister delete: " + ex.getMessage());
		}
	}
}