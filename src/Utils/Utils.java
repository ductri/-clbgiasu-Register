package Utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;


public class Utils {
//	static String url = "127.8.183.2:3306";
//	static String dbname = "clbgiasu";
//	static String username = "adminzwy82sW";
//	static String password = "ykRyBrsjsFr9";
	static String url = "localhost";
	static String dbname = "clbgiasu";
	static String username = "root";
	static String password = "ductri";
	
	private static int noAccess = 0;
	private static Connection conn=null;
	public static Connection connectDB() {
		if (conn==null) {
			System.out.println("-------- MySQL JDBC Connection Testing ------------");
	
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Where is your MySQL JDBC Driver?");
				e.printStackTrace();
				return null;
			}
	
			System.out.println("MySQL JDBC Driver Registered!");
			Connection connection = null;
	
			try {
				connection = (Connection) DriverManager
				.getConnection("jdbc:mysql://"+url+"/" +dbname, username, password);
	
			} catch (SQLException e) {
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return null;
			}
	
			if (connection != null) {
				System.out.println("You made it, take control your database now!");
			} else {
				System.out.println("Failed to make connection!");
			}
			conn = connection;
		}
		return conn;
	}
	
	public static void closeConn() {
		if (conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void getNoAccess() {
		String sql = "SELECT noaccess FROM traffic where id=1";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				noAccess = rs.getInt("noaccess");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateNoAccess() {
	
		String sql = "UPDATE traffic SET noaccess=" + noAccess +" where id=1";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int showNoAccess() {
		return noAccess;
	}
	public static void inCreaseNoAccess() {
		noAccess++;
	}
}
