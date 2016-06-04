package org.tigersndragons.salonbooks.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.tigersndragons.salonbooks.model.Appointment;
import org.tigersndragons.salonbooks.model.Employee;
import org.tigersndragons.salonbooks.model.Person;
@Component
public interface AppointmentDAO extends SalonEntityDAO<Appointment> {
	
	public List<Appointment> getOpenAppointments();
	
	public List<Appointment> getAllAppointments();
	
	public List<Appointment> getOpenAppointmentsForEmployee(Employee emp);
	
	public List<Appointment> getAllAppointmentsForEmployee(Employee emp);

	public List<Appointment> getAppointmentsForPerson(Person p, Employee emp);

}
