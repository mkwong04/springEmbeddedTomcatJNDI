package test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import test.config.AppConfiguration;

@SpringBootApplication
public class Application extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class, AppConfiguration.class);
	}
	
	public static final void main(String[] args) {
		new SpringApplicationBuilder(Application.class).sources(AppConfiguration.class)
													   .run(args);
	}

}
