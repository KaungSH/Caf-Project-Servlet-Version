package cafe.project.servlet.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cafe.project.servlet.models.EmployeeEntryDto;
import cafe.project.servlet.models.EmployeeListDto;
import cafe.project.servlet.models.Gender;
import cafe.project.servlet.utils.MyConnection;

public class EmployeeRepository {

	public List<EmployeeListDto> getAll() {
		List<EmployeeListDto> employees = new ArrayList<>();
		String sql = "SELECT e.*, r.name as role_name, b.name as branch_name, s.name as status_name "
				+ "FROM employees e " + "INNER JOIN employee_roles r ON e.employee_role_id = r.role_id "
				+ "INNER JOIN branches b ON e.branch_id = b.branch_id "
				+ "INNER JOIN employee_statuses s ON e.employee_status_id = s.employee_status_id "
				+ "ORDER BY e.created_at DESC";

		try (Connection con = MyConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				EmployeeListDto emp = new EmployeeListDto();
				emp.setEmployeeId(rs.getString("employee_id"));
				emp.setName(rs.getString("name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhone(rs.getString("phone"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setAddress(rs.getString("address"));
				if (rs.getDate("dob") != null) {
					emp.setDob(rs.getDate("dob").toLocalDate());
				}
				emp.setPhotopath(rs.getString("photopath"));

				String genderStr = rs.getString("gender");
				if (genderStr != null) {
					emp.setGender(Gender.valueOf(genderStr));
				}

				emp.setRoleName(rs.getString("role_name"));
				emp.setBranchName(rs.getString("branch_name"));
				emp.setStatusName(rs.getString("status_name"));

				employees.add(emp);
			}
		} catch (SQLException ex) {
			ex.printStackTrace(); // Console ထဲမှာ Database Error အတိအကျ ပေါ်လာပါလိမ့်မယ်
		}
		return employees;
	}

	public EmployeeEntryDto getById(String id) {
		String sql = "SELECT * FROM employees WHERE employee_id = ?";
		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					EmployeeEntryDto emp = new EmployeeEntryDto();
					emp.setEmployeeId(rs.getString("employee_id"));
					emp.setName(rs.getString("name"));
					emp.setEmail(rs.getString("email"));
					emp.setPassword(rs.getString("password"));
					emp.setPhotopath(rs.getString("photopath"));
					emp.setPhone(rs.getString("phone"));
					emp.setSalary(rs.getDouble("salary"));
					emp.setAddress(rs.getString("address"));
					if (rs.getDate("dob") != null) {
						emp.setDob(rs.getDate("dob").toLocalDate());
					}
					String genderStr = rs.getString("gender");
					if (genderStr != null) {
						emp.setGender(Gender.valueOf(genderStr));
					}
					emp.setEmployeeStatusId(rs.getString("employee_status_id"));
					emp.setEmployeeRoleId(rs.getString("employee_role_id"));
					emp.setBranchId(rs.getString("branch_id"));

					return emp;
				}
			}
		} catch (SQLException ex) {
			System.out.println("Error in Employee getById: " + ex.getMessage());
		}
		return null;
	}

	public void add(EmployeeEntryDto emp) {
		String sql = "INSERT INTO employees (employee_id, name, email, photopath, password, employee_status_id, phone, salary, address, dob, gender, employee_role_id, branch_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, emp.getEmployeeId());
			ps.setString(2, emp.getName());
			ps.setString(3, emp.getEmail());
			ps.setString(4, emp.getPhotopath());
			ps.setString(5, emp.getPassword());
			ps.setString(6, emp.getEmployeeStatusId());
			ps.setString(7, emp.getPhone());
			ps.setDouble(8, emp.getSalary());
			ps.setString(9, emp.getAddress());
			ps.setObject(10, emp.getDob());
			ps.setString(11, emp.getGender().toString());
			ps.setString(12, emp.getEmployeeRoleId());
			ps.setString(13, emp.getBranchId());

			ps.executeUpdate();
			System.out.println("Insert Employee Successful.");
		} catch (SQLException ex) {
			System.out.println("Error in Employee add: " + ex.getMessage());
		}
	}

	public void edit(String id, EmployeeEntryDto emp) {
		String sql = "UPDATE employees SET name = ?, email = ?, photopath = ?, phone = ?, salary = ?, address = ?, dob = ?, gender = ?, employee_status_id = ?, employee_role_id = ?, branch_id = ? WHERE employee_id = ?";

		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, emp.getName());
			ps.setString(2, emp.getEmail());
			ps.setString(3, emp.getPhotopath());
			ps.setString(4, emp.getPhone());
			ps.setDouble(5, emp.getSalary());
			ps.setString(6, emp.getAddress());
			ps.setObject(7, emp.getDob());
			ps.setString(8, emp.getGender().toString());
			ps.setString(9, emp.getEmployeeStatusId());
			ps.setString(10, emp.getEmployeeRoleId());
			ps.setString(11, emp.getBranchId());
			ps.setString(12, id);

			ps.executeUpdate();
			System.out.println("Update Employee Successful.");
		} catch (SQLException ex) {
			System.out.println("Error in Employee edit: " + ex.getMessage());
		}
	}

	public void delete(String id) {
		String sql = "DELETE FROM employees WHERE employee_id = ?";
		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, id);
			ps.executeUpdate();
			System.out.println("Delete Employee Successful.");
		} catch (SQLException ex) {
			System.out.println("Error in Employee delete: " + ex.getMessage());
		}
	}
}