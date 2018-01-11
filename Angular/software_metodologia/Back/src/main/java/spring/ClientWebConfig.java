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

			List<String> lista = SimpleFile.readFile(s, "config.prop");
			setFrontDirectory(lista.get(0));
		}
		return frontDirectory;
	}


	public static void setFrontDirectory(String frontDirectory) {
		ClientWebConfig.frontDirectory = frontDirectory;
	}

}