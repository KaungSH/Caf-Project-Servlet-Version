package cafe.project.servlet.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import cafe.project.servlet.repositories.entities.Branch;

public class BranchMapper2 {

	public Branch mapRow(ResultSet rs) throws SQLException {
		Branch branch = new BranchMapper().mapRow(rs);
		branch.setStatus_name(rs.getString("status_name"));
		return branch;
	}

}
