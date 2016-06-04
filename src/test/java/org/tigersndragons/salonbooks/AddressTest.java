package org.tigersndragons.salonbooks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tigersndragons.salonbooks.exception.PersonNotFoundException;
import org.tigersndragons.salonbooks.exception.ValidationException;
import org.tigersndragons.salonbooks.model.Address;
import org.tigersndragons.salonbooks.model.Appointment;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.flows.PersonFormModel;
import org.tigersndragons.salonbooks.model.type.AppointmentStatusType;
import org.tigersndragons.salonbooks.model.type.GenderType;
import org.tigersndragons.salonbooks.service.AddressService;
import org.tigersndragons.salonbooks.service.AppointmentService;
import org.tigersndragons.salonbooks.service.EmployeeService;
import org.tigersndragons.salonbooks.service.PersonService;

public class AddressTest extends BaseTestCase {

	@Autowired 
	AddressService addressService;
	@Autowired 
	EmployeeService employeeService;
	@Autowired 
	PersonService personService;
	
	private Address e1, e2;
	@Before
	public void setUp() throws Exception {
		e1= new Address();
		e2= new Address();
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
		e1.setCity("auser");
		e2.setId(0L);
		e2.setCity("auser");
		assertTrue(e1.equals(e2));
		assertTrue(e1.getCity().equals(e2.getCity()));
		e2.setId(1L);
		assertFalse(e1.equals(e2));		
		assertTrue(e1.getCity().equals(e2.getCity()));
	}
	
	private Address theDefaultAddress(){
		Address emp = new Address();
		emp.setId(0L);
		emp.setLine1("default");
		emp.setCity("Des Moines");
		emp.setState("IA");
		emp.setZip("50315");
		emp.setPerson(personService.getDefaultPerson());
		emp.setCreateDate(new DateTime());
		emp.setUpdateDate(new DateTime());
		return emp;
	}
	@Test 
	public void retrieveDefaultAddress(){
		Address emp = addressService.createDefaultAddress();
		emp.setId(0L);
		assertTrue(emp.equals( theDefaultAddress()));
		
	}
	
	@Test 
	public void retrieveListOfAddresses(){
		List<Address > apptList = addressService.getAddressByPerson(personService.getDefaultPerson());
		assertTrue(CollectionUtils.isNotEmpty(apptList)
				&& apptList.size()>0);
		assertTrue(apptList.get(0).getLine1().equals(this.theDefaultAddress().getLine1()));
	}
	
	@Test 
	public void retrieveAddressById(){
		Address emp = addressService.getAddressbyId(0L);
		Address e2 = theDefaultAddress();
		assertTrue(emp.equals(e2 ));
		assertTrue(StringUtils.equals(emp.getCity(), e2.getCity()));
	}
	

		
	@Test
	//(expected=ConstraintViolationException.class)
	public void testSaveDefaultAddress() {
		Address emp = theDefaultAddress();
		emp.setId(0L);
		addressService.saveAddress(emp);
		
//		addressService.saveAddress(emp);
	}

}
