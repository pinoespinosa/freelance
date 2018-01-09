package spring;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import datasource.DataSourceReal;
import datasource.IDataSource;

@EnableWebMvc
@Configuration
public class ClientWebConfig extends WebMvcConfigurerAdapter {

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
	
    // API

}