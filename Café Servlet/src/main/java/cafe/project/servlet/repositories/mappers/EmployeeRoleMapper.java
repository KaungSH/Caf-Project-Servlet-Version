package cafe.project.servlet.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRoleMapper {
	
	public String mapRow(ResultSet rs) throws SQLException {
		return rs.getString("name");
	}
}