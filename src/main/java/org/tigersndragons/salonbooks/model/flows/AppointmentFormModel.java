package org.tigersndragons.salonbooks.model.flows;

import java.util.TimeZone;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tigersndragons.salonbooks.ServiceUtils;
import org.tigersndragons.salonbooks.exception.PersonNotFoundException;
import org.tigersndragons.salonbooks.exception.ValidationException;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.type.AppointmentStatusType;

@Transactional
@Component
public class AppointmentFormModel extends SalonFlows {
	private static final long serialVersionUID = 1L;
	
	private String notes;
	private Long appointmentId;
	private Long personId;
	private DateTime appointmentDate;
	private boolean addOrdertoAppointment = false;
	private DateTime createDate;
	
	private AppointmentStatusType appointmentStatusType;
	
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public AppointmentStatusType getAppointmentStatusType() {
		return appointmentStatusType;
	}
	public void setAppointmentStatusType(AppointmentStatusType appointmentStatusType) {
		this.appointmentStatusType = appointmentStatusType;
	}
	public boolean isAddOrdertoAppointment() {
		return addOrdertoAppointment;
	}
	public void setAddOrdertoAppointment(boolean addOrdertoAppointment) {
		this.addOrdertoAppointment = addOrdertoAppointment;
	}
	public DateTime getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(DateTime appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public DateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}
	public TimeZone getLocaleTZ() {
		return localeTZ;
	}
	public void setLocaleTZ(TimeZone localeTZ) {
		this.localeTZ = localeTZ;
	}

}