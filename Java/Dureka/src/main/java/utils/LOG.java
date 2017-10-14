package utils;

public class LOG {

	private static boolean log=false;
	private static boolean reporte=true;
	
	public static void log(String text){
		
		if (log)
			System.out.println(text);
		
	}

	public static void reporte(String text){
		
		if (reporte)
			System.out.println(text);
		
	}

	
}
