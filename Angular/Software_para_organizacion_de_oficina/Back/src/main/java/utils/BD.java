package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.mysql.jdbc.PreparedStatement;

public class BD {

 	
	public static final boolean justRead = false;
	public static final boolean local = false;

	
	
	// BD tools
	private static Connection con = null;
	private static Session session = null;

	
	public static String get_BD_NAME(){
		
		if (local)
			return "dureka";
		else
			return "c0410390_dureka";
		
	}
	
	   private static void doSshTunnel(String strSshUser, String strSshPassword, String strSshHost, int nSshPort,
	            String strRemoteHost, int nLocalPort, int nRemotePort) throws JSchException {
	        final JSch jsch = new JSch();
	        session = jsch.getSession(strSshUser, strSshHost, nSshPort);
	        session.setPassword(strSshPassword);

	        final Properties config = new Properties();
	        config.put("StrictHostKeyChecking", "no");
	        session.setConfig(config);

	        session.connect();
	        
	        if (nLocalPort!=-1 && strRemoteHost!=null && nRemotePort!=-1)
	        	session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
	    }


	public static void executeBash(String text, String comando) throws SQLException, JSchException, IOException{
		
		closeConnection();
		
	     List<String> result = new ArrayList<String>();
		
		String strSshUser = ConfigUtils.getConfigDatos().get("strSshUser"); 					// SSH loging username
		String strSshPassword = ConfigUtils.getConfigDatos().get("strSshPassword"); 			// SSH login password
		String strSshHost = ConfigUtils.getConfigDatos().get("strSshHost"); 	
		int nSshPort = Integer.parseInt(ConfigUtils.getConfigDatos().get("nSshPort")); 			// remote SSH host port number

		doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, null, -1, -1);

        ChannelExec channelExec = (ChannelExec)session.openChannel("exec");
        InputStream in = channelExec.getInputStream();
		
        channelExec.setCommand(comando);
                        
        channelExec.connect();
        
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;

		while ((line = reader.readLine()) != null) {
			result.add(line);
			System.out.println(line);
		}

		int exitStatus = channelExec.getExitStatus();

		channelExec.disconnect();
		session.disconnect();
		if (exitStatus < 0) {
			// System.out.println("Done, but exit status not set!");
		} else if (exitStatus > 0) {
			// System.out.println("Done, but with error!");
		}

		else {

			System.out.println("Done!");

		}
        
		closeConnection();

		
	}
	
	
	
	
	
	private static void openConnection() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if (local)
		{
			try {
				int nLocalPort = 3306; // local port number use to bind SSH tunnel
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:" + nLocalPort, "elcomerciomkp", "elcomerciomkp");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else {
		try {
			String strSshUser = ConfigUtils.getConfigDatos().get("strSshUser"); 					// SSH loging username
			String strSshPassword = ConfigUtils.getConfigDatos().get("strSshPassword"); 			// SSH login password
			String strSshHost = ConfigUtils.getConfigDatos().get("strSshHost"); 					// hostname or ip or
																									// SSH server
			int nSshPort = Integer.parseInt(ConfigUtils.getConfigDatos().get("nSshPort")); 			// remote SSH host port number
			String strRemoteHost = ConfigUtils.getConfigDatos().get("strRemoteHost"); 													// hostname or ip of your database server

			int nLocalPort = Integer.parseInt(ConfigUtils.getConfigDatos().get("nLocalPort")); 		// local port number use to bind SSH tunnel
			int nRemotePort = Integer.parseInt(ConfigUtils.getConfigDatos().get("nRemotePort")); 	// remote port number of your database
			String strDbUser = ConfigUtils.getConfigDatos().get("strDbUser"); 						// database loging username
			String strDbPassword = ConfigUtils.getConfigDatos().get("strDbPassword"); 				// database login password

			doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:" + nLocalPort, strDbUser,
					strDbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		}
	
	}
	
	public static void closeConnection() throws SQLException{
		if (con != null) {
			con.close();
			con = null;
		}
		if (session!=null){
			session.disconnect();
			session=null;
		}
	}
	
	public static void executeSqlVoid(String script) throws SQLException {

		if (justRead)
			return;
		
		if (con==null)
			openConnection();
			
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
