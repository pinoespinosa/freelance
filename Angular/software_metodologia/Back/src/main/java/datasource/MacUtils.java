package datasource;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MacUtils{

   
   public static String getMac(){
	   
		try {

			InetAddress ip = InetAddress.getLocalHost();
			return ip.toString();

		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
		return null;
	   
   }

}