package cafe.project.servlet.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import cafe.project.servlet.models.LoginDto;

public class LoginMapper2 {
	
	public LoginDto mapRow(ResultSet rs) throws SQLException {
		
		return new LoginDto(rs.getString("e.email"), rs.getString("e.password"), rs.getString("employee_id"), 
							rs.getString("role_name"), rs.getString("branch_id"), rs.getString("branch_name"), 
							rs.getString("status_name"), rs.getString("employee_role_id"), rs.getString("employee_status_id"), rs.getString("e.name"), rs.getString("photopath"));
	}

}
