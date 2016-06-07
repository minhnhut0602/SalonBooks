package org.tigersndragons.salonbooks.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tigersndragons.salonbooks.service.AddressService;
import org.tigersndragons.salonbooks.service.AppointmentService;
import org.tigersndragons.salonbooks.service.ContactService;
import org.tigersndragons.salonbooks.service.EmployeeService;
import org.tigersndragons.salonbooks.service.EncryptionService;
import org.tigersndragons.salonbooks.service.HomeService;
import org.tigersndragons.salonbooks.service.ItemService;
import org.tigersndragons.salonbooks.service.OrderService;
import org.tigersndragons.salonbooks.service.PaymentService;
import org.tigersndragons.salonbooks.service.PersonService;
import org.tigersndragons.salonbooks.service.ShippingMethodService;
import org.tigersndragons.salonbooks.service.impl.AddressServiceImpl;
import org.tigersndragons.salonbooks.service.impl.AppointmentServiceImpl;
import org.tigersndragons.salonbooks.service.impl.ContactServiceImpl;
import org.tigersndragons.salonbooks.service.impl.EmployeeServiceImpl;
import org.tigersndragons.salonbooks.service.impl.EncryptionServiceImpl;
import org.tigersndragons.salonbooks.service.impl.HomeServiceImpl;
import org.tigersndragons.salonbooks.service.impl.ItemServiceImpl;
import org.tigersndragons.salonbooks.service.impl.OrderServiceImpl;
import org.tigersndragons.salonbooks.service.impl.PaymentServiceImpl;
import org.tigersndragons.salonbooks.service.impl.PersonServiceImpl;
import org.tigersndragons.salonbooks.service.impl.ShippingMethodServiceImpl;

@Configuration
public class ServiceConfiguration {

	@Bean
	public EmployeeService employeeService(){
		EmployeeServiceImpl service = new EmployeeServiceImpl();
		return service;
	}
	
	@Bean
	public EncryptionService encryptionService(){
		return new EncryptionServiceImpl();
	}
	@Bean
	public AppointmentService appointmentService(){
		AppointmentServiceImpl service = new AppointmentServiceImpl();
		service.setEmployeeService(employeeService());
		return service;
	}
	@Bean 
	public PaymentService paymentService(){
		PaymentServiceImpl service= new PaymentServiceImpl();
		return service;
	}
	@Bean
	public ShippingMethodService shippingMethodService(){
		ShippingMethodServiceImpl service= new ShippingMethodServiceImpl();
		return service;
	}
	@Bean
	public ItemService itemService(){
		ItemServiceImpl service= new ItemServiceImpl();
		return service;
	}
	@Bean
	public OrderService orderService(){
		OrderServiceImpl service = new OrderServiceImpl();
		service.setAppointmentService(appointmentService());
		service.setEmployeeService(employeeService());
		service.setItemService(itemService());
		service.setPaymentService(paymentService());
		service.setShipperService(shippingMethodService());
		return service;
	}
	
	@Bean 
	public AddressService addressService(){
		AddressServiceImpl service = new AddressServiceImpl();

		return service;
	}	
	@Bean 
	public ContactService contactService(){
		ContactServiceImpl service = new ContactServiceImpl();
		service.setEmployeeService(employeeService());
		return service;
	}
	@Bean
	public PersonService personService(){
		PersonServiceImpl service= new PersonServiceImpl();
		service.setAppointmentService(appointmentService());
		service.setOrderService(orderService());
		service.setAddressService(addressService());
		service.setContactService(contactService());
		return service;
	}


	@Bean
	public HomeService homeService(){
		HomeServiceImpl homeService = new HomeServiceImpl();
		homeService.setAppointmentService(appointmentService());
		homeService.setOrderService(orderService());
		homeService.setPersonService(personService());
		homeService.setShipperService(shippingMethodService());
		return homeService;
	}

}
