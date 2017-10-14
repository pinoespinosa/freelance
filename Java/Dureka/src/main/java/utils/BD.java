package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class BD {

	private static Connection con = null;

	
	private static void openConnection() throws SQLException{
		String sURL = "jdbc:mysql://localhost:3306/filesCSV";
		con = DriverManager.getConnection(sURL, "elcomerciomkp", "elcomerciomkp");
	}
	
	public static void closeConnection() throws SQLException{
		con.close();
	}
	
	public static void executeSqlVoid(String script) throws SQLException {

		if (con==null)
			openConnection();
	
//		System.out.println( script);

		
		try (PreparedStatement stmt = (PreparedStatement) con.prepareStatement(script)) {
			stmt.execute();

		} catch (SQLException sqle) {
			System.out.println("Error en la ejecución:" + sqle.getErrorCode() + " " + sqle.getMessage());
		}
		
	}

	
	public static String executeSqlQueryString(String script, String columna) throws SQLException {

		if (con==null)
			openConnection();
		
		String result="";
		
//		System.out.println( script);
		
		try (PreparedStatement stmt = (PreparedStatement)
				  con.prepareStatement(script)) { ResultSet rs =
				  stmt.executeQuery();
				  
				  while (rs.next()) 
					  result = rs.getString(columna);
				  
				  } catch (SQLException sqle) {
				  System.out.println("Error en la ejecución:" + sqle.getErrorCode() +
				  " " + sqle.getMessage()); }


		return result;
		
	}

}
