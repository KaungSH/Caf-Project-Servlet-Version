package cafe.project.servlet.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cafe.project.servlet.repositories.entities.BranchStatus;
import cafe.project.servlet.repositories.mappers.BranchStatusMapper;
import cafe.project.servlet.utils.MyConnection;

public class BranchStatusRepository {

	Connection con = MyConnection.getConnection();
	private BranchStatusMapper branchStatusMapper = new BranchStatusMapper();
	
	public List<BranchStatus> findAll() {
		String sql = "SELECT * FROM branch_statuses";
		List<BranchStatus> branchStatuses = new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				branchStatuses.add(branchStatusMapper.mapRow(rs));
			}
			
		}catch(SQLException e) {
			System.out.println("Get All Products Failed - " + e);
		}
		return branchStatuses;
	}
}