package cafe.project.servlet.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
	
	public static Connection getConnection() {
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String db = "jdbc:mysql://localhost:3306/final_cafe_db";
			String username = "root";
			String password = "000681";
			
			con = DriverManager.getConnection(db, username, password);
			System.out.println("Connection Successful.");
			
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found.");
		} catch (SQLException e) {
			System.out.println("Connection Failed.");
		}
		
		return con;
	}

}
