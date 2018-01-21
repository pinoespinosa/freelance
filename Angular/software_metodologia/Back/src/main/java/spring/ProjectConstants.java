package spring;

import datasource.MacUtils;

public class ProjectConstants {

	
	public static String PROJECT_NAME = "Office Manager";
	public static String PROJECT_VERSION = "1.0";

	public final static boolean HIDE_SWAGGER_OP = false;
	
	public static boolean isLocal(){
		return MacUtils.getMac().contains("pino-BES-G1529");
	}
		
}
