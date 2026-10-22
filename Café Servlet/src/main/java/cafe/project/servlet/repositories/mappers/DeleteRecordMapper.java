package cafe.project.servlet.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import cafe.project.servlet.repositories.entities.DeleteRecord;

public class DeleteRecordMapper {
	
	public DeleteRecord mapRow(ResultSet rs) throws SQLException {
		return new DeleteRecord(rs.getString("parent_id"), rs.getString("parent_table_name"), rs.getString("child_id"), rs.getString("child_table_name"));
	}

}
