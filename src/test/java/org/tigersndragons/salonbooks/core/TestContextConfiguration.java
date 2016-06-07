package org.tigersndragons.salonbooks.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@Import({
	ServiceConfiguration.class,
	FlowConfiguration.class,
	DAOConfiguration.class,
	WebMVCConfiguration.class
//	MockRepositoryConfiguration.class
})
public class TestContextConfiguration {

	@Bean
	public static PropertySourcesPlaceholderConfigurer  propertyPlaceholderConfigurator(){
		PropertySourcesPlaceholderConfigurer  configurator = new PropertySourcesPlaceholderConfigurer();
		configurator.setLocations(new Resource[] {
			new ClassPathResource("props/default.properties")
		});
		return configurator;
	}

//	@Bean
//	public PlatformTransactionManager hibernateTransactionManager(){
//		return new MockPlatformTransactionManager();
//	}
}
