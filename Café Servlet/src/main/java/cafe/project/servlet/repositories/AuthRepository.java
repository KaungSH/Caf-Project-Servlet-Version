package cafe.project.servlet.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cafe.project.servlet.models.UserSessionDto;
import cafe.project.servlet.utils.MyConnection;


public class AuthRepository {
	public UserSessionDto authenticate(String email, String password) {
		UserSessionDto user = null;
		String sql = "SELECT e.employee_id, e.name, e.email, e.password, e.employee_role_id, \r\n"
				+ "                     r.name AS role_name, e.branch_id, b.name AS branch_name, e.employee_status_id\r\n"
				+ "                     FROM employees e \r\n"
				+ "                     JOIN employee_roles r ON e.employee_role_id = r.role_id \r\n"
				+ "                     JOIN branches b ON e.branch_id = b.branch_id \r\n"
				+ "                     WHERE e.email = ? AND e.employee_status_id = '2';";
		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String dbPassword = rs.getString("password");
					boolean isPasswordMatch = false;
					if (dbPassword != null) {
						if (dbPassword.equals(dbPassword)) {
							isPasswordMatch = true;
						}
					}
					if (isPasswordMatch) {
						user = new UserSessionDto(rs.getString("employee_id"), rs.getString("name"),
								rs.getString("email"), rs.getString("employee_role_id"), rs.getString("role_name"),
								rs.getString("branch_id"), rs.getString("branch_name"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}