package org.tigersndragons.salonbooks.service.impl;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.tigersndragons.salonbooks.ServiceUtils;
import org.tigersndragons.salonbooks.dao.ContactDAO;
import org.tigersndragons.salonbooks.model.Contact;
import org.tigersndragons.salonbooks.model.Employee;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.factory.ContactFactory;
import org.tigersndragons.salonbooks.model.type.ContactType;
import org.tigersndragons.salonbooks.service.ContactService;
import org.tigersndragons.salonbooks.service.EmployeeService;
import org.tigersndragons.salonbooks.service.PersonService;

public class ContactServiceImpl extends BaseServiceImpl implements ContactService {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ContactDAO contactDAO;
//	private PersonService personService;
	private EmployeeService employeeService;


	public List<Contact> getActiveContacts() {

		return contactDAO.getActiveContacts();
	}


	public Contact createContact() {
		Contact newOne = createContactForPerson(new Person());//personService.getDefaultPerson());
		//ContactDAO.saveObject(newOne);
		return newOne;
	}
	
	
	public Contact createContactForPerson(Person person) {
		Contact newOne = ContactFactory.createContact("", null);//.defaultContact();
		newOne = assignContacttoPerson(newOne, person);
		return newOne;
	}
	
	public Contact createContactTypeForPerson(Person person, ContactType type) {
		Contact newOne =null;
		if (type == null ){
			 newOne =ContactFactory.defaultContact();
		}else{
			newOne = ContactFactory.createContact("", type);
		}
		newOne = assignContacttoPerson(newOne, person);
		return newOne;
	}
	
	public Contact createContactTypeForPerson(Person person, ContactType type, String label) {
		Contact newOne = ContactFactory.createContact(label, type);
		newOne = assignContacttoPerson(newOne, person);
		return newOne;
	}
	
	public void closeContact(Contact appt){
		appt.setIsActive("N");
		appt.setUpdateDate(new DateTime());
		contactDAO.saveObject(appt);
	}
	

	
	public Contact assignContacttoPerson(Contact appt, Person person){
		appt.setPerson(person);
		return appt;
		
	}

	private Employee defaultEmployee() {
		return employeeService.getDefaultEmployee();
	}

	private Person defaultPerson() {
		return new Person();//personService.getDefaultPerson();
	}

//	public void startContact(Contact appt) {
//		appt.setContactType(new ContactType());
//		appt.setUpdateDate(new DateTime());
//	}

	public List<Contact> getContactsForPerson(Person p, Employee emp) {
		ServiceUtils.assertNotNull("Person cannot be null",p);
		ServiceUtils.assertNotNull("employee cannot be null",emp);
		return contactDAO.getContactsForPerson(p);
	}
	

	public List<ContactType> getContactTypeList(){
		return contactDAO.getContactTypeList();
	}


	public Contact getContactById(long id) {
		ServiceUtils.assertNotNull("ID cannot be null", id);
		return contactDAO.getObjectById(id);
	}
	
	public List<Contact> getContactsByPerson(Person p){
		return contactDAO.getContactsForPerson(p);
		
	}

	public void save(Contact theContact) {
		contactDAO.saveObject(theContact);
	}


	public Contact getContactbyId(Long id) {
		return getContactById(id);
	}


	public void saveContact(Contact c) {
		save(c);
	}
	@Required
	public void setContactDAO(ContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}

//	@Required
//	public void setPersonService(PersonService personService) {
//		this.personService = personService;
//	}

	@Required
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}


	public List<Contact> createDefaultContacts(Person person) {
		List<Contact> contacts = new ArrayList<Contact>();
		ContactType ct = new ContactType();
		ct.setId(0L);
		Contact emp = createContactTypeForPerson(person,ct,person.getPrimaryPhoneNumber());
		contacts.add(emp);
		ct.setId(1L);
		Contact emp1 = createContactTypeForPerson(person,ct,"5152810000");
		contacts.add(emp1);
		ct.setId(4L);
		Contact emp4 = createContactTypeForPerson(person,ct,"@default");
		contacts.add(emp4);
		ct.setId(6L);
		Contact emp6 = createContactTypeForPerson(person,ct,"email@default.ca");
		contacts.add(emp);
		try {
			URL aUrl = new URL(emp6.getFormat());
		} catch (MalformedURLException e) {
			assertTrue(e==null);
		}		
		return contacts;
	
	}
}
