package org.tigersndragons.salonbooks;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tigersndragons.salonbooks.model.Contact;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.type.ContactType;
import org.tigersndragons.salonbooks.service.ContactService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ContactTest extends BaseTestCase {

	@Autowired 
	ContactService contactService;

	private Contact e1, e2;
	@Before
	public void setUp() throws Exception {
		e1= new Contact();
		e2= new Contact();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMatchId() {
		e1.setId(0L);
		e2.setId(0L);
		assertTrue(e1.equals(e2));
		e2.setId(1L);
		assertFalse(e1.equals(e2));
	}
	
	@Test
	public void testMatchingNotes(){
		e1.setId(0L);
		e1.setLabel("auser");
		e2.setId(0L);
		e2.setLabel("auser");
		assertTrue(e1.equals(e2));
		assertTrue(e1.getLabel().equals(e2.getLabel()));
		e2.setId(1L);
		assertFalse(e1.equals(e2));		
		assertTrue(e1.getLabel().equals(e2.getLabel()));
	}
	
	private Contact theDefaultContact(){
		Contact emp = new Contact();
		emp.setId(0L);
		emp.setLabel("3196210000");
		ContactType ct = new ContactType();
		ct.setId(0L);
		ct.setName("MOBILE_PHONE");
		emp.setContactType(ct);
		emp.setPerson(getDefaultPerson());
		emp.setCreateDate(new DateTime());
		emp.setUpdateDate(new DateTime());
		return emp;
	}
	@Test 
	public void retrieveDefaultAppointment(){
		Contact emp = contactService.createContactTypeForPerson(getDefaultPerson(),null);
		assertTrue(emp.getLabel().equals( theDefaultContact().getLabel()));
		
	}
	
	@Test 
	public void testContactFactory(){
		ContactType ct = new ContactType();
		ct.setId(0L);
		Contact emp = contactService.createContactTypeForPerson(getDefaultPerson(),ct,"3196216807");
		assertTrue(emp.getLabel().equals( "3196216807"));
		ct.setId(1L);
		Contact emp1 = contactService.createContactTypeForPerson(getDefaultPerson(),ct,"5152810000");
		assertTrue(emp1.getContactType().getName().equals("HOME_PHONE"));
		ct.setId(4L);
		Contact emp4 = contactService.createContactTypeForPerson(getDefaultPerson(),ct,"@iowatiger08");
		assertTrue(emp4.getContactType().getName().equals("TWITTER"));
		try {
			URL aUrl = new URL(emp4.getFormat());
		} catch (MalformedURLException e) {
			assertTrue(e==null);
		}
		ct.setId(6L);
		Contact emp6 = contactService.createContactTypeForPerson(getDefaultPerson(),ct,"iowatiger08@gmail.ca");
		assertTrue(emp6.getContactType().getName().equals("EMAIL"));
		try {
			URL aUrl = new URL(emp6.getFormat());
		} catch (MalformedURLException e) {
			assertTrue(e==null);
		}
	}
	@Ignore
	@Test
	public  void createDefaultContacts() {
		Person person = new Person();
		person.setId(101L);
		person.setPrimaryPhoneNumber("8162223333");
		
		List<Contact> contacts = new ArrayList<Contact>();
		contacts = contactService.createDefaultContacts(person);
		assertTrue(contacts.size()==4);
		Contact emp =contacts.get(0);//
		assertTrue(emp.getLabel().equals( "8162223333"));
		Contact emp1 = contacts.get(1);//
		assertTrue(emp1.getContactType().getName().equals("HOME_PHONE"));
		Contact emp4 = contacts.get(2);//
		assertTrue(emp4.getContactType().getName().equals("TWITTER"));
		try {
			URL aUrl = new URL(emp4.getFormat());
		} catch (MalformedURLException e) {
			assertTrue(e==null);
		}
		Contact emp6 = contacts.get(3);//
		assertTrue(emp6.getContactType().getName().equals("EMAIL"));
		try {
			URL aUrl = new URL(emp6.getFormat());
		} catch (MalformedURLException e) {
			assertTrue(e==null);
		}
	}
	
	@Test 
	public void retrieveListOfContact(){
		List<Contact > apptList = contactService.getContactsByPerson(getDefaultPerson());
		assertTrue(CollectionUtils.isNotEmpty(apptList)
				&& apptList.size()>0);
		assertTrue(apptList.get(0).equals(this.theDefaultContact()));
	}
	
	@Test 
	public void retrieveContactById(){
		Contact emp = contactService.getContactbyId(0L);
		Contact e2 = theDefaultContact();
		assertTrue(emp.equals(e2 ));
		assertTrue(StringUtils.equals(emp.getLabel(), e2.getLabel()));
	}
	

		
	@Test
	//(expected=ConstraintViolationException.class)
	public void testSaveDefaultContact() {
		Contact emp = theDefaultContact();
		emp.setId(1L);
		contactService.saveContact(emp);
		
//		contactService.saveContact(theDefaultContact());
	}

}
