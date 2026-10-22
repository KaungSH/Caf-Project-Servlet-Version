package cafe.project.servlet.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cafe.project.servlet.models.Suppliers;
import cafe.project.servlet.utils.MyConnection;

public class SupplierDAO {
	public List<Suppliers> getAllSuppliers() throws SQLException {

		List<Suppliers> suppliers = new ArrayList<>();

		String sql = "SELECT supplier_id, name, contact_info,isdeleted, created_at \r\n"
				+ "FROM suppliers WHERE isdeleted = 0 ORDER BY created_at ASC";

		try (Connection con = MyConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Suppliers s = new Suppliers();

				s.setSupplier_id(rs.getString("supplier_id"));
				s.setName(rs.getString("name"));
				s.setContact_info(rs.getString("contact_info"));
				s.setIsdeleted(rs.getBoolean("isdeleted"));
				// s.setCreated_at(rs.getObject("created_at"));

				if (rs.getTimestamp("created_at") != null) {
					s.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
				}

				suppliers.add(s);
			}
		}
		return suppliers;
	}

	public Suppliers getSupplierById(String id) throws SQLException {

		String sql = "SELECT supplier_id, name, contact_info, isdeleted, created_at FROM suppliers \r\n"
				+ "WHERE supplier_id = ? AND isdeleted = 0";
		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Suppliers s = new Suppliers();

					s.setSupplier_id(rs.getString("supplier_id"));
					s.setName(rs.getString("name"));
					s.setContact_info(rs.getString("contact_info"));
					s.setIsdeleted(rs.getBoolean("isdeleted"));

					if (rs.getTimestamp("created_at") != null) {
						s.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
					}

					return s;
				}
			}
		}
		return null;
	}

	public boolean isSupplierNameExists(String name) throws SQLException {

		String sql = "SELECT COUNT(*) FROM suppliers " + "WHERE name = ?";

		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, name.trim());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}

		return false;
	}

	public boolean addSupplier(Suppliers s) throws SQLException {
		String sql = "INSERT INTO suppliers\r\n" + "(supplier_id, name, contact_info, isdeleted) \r\n"
				+ "VALUES (?,?,?,0)";

		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			String supplierId = UUID.randomUUID().toString();

			ps.setString(1, supplierId);
			ps.setString(2, s.getName());
			ps.setString(3, s.getContact_info());

			return ps.executeUpdate() > 0;
		}
	}

	public boolean isSupplierNameExistsForUpdate(String name, String supplierId) throws SQLException {

		String sql = "SELECT COUNT(*) FROM suppliers " + "WHERE name = ? AND supplier_id <> ?";

		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, name);
			ps.setString(2, supplierId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}

		return false;
	}

	public boolean updateSupplier(Suppliers s) throws SQLException {

		String sql = "UPDATE suppliers " + "SET name = ?, contact_info = ? "
				+ "WHERE supplier_id = ? AND isdeleted = 0";

		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, s.getName());
			ps.setString(2, s.getContact_info());
			ps.setString(3, s.getSupplier_id());

			int rows = ps.executeUpdate();

			return rows > 0;
		}
	}

	public boolean deleteSupplier(String id) throws SQLException {
		String sql = "UPDATE suppliers SET isdeleted = 1 WHERE supplier_id = ? AND isdeleted = 0";

		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, id);

			return ps.executeUpdate() > 0;

		}
	}

	public List<Suppliers> getDeletedSuppliers() throws SQLException {

		List<Suppliers> suppliers = new ArrayList<>();

		String sql = "SELECT supplier_id, name, contact_info, isdeleted, created_at FROM suppliers \r\n"
				+ "WHERE isdeleted = 1 ORDER BY created_at DESC";

		try (Connection con = MyConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Suppliers s = new Suppliers();

				s.setSupplier_id(rs.getString("supplier_id"));
				s.setName(rs.getString("name"));
				s.setContact_info(rs.getString("contact_info"));
				s.setIsdeleted(rs.getBoolean("isdeleted"));

				java.sql.Timestamp timestamp = rs.getTimestamp("created_at");

				if (timestamp != null) {
					s.setCreated_at(timestamp.toLocalDateTime());
				}

				suppliers.add(s);
			}
		}

		return suppliers;
	}

	public boolean restoreSupplier(String id) throws SQLException {

		String sql = "UPDATE suppliers SET isdeleted = 0 \r\n" + "WHERE supplier_id = ? AND isdeleted = 1";

		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, id);

			return ps.executeUpdate() > 0;
		}
	}

	public boolean permanentDeleteSupplier(String id) throws SQLException {

		String sql = "DELETE FROM suppliers WHERE supplier_id = ? AND isdeleted = 1";

		try (Connection con = MyConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, id);

			return ps.executeUpdate() > 0;
		}
	}
}
