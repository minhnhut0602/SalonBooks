package org.tigersndragons.salonbooks.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.tigersndragons.salonbooks.ServiceUtils;
import org.tigersndragons.salonbooks.dao.PersonDAO;
import org.tigersndragons.salonbooks.model.Address;
import org.tigersndragons.salonbooks.model.Appointment;
import org.tigersndragons.salonbooks.model.Order;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.PersonProfile;
import org.tigersndragons.salonbooks.model.type.GenderType;
import org.tigersndragons.salonbooks.service.AddressService;
import org.tigersndragons.salonbooks.service.AppointmentService;
import org.tigersndragons.salonbooks.service.ContactService;
import org.tigersndragons.salonbooks.service.EmployeeService;
import org.tigersndragons.salonbooks.service.OrderService;
import org.tigersndragons.salonbooks.service.PersonService;

@Transactional
public class PersonServiceImpl extends BaseServiceImpl  implements PersonService {

	private static final long serialVersionUID = 1L;
	@Autowired
	private PersonDAO personDAO;
	@Autowired
	private AddressService addressService;
	@Autowired
	private ContactService contactService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private EmployeeService employeeService;
	
	public Person lookupByPhoneNumber(String phoneNumber) {
		ServiceUtils.assertNotNull("PHONE CANNOT BE NULL", phoneNumber);
		return personDAO.lookupByPhoneNumber(phoneNumber);
	}

	public Person lookupByLastName(String lastName) {
		ServiceUtils.assertNotNull("Name cannot be null", lastName);
		return personDAO.lookupByLastName(lastName);
	}

	public List<Person> getListOfActivePersons() {
		return personDAO.getListOfActivePersons();
	}

	public Person getPersonById(Long id) {
		return personDAO.getObjectById(id);
	}

	public Person createPerson(String phoneNumber) {
		Person p = new Person();
		p.setBirthDate(new LocalDate());
		p.setLastName("CHANGE");
		p.setFirstName("CHANGE");
		p.setPrimaryPhoneNumber(phoneNumber);
		p.setCreateDate(new DateTime());
		p.setUpdateDate(new DateTime());
		p.setGender(GenderType.U);
		return p;
	}

	public Person getDefaultPerson() {
		Person person = new Person();
		person.setId(0L);
		return  person;
	}

	public Person createPerson() {
		return createPerson(null);
	}

	public void save(Person person) {
		ServiceUtils.assertNotNull("Person cannot be null",person);
		ServiceUtils.assertNotNull("PrimaryPhoneNumber cannot be null",person.getPrimaryPhoneNumber());
		if (person.getId()==null){
			personDAO.saveObject(person);
		}else{
			personDAO.updateObject(person);
		}
	}
	
	public PersonProfile getPersonProfile(Person person) {
		PersonProfile profile = new PersonProfile();
		profile.setPerson(person);
		profile.setAddresses(addressService.getAddressByPerson(person));
//		profile.setContacts(contactService.getContactsByPerson(person));
		profile.setAppointments(appointmentService.getAppointmentsForPerson(person,employeeService.getDefaultEmployee()));
		profile.setOrders(orderService.getOrdersForPerson(person));
		return profile;
	}
	public PersonProfile createPersonProfile(){
		return 	 createPersonProfile(new Person());
	}

	public PersonProfile createPersonProfile(Person person) {
		PersonProfile profile = new PersonProfile();
		profile.setPerson(person);
		List<Address> addresses = new ArrayList<Address> ();
		addresses.add(addressService.createDefaultAddress());
		profile.setAddresses(addresses);
//		profile.setContacts(contactService.createDefaultContacts(person));
		return profile;
	}

	public PersonProfile updatePersonProfile(PersonProfile profile) {
		save(profile.getPerson());
		for (Address addy :profile.getAddresses()){
			addressService.saveAddress(addy);
		}
//		for (Contact cnt: profile.getContacts()){
//			contactService.saveContact(cnt);
//		}
		for (Appointment appt: profile.getAppointments()){
			appointmentService.save(appt);
		}
		for (Order order : profile.getOrders()){
			orderService.saveOrder(order);
		}
		return profile;
	}
	
	@Required
	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}
	@Required
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}
	@Required
	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}
	@Required
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	@Required
	public void setAppointmentService(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
}
