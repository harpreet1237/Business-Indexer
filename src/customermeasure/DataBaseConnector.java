package customermeasure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataBaseConnector {

	public static Connection getConnection()
	{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection con=null;
		try {
			con=DriverManager.getConnection("jdbc:mysql://localhost/tailor_project","root","");
			System.out.println("Connected to DataBase Server....");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}