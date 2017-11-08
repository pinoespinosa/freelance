package spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import web.service.SemaphoreEngine;

@EnableWebMvc
@Configuration
public class ClientWebConfig extends WebMvcConfigurerAdapter {

	public ClientWebConfig() {
		super();
	}

	@Bean
	public SemaphoreEngine createDataSource() {
		return new SemaphoreEngine();
	}

}