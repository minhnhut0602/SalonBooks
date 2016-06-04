package org.tigersndragons.salonbooks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tigersndragons.salonbooks.exception.PersonNotFoundException;
import org.tigersndragons.salonbooks.exception.ValidationException;
import org.tigersndragons.salonbooks.model.Appointment;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.flows.PersonFormModel;
import org.tigersndragons.salonbooks.model.type.AppointmentStatusType;
import org.tigersndragons.salonbooks.model.type.GenderType;
import org.tigersndragons.salonbooks.service.AppointmentService;
import org.tigersndragons.salonbooks.service.EmployeeService;
import org.tigersndragons.salonbooks.service.PersonService;

public class AppointmentTest extends BaseTestCase {

	@Autowired 
	AppointmentService appointmentService;
	@Autowired 
	EmployeeService employeeService;
	@Autowired 
	PersonService personService;
	
	private Appointment e1, e2;
	@Before
	public void setUp() throws Exception {
		e1= new Appointment();
		e2= new Appointment();
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
		e1.setNotes("auser");
		e2.setId(0L);
		e2.setNotes("auser");
		assertTrue(e1.equals(e2));
		assertTrue(e1.getNotes().equals(e2.getNotes()));
		e2.setId(1L);
		assertFalse(e1.equals(e2));		
		assertTrue(e1.getNotes().equals(e2.getNotes()));
	}
	
	private Appointment theDefaultAppointment(){
		Appointment emp = new Appointment();
		emp.setId(0L);
		emp.setNotes("TEST DEFAULT");
		emp.setAppointmentStatusType(AppointmentStatusType.OPEN);
		emp.setAppointmentDate(new DateTime());
		emp.setEmployee(employeeService.getDefaultEmployee());
		emp.setPerson(personService.getDefaultPerson());
		emp.setCreateDate(new DateTime());//(personService.getDefaultPerson());
		emp.setUpdateDate(new DateTime());//(personService.getDefaultPerson());
		return emp;
	}
//	@Test 
//	public void retrieveDefaultAppointment(){
//		Appointment emp = appointmentService.createAppointment();
//		assertFalse(emp.equals( theDefaultAppointment()));
//		
//	}

	@Test 
	public void retrieveListOfAppointment2(){
		List<Appointment > apptList = appointmentService.getOpenAppointments();
//				getOpenAppointmentsForEmployee(employeeService.getDefaultEmployee());
		assertTrue(CollectionUtils.isNotEmpty(apptList)
				&& apptList.size()>0);
		assertTrue(apptList.get(apptList.size()-1).getNotes().equals(this.theDefaultAppointment().getNotes()));
	}
	@Test 
	public void retrieveListOfAppointment(){
		List<Appointment > apptList = appointmentService.
				getOpenAppointmentsForEmployee(employeeService.getDefaultEmployee());
		assertTrue(CollectionUtils.isNotEmpty(apptList)
				&& apptList.size()>0);
		assertTrue(apptList.get(apptList.size()-1).equals(this.theDefaultAppointment()));
	}
	
	@Test 
	public void retrieveAppointmentById(){
		Appointment emp = appointmentService.getAppointmentById(0L);
		Appointment e2 = theDefaultAppointment();
		assertTrue(emp.equals(e2 ));
		assertTrue(StringUtils.equals(emp.getNotes(), e2.getNotes()));
//		assertTrue(StringUtils.equals(emp.getAppointmentDate(), e2.getAppointmentDate()));
//		assertTrue(StringUtils.equals(emp.getPassword(), e2.getPassword()));
	}
	
	@Test
	public void testgetAppointmentsByPerson() {
		List<Appointment> alist =appointmentService.getAppointmentsByPerson(personService.getDefaultPerson(),employeeService.getDefaultEmployee());
		assertTrue(CollectionUtils.isNotEmpty(alist));	
		Appointment tester = alist.get(0);
		Appointment dTested = theDefaultAppointment();
//		assertTrue(tester.equals(dTested));
	}
		
	@Test
	public void testSaveDefaultAppointment() {
		appointmentService.save(theDefaultAppointment());
	}
/*	@Autowired
	PersonFlowActions personFlowActions;
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
		assertTrue(p2!=null);*
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
	}*/

}
