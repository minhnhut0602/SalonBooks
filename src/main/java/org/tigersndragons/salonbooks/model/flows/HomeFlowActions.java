package org.tigersndragons.salonbooks.model.flows;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tigersndragons.salonbooks.exception.PersonNotFoundException;
import org.tigersndragons.salonbooks.exception.ValidationException;
import org.tigersndragons.salonbooks.model.Appointment;
import org.tigersndragons.salonbooks.model.Person;
@Transactional
@Component
public class HomeFlowActions implements Serializable {

	@Autowired 
	PersonFormModel personFlowActions;
	
	private static final long serialVersionUID = 1L;
	@Size(min=10, max=12, message="Phone number must be between 10 and 12 characters")
	@Pattern(regexp="^([0-9]|\\.|-)+$",message="Phone number is numbers only")
	private String phoneNumberEntered;

	private List<Appointment> appointmentList;
	public String getPhoneNumberEntered() {
		return phoneNumberEntered;
	}
	public void setPhoneNumberEntered(String phoneNumberEntered) {
		this.phoneNumberEntered = phoneNumberEntered;
	}

	
	public Person lookupByPhoneNumber(){
		if (phoneNumberEntered!=null){
			try {
				return personFlowActions.lookupByPhoneNumber(phoneNumberEntered);
			} catch (PersonNotFoundException e) {
				e.printStackTrace();
			} catch (ValidationException e) {
				return null;
			}
		}
		return null;

	}

	public List<Appointment> getAppointmentList() {
		return appointmentList;
	}
	public void setAppointmentList(List<Appointment> appointmentList) {
		this.appointmentList = appointmentList;
	}
	@Required
	public void setPersonFlowActions(PersonFormModel personFlowActions) {
		this.personFlowActions = personFlowActions;
	}	
	public PersonFormModel getPersonFlowActions() {
		return personFlowActions;
	}
}
