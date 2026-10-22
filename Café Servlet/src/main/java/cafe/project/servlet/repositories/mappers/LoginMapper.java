package cafe.project.servlet.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import cafe.project.servlet.models.LoginDto;

public class LoginMapper {
	
	public LoginDto mapRow(ResultSet rs) throws SQLException {
		
		return new LoginDto(rs.getString("email"), rs.getString("password"));
	}

}
