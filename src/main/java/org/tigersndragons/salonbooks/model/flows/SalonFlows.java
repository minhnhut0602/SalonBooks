package org.tigersndragons.salonbooks.model.flows;

import java.io.Serializable;
import java.util.TimeZone;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tigersndragons.salonbooks.ServiceUtils;
import org.tigersndragons.salonbooks.exception.LoginNotFoundException;
import org.tigersndragons.salonbooks.exception.PersonNotFoundException;
import org.tigersndragons.salonbooks.exception.ValidationException;
import org.tigersndragons.salonbooks.model.Employee;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.service.EmployeeService;
import org.tigersndragons.salonbooks.service.PersonService;

@Component
public class SalonFlows implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private PersonService personService;
	@Autowired
	private EmployeeService employeeService;
	
	private String entityMonth;
	private Long entityDate;
	private Long entityHour;
	private Long entityMinute;
	private Long entityYear;
	protected TimeZone localeTZ;

	public Person lookupCustomer(String phoneNumber)
			throws PersonNotFoundException, ValidationException {
		Person customer = personService
				.lookupByPhoneNumber(cleanPhoneNumber(phoneNumber));
		if (customer != null) {
			return customer;
		} else {
			customer = personService.createPerson(phoneNumber);
			return customer;
		}
	}

	public String cleanPhoneNumber(String number) throws ValidationException {
		return ServiceUtils.cleanPhoneNumber(number);

	}
	
	public Employee loginEmployee(String username, String password) throws LoginNotFoundException{
		try {
			ServiceUtils.assertNotNull("username cannot be null", username);
			ServiceUtils.assertNotNull("password cannot be null", password);
			return employeeService.getEmployee(username, password);
		} catch (IllegalArgumentException e) {
			throw new LoginNotFoundException("Invalid login credentials");
		}
	}
	
	public void convertEntityDateToModel(DateTime entityDt){
		entityDt=entityDt.withZone( DateTimeZone.forOffsetHours(-6));
		setEntityDate(new Long(entityDt.dayOfMonth().get()));
		setEntityHour(new Long(entityDt.hourOfDay().get()));
		setEntityMinute(new Long (entityDt.minuteOfHour().get()));
		setEntityMonth(entityDt.monthOfYear().getAsShortText());
		setEntityYear(new Long(entityDt.year().get()));
		setLocaleTZ(entityDt.getZone().toTimeZone());
	}
	
	public DateTime convertModelToJodaTime(){
		if (entityYear==null || entityDate==null){
			return new DateTime();
		}
		DateTime dt = new DateTime(entityYear.intValue(),
				convertMonthString(entityMonth), 
				entityDate.intValue(), 
				entityHour.intValue(), 
				entityMinute.intValue());

		return dt;
		
	}
	public final int [] DATES ={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
	public final int [] HOURS ={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
	public final String [] MONTHS ={"Jan","Feb","Mar","Apr", "May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

	public final int [] YEARS ={2014,2015,2016,2017};
	public final int [] MINUTES = getMinuteValues();
			
	private int[] getMinuteValues(){
		int [] minutes= new int [60];
		int i=0;
		while (i<60){
			minutes[i]=i;
			i++;
		}
		return minutes;
	}
	private int convertMonthString(String entityMonth2) {
		if (entityMonth2==null){
			throw new IllegalArgumentException ("no month provided to read");
		}
		int index=0;
		for (String mon : MONTHS){
			index++;
			if (StringUtils.equalsIgnoreCase(mon, entityMonth2)){
				return index;
			}
		}
		return 0;
	}

	public String getEntityMonth() {
		return entityMonth;
	}

	public void setEntityMonth(String entityMonth) {
		this.entityMonth = entityMonth;
	}

	public Long getEntityDate() {
		return entityDate;
	}

	public void setEntityDate(Long entityDate) {
		this.entityDate = entityDate;
	}

	public Long getEntityHour() {
		return entityHour;
	}

	public void setEntityHour(Long entityHour) {
		this.entityHour = entityHour;
	}

	public Long getEntityMinute() {
		return entityMinute;
	}

	public void setEntityMinute(Long entityMinute) {
		this.entityMinute = entityMinute;
	}

	public Long getEntityYear() {
		return entityYear;
	}

	public void setEntityYear(Long entityYear) {
		this.entityYear = entityYear;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public int[] getDATES() {
		return DATES;
	}

	public int[] getHOURS() {
		return HOURS;
	}

	public String[] getMONTHS() {
		return MONTHS;
	}

	public  int[] getDates() {
		return DATES;
	}

	public  int[] getHours() {
		return HOURS;
	}

	public  int[] getYears() {
		return YEARS;
	}

	public TimeZone getLocaleTZ() {
		return localeTZ;
	}

	public void setLocaleTZ(TimeZone localeTZ) {
		this.localeTZ = localeTZ;
	}
	
}
