package cafe.project.servlet.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import cafe.project.servlet.repositories.entities.BranchStatus;

	public class BranchStatusMapper {
	    public BranchStatus mapRow(ResultSet rs) throws SQLException {
	        BranchStatus status = new BranchStatus();
	        status.setBranch_status_id(rs.getString("branch_status_id"));
	        status.setName(rs.getString("name"));
	        return status;
	    }
	}

