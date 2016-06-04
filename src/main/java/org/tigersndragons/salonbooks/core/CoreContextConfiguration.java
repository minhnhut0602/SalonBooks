package org.tigersndragons.salonbooks.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@EnableWebMvc
@Configuration
@Import({
	WebMVCConfiguration.class,
	ServiceConfiguration.class, 
	DAOConfiguration.class
	,ServiceFlowConfiguration.class
})
@ComponentScan("org.tigersndragons.salonbooks.model org.tigersndragons.salonbooks.exception")
public class CoreContextConfiguration {
	private static Logger logger = LoggerFactory.getLogger(CoreContextConfiguration.class);

	@Bean
	static public PropertySourcesPlaceholderConfigurer  propertyPlaceholderConfigurator() throws UnknownHostException{
		String hostName = InetAddress.getLocalHost().getHostName();
		Resource propertiesResource = new ClassPathResource("props/" + hostName + ".properties");
		logger.info("using properties file: {}", propertiesResource);
		
		PropertySourcesPlaceholderConfigurer  configurator = new PropertySourcesPlaceholderConfigurer();
		configurator.setLocations(new Resource[] {
				new ClassPathResource("props/default.properties"),
				propertiesResource
		});
		return configurator;
	}	

}
