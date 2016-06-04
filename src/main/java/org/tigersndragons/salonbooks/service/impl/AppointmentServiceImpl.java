package org.tigersndragons.salonbooks.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.tigersndragons.salonbooks.ServiceUtils;
import org.tigersndragons.salonbooks.dao.AppointmentDAO;
import org.tigersndragons.salonbooks.model.Appointment;
import org.tigersndragons.salonbooks.model.Employee;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.type.AppointmentStatusType;
import org.tigersndragons.salonbooks.service.AppointmentService;
import org.tigersndragons.salonbooks.service.EmployeeService;
import org.tigersndragons.salonbooks.service.PersonService;

@Transactional
public class AppointmentServiceImpl extends BaseServiceImpl implements AppointmentService {
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private AppointmentDAO appointmentDAO;
	@Autowired
	private EmployeeService employeeService;

	public List<Appointment> getOpenAppointments() {

		return appointmentDAO.getOpenAppointments();
	}

	public List<Appointment> getOpenAppointmentsForEmployee(Employee emp) {
		return appointmentDAO.getAllAppointmentsForEmployee(emp);
	}

//	public Appointment createAppointment() {
//		Appointment newOne = createAppointmentForPerson(defaultPerson());//personService.getDefaultPerson());
//		//PersonService.class.s
//		appointmentDAO.saveObject(newOne);
//		return newOne;
//	}
	
	public Appointment createAppointmentForPerson(Person person) {
		Appointment newOne = defaultAppointment();
		newOne = assignAppointmenttoPerson(newOne, person);
		appointmentDAO.saveObject(newOne);
		return newOne;
	}
	
	public void closeAppointment(Appointment appt){
		appt = getAppointmentById(appt.getId());
		appt.setAppointmentStatusType(AppointmentStatusType.CLOSED);
		appt.setUpdateDate(new DateTime());
		appointmentDAO.saveObject(appt);
	}
	
	private Appointment defaultAppointment(){
		Appointment newOne = new Appointment();
		newOne.setAppointmentDate(new DateTime());
		newOne.setEmployee(defaultEmployee());
		newOne.setPerson(defaultPerson());
		newOne.setCreateDate(new DateTime());
		newOne.setUpdateDate(new DateTime());
		newOne.setAppointmentStatusType(AppointmentStatusType.OPEN);
		newOne.setNotes("No other notes set for this appointment");
		return newOne;
	}
	
	public Appointment assignAppointmenttoPerson(Appointment appt, Person person){
		appt.setPerson(person);
		return appt;
	}

	private Employee defaultEmployee() {
		return employeeService.getDefaultEmployee();
	}

	private Person defaultPerson() {
		return new Person();//personService.getDefaultPerson();
	}

	public void startAppointment(Appointment appt) {
		appt.setAppointmentStatusType(AppointmentStatusType.WORKING);
		appt.setUpdateDate(new DateTime());
	}

	public List<Appointment> getAppointmentsForPerson(Person p, Employee emp) {
		ServiceUtils.assertNotNull("Person cannot be null",p);
		ServiceUtils.assertNotNull("employee cannot be null",emp);
		return appointmentDAO.getAppointmentsForPerson(p, emp);
	}

	public Appointment getAppointmentById(long id) {
		ServiceUtils.assertNotNull("ID cannot be null", id);
		return appointmentDAO.getObjectById(id);
	}
	
	public List<Appointment> getAppointmentsByPerson(Person p,Employee emp){
		return appointmentDAO.getAppointmentsForPerson(p, emp);
		
	}

	public void save(Appointment theAppointment) {
		appointmentDAO.saveObject(theAppointment);
		
	}
	
	@Required
	public void setAppointmentDAO(AppointmentDAO appointmentDAO) {
		this.appointmentDAO = appointmentDAO;
	}

//	@Required
//	public void setPersonService(PersonService personService) {
//		this.personService = personService;
//	}

	@Required
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
}
