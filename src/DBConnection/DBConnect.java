package DBConnection;

import java.sql.*;

public class DBConnect {
	
	public static Connection _conn = null;
	
	public static Connection getConnection() throws Exception {

		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3305/chatdb";
			String username = "root";
			String password = "qwerty123";
			Class.forName(driver);

			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}
}