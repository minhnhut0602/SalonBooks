package org.tigersndragons.salonbooks.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.tigersndragons.salonbooks.dao.AddressDAO;
import org.tigersndragons.salonbooks.dao.AppointmentDAO;
import org.tigersndragons.salonbooks.dao.ContactDAO;
import org.tigersndragons.salonbooks.dao.EmployeeDAO;
import org.tigersndragons.salonbooks.dao.ItemDAO;
import org.tigersndragons.salonbooks.dao.OrderDAO;
import org.tigersndragons.salonbooks.dao.PersonDAO;
import org.tigersndragons.salonbooks.dao.ShipperDAO;
import org.tigersndragons.salonbooks.dao.impl.AddressDAOImpl;
import org.tigersndragons.salonbooks.dao.impl.AppointmentDAOImpl;
import org.tigersndragons.salonbooks.dao.impl.ContactDAOImpl;
import org.tigersndragons.salonbooks.dao.impl.EmployeeDAOImpl;
import org.tigersndragons.salonbooks.dao.impl.ItemDAOImpl;
import org.tigersndragons.salonbooks.dao.impl.OrderDAOImpl;
import org.tigersndragons.salonbooks.dao.impl.PersonDAOImpl;
import org.tigersndragons.salonbooks.dao.impl.ShipperDAOImpl;

@Configuration
@Import({
	HibernateConfiguration.class,
})
public class DAOConfiguration {

	@Bean
	public PersonDAO personDAO(){
		return new PersonDAOImpl();
	}
	@Bean
	public ShipperDAO shipperDAO(){
		return new ShipperDAOImpl();
	}	
	@Bean
	public AppointmentDAO appointmentDAO(){
		return new AppointmentDAOImpl();
	}
	@Bean
	public OrderDAO orderDAO(){
		return new OrderDAOImpl();
	}
	@Bean
	public EmployeeDAO employeeDAO(){
		return new EmployeeDAOImpl();
	}
	@Bean
	public ItemDAO itemDAO(){
		return new ItemDAOImpl();
	}
	@Bean
	public ContactDAO contactDAO(){
		return new ContactDAOImpl();
	}
	@Bean
	public AddressDAO addressDAO(){
		return new AddressDAOImpl();
	}
}
