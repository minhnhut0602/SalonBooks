package org.tigersndragons.salonbooks.core;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.tigersndragons.salonbooks.dao.impl.AuditTrailInterceptor;
import org.tigersndragons.salonbooks.model.Address;
import org.tigersndragons.salonbooks.model.Appointment;
import org.tigersndragons.salonbooks.model.Contact;
import org.tigersndragons.salonbooks.model.Employee;
import org.tigersndragons.salonbooks.model.Item;
import org.tigersndragons.salonbooks.model.Order;
import org.tigersndragons.salonbooks.model.OrderItem;
import org.tigersndragons.salonbooks.model.OrderItemId;
import org.tigersndragons.salonbooks.model.PaymentMethod;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.SalonObject;
import org.tigersndragons.salonbooks.model.ShippingMethod;
import org.tigersndragons.salonbooks.model.type.ContactType;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration implements TransactionManagementConfigurer{
	
	@Bean
	public BasicDataSource getDefaultDataSource(){
		BasicDataSource defaultds = new org.apache.commons.dbcp.BasicDataSource();
		defaultds.setDriverClassName("com.mysql.jdbc.Driver");
		defaultds.setPassword("salonbooks123");
		defaultds.setUrl("jdbc:mysql://localhost:3306/salonbooks");
		defaultds.setUsername("salonbooks");
		defaultds.setInitialSize(30);
		defaultds.setPoolPreparedStatements(true);
		return defaultds;

	}
	@Bean
	public DataSource dataSource(){
		try {
			JndiObjectFactoryBean factoryBean = new JndiObjectFactoryBean();
			factoryBean.setDefaultObject(getDefaultDataSource());
			factoryBean.setExpectedType(DataSource.class);
			factoryBean.setJndiName("jdbc/SalonbooksDatasource");
			factoryBean.setResourceRef(true);
			factoryBean.afterPropertiesSet();
			return (DataSource) factoryBean.getObject();
		}catch(Exception e){
			throw new BeanCreationException("dataSource", e);
		}
	}
/*
 * not used at moment
 * 	@Bean
	public SpringLiquibase liquibase(){
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog("classpath:liquibase/changelog.xml");
		liquibase.setDataSource(dataSource());
		return liquibase;
	}*/
	@Bean
	public SessionFactory sessionFactory(){
		String[] packages = {
			"org.tigersndragons.salonbooks.model.type"
		};
		
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.cache.use_query_cache", true);
		hibernateProperties.put("hibernate.cache.use_structured_entries", true);
		hibernateProperties.put("hibernate.cache.use_second_level_cache", true);
		hibernateProperties.put("hibernate.cache.region.factory_class", SingletonEhCacheRegionFactory.class.getName());
		hibernateProperties.put("hibernate.order_updates", true);
		hibernateProperties.put("hibernate.order_inserts", true);
		hibernateProperties.put("jadira.usertype.autoRegisterUserTypes", "true");
		hibernateProperties.put("jadira.usertype.databaseZone", "UTC");
		hibernateProperties.put("jadira.usertype.javaZone", "UTC");
		hibernateProperties.put("hibernate.show_sql", true);
		hibernateProperties.put("hibernate.connection.autocommit", true);
		hibernateProperties.put("hibernate.flush_mode", "COMMIT");
		
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());

		builder.addAnnotatedClass(SalonObject.class);
		builder.addAnnotatedClass(ContactType.class);
		builder.addAnnotatedClass(Employee.class);
		builder.addAnnotatedClass(Person.class);
		builder.addAnnotatedClass(Order.class);
		builder.addAnnotatedClass(OrderItem.class);
		builder.addAnnotatedClass(OrderItemId.class);
		builder.addAnnotatedClass(Item.class);
		builder.addAnnotatedClass(Appointment.class);
		builder.addAnnotatedClass(ShippingMethod.class);
		builder.addAnnotatedClass(PaymentMethod.class);
		builder.addAnnotatedClass(Contact.class);
		builder.addAnnotatedClass(Address.class);

		builder.scanPackages(packages);
		builder.addPackages(packages);
		builder.addProperties(hibernateProperties);

		builder.setProperty("show.sql", Boolean.TRUE.toString());
//		builder.setProperty("", Boolean.TRUE.toString())
		builder.setInterceptor(new AuditTrailInterceptor());
		builder.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect" );
		return builder.buildSessionFactory();
	}
//	@Bean
//	public OpenSessionInViewFilter openSessionInViewFilter(){
//		OpenSessionInViewFilter filter = new OpenSessionInViewFilter();
//		//filter.
//		return filter;
//	}
	@Bean	
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return hibernateTransactionManager();
	}
	
	@Bean
	public PlatformTransactionManager hibernateTransactionManager(){
		return new HibernateTransactionManager(sessionFactory());
	}

}
