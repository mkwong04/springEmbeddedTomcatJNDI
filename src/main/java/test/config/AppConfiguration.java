package test.config;


import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;


@Configuration
public class AppConfiguration {
	
	private static Logger LOG = LoggerFactory.getLogger(AppConfiguration.class); 
	
	@Bean
	@Profile("dev")
	public TomcatServletWebServerFactory tomcatFactory() {
		LOG.info("initializing tomcat factory... ");
		return new TomcatServletWebServerFactory() {
			
			@Override
			protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
				tomcat.enableNaming();
				return new TomcatWebServer(tomcat, getPort() >= 0);
			}

			@Override
			protected void postProcessContext(Context context) {
				LOG.info("initializing tomcat factory JDNI ... ");
				ContextResource resource = new ContextResource();
				resource.setName(System.getProperty("dev.jndi.name", "jdbc/myDataSource"));
				resource.setType(DataSource.class.getName());
				resource.setProperty("driverClassName", System.getProperty("dev.driver.class.name", "com.mysql.jdbc.Driver"));
				resource.setProperty("url", System.getProperty("dev.url","jdbc:mysql://localhost:3306/test"));
				resource.setProperty("username", System.getProperty("dev.username", "db_deployer"));
				resource.setProperty("password", System.getProperty("dev.password","password"));
				
				context.getNamingResources().addResource(resource);
			}
		};
	}
	
	@Bean(destroyMethod="")
	public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:comp/env/jdbc/myDataSource");
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(true);
		bean.afterPropertiesSet();
		return (DataSource)bean.getObject();
	}

	@Bean
	public JdbcTemplate jdbcTemplate() throws IllegalArgumentException, NamingException {
		return new JdbcTemplate(jndiDataSource());
	}
}
