package org.tigersndragons.salonbooks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tigersndragons.salonbooks.exception.PersonNotFoundException;
import org.tigersndragons.salonbooks.exception.ValidationException;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.flows.PersonFormModel;
import org.tigersndragons.salonbooks.model.type.GenderType;
import org.tigersndragons.salonbooks.service.PersonService;

public class PersonTest extends BaseTestCase {

	@Autowired 
	PersonService personService;
	
	private Person e1, e2;
	@Before
	public void setUp() throws Exception {
		e1= new Person();
		e2= new Person();
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
	public void testMatchingFirstName(){
		e1.setId(0L);
		e1.setFirstName("auser");
		e2.setId(0L);
		e2.setFirstName("auser");
		assertTrue(e1.equals(e2));
		assertTrue(e1.getFirstName().equals(e2.getFirstName()));
		e2.setId(1L);
		assertFalse(e1.equals(e2));		
		assertTrue(e1.getFirstName().equals(e2.getFirstName()));
	}
	
	private Person theDefaultPerson(){
		Person emp = new Person();
		emp.setId(0L);
		emp.setFirstName("TEST");
		emp.setLastName("TESTER");
		emp.setBirthDate(new LocalDate("1980-01-01"));//("password1");
		emp.setGender(GenderType.U);
		emp.setPrimaryPhoneNumber("5152201111");
		return emp;
	}
	@Test 
	public void retrieveDefaultPerson(){
		Person emp = personService.getDefaultPerson();
		assertTrue(emp.equals( theDefaultPerson()));
		
	}
	
	@Test 
	public void retrieveListOfPerson(){
		List<Person > personList = personService.getListOfActivePersons();
		assertTrue(CollectionUtils.isNotEmpty(personList)
				&& personList.size()>0);
		assertTrue(personList.get(0).equals(this.theDefaultPerson()));
	}
	
	@Test 
	public void retrieveDefaultPersonById(){
		Person emp = personService.getPersonById(0L);
		Person e2 = theDefaultPerson();
		assertTrue(emp.equals(e2 ));
		assertTrue(StringUtils.equals(emp.getFirstName(), e2.getFirstName()));
		assertTrue(StringUtils.equals(emp.getPrimaryPhoneNumber(), e2.getPrimaryPhoneNumber()));
		//assertTrue(StringUtils.equals(emp.getPassword(), e2.getPassword()));
	}
	
	@Test
	public void testlookupByLastName() {
		Person tester =personService.lookupByLastName("TESTER");
		Person dTested = theDefaultPerson();
		assertTrue(tester.equals(dTested));
	}
	@Test
	public void testlookupByPhone() {
		Person tester =personService.lookupByPhoneNumber("5152201111");
		Person dTested = theDefaultPerson();
		assertTrue(tester.equals(dTested));
	}	
	@Test
	public void testSaveDefaultPerson() {
		Person p =theDefaultPerson();
		p.setId(null);
		p.setPrimaryPhoneNumber("5152202222");
		personService.save(p);
		assertTrue(p.getId()!=null );
	}
	@Autowired
	PersonFormModel personFlowActions;
	@Test
	public void testCreatePersonService(){
		Person p = personService.createPerson();
		assertTrue(p!=null);
		Person p1 = personService.createPerson("");
		assertTrue(p1!=null);
		Person p2 = personService.createPerson("478");
		assertTrue(p2!=null);

		Person p3 = personService.createPerson("1234567890");
		assertTrue(p3!=null);
		Person p32 = personService.createPerson("123-456-7890");
		assertTrue(p32!=null);
		Person p4 = personService.createPerson("!234567890");
		assertTrue(p4!=null);
		Person p42 = personService.createPerson("I234567890");
		assertTrue(p42!=null);
		
	}
	
	@Test(expected = ValidationException.class)
	public void testCreatePersonFlow() throws PersonNotFoundException, ValidationException{
		/*Person p;
		try {
			p = personFlowActions.lookupCustomer(null);
		} catch (PersonNotFoundException e) {
			assertTrue(p==null);
		}
		assertTrue(p!=null);
		Person p1 = personFlowActions.lookupCustomer("");
		assertTrue(p1!=null);
		Person p2 = personFlowActions.lookupCustomer("478");
		assertTrue(p2!=null);*/
		try {
		Person p3 = personFlowActions.lookupCustomer("1234567890");
		} catch (PersonNotFoundException e) {	}
		Person p32 = personFlowActions.lookupCustomer("515-220-1111");
		assertTrue(p32!=null);
		Person p4 = personFlowActions.lookupCustomer("!5152201111");
		assertTrue(p4!=null);
		Person defaultOne = personFlowActions.lookupCustomer("5152201111");
		assertTrue(defaultOne!=null);
		//throw exception
		personFlowActions.lookupCustomer("I515220110");
	}

}
