package spring;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import datasource.DataSourceReal;
import datasource.IDataSource;
import datasource.SimpleFile;

@EnableWebMvc
@Configuration
public class ClientWebConfig extends WebMvcConfigurerAdapter {

	private static String frontDirectory;
	private static String filesDirectory;

    public ClientWebConfig() {
        super();
        
        PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream("log.txt"));
	        System.setErr(out);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
    }

    
	@Bean
	public IDataSource createDataSource() {
		return new DataSourceReal();
	}
    
	@Bean
	public AccesManager accessManager() {
		return new AccesManager();
	}


	public static String getFrontDirectory() {

		if (frontDirectory == null) {
			String s = "";

			if (ProjectConstants.isLocal())
				s = "/home/pino/freelance/Angular/software_metodologia/Front/";
			else
				s = "/home/ubuntu/apache-tomcat-8.5.14/webapps/assets/imagenes/";
			setFrontDirectory(s);

		}
		return frontDirectory;
	}

	public static String getFilesDirectory() {

		if (filesDirectory == null) {
			String s = "";

			if (ProjectConstants.isLocal())
				s = "/home/pino/freelance/Angular/software_metodologia/Front/src/";
			else
				s = "/home/ubuntu/apache-tomcat-8.5.14/webapps/assets/imagenes/";
			
			filesDirectory=s;
		}
		return filesDirectory;
	}
	
	
	public static void setFrontDirectory(String frontDirectory) {
		ClientWebConfig.frontDirectory = frontDirectory;
	}

}