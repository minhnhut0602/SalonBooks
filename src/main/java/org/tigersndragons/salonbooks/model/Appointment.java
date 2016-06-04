package org.tigersndragons.salonbooks.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.tigersndragons.salonbooks.model.type.AppointmentStatusType;

@Entity
@Table(schema="SALONBOOKS",name="APPOINTMENT")
@AttributeOverride(name="id", column=@Column(name="APPOINTMENT_ID"))
public class Appointment extends SalonObject {

	private static final long serialVersionUID = 1L;
	private DateTime appointmentDate;
	private String notes;
	private Person person;
	private Employee employee;
	private AppointmentStatusType appointmentStatusType;
	
	@Column(name="APPOINTMENT_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(DateTime appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	@Column(name="NOTES")
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	@ManyToOne
	@JoinColumn(name="PERSON_ID")
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}

	@ManyToOne
	@JoinColumn(name="EMPLOYEE_ID")
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

//	@Type(type="org.hibernate.type.EnumType")
//	@Transient
//	@Enumerated(EnumType.STRING)
//	@Column(name="APPOINTMENT_STATUS")
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}
	@Enumerated(EnumType.STRING)
	@Column(name="APPOINTMENT_STATUS")
	public AppointmentStatusType getAppointmentStatusType() {
		return appointmentStatusType;
	}
	public void setAppointmentStatusType(AppointmentStatusType appointmentStatusType) {
		this.appointmentStatusType = appointmentStatusType;
	}
	
	
//	@Override
//
//	public Long getId(){
//		return id;
//	}
}
