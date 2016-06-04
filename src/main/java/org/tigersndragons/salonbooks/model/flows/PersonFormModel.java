package org.tigersndragons.salonbooks.model.flows;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tigersndragons.salonbooks.ServiceUtils;
import org.tigersndragons.salonbooks.exception.PersonNotFoundException;
import org.tigersndragons.salonbooks.exception.ValidationException;
import org.tigersndragons.salonbooks.model.Address;
import org.tigersndragons.salonbooks.model.Contact;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.PersonProfile;
import org.tigersndragons.salonbooks.model.type.GenderType;
import org.tigersndragons.salonbooks.service.AddressService;
import org.tigersndragons.salonbooks.service.PersonService;
@Transactional
@Component
public class PersonFormModel extends SalonFlows {
	private static final long serialVersionUID = 1L;
	@NotNull
	@Size(min=10, max=12, message="Phone number must be between 10 and 12 characters")
	private String phoneNumber;
	private String lastName;
	private String firstName;
	private String email;
	private String twitter;
	private String line1;
	private String line2;
	private String city;
	@Pattern (regexp="[0-9][0-9]-[0-9][0-9]",message="Birthday is MM-dd format")
	private String birthday;
	@Size(max=2, message="State Code is 2 Characters ")
	private String state;
	@Size(max=5, message="Zip is 5 digits ")
	@Pattern(regexp="^([0-9]|-)+$",message="zip code is numbers only")
	private String zipCode;
	private Person person;
	private Long personId;
	private Long addreessId;
	private Address addy;
	@Size(min=10, max=12, message="Phone number must be between 10 and 12 characters")
	private String homePhoneNumber;

	private String prefix;
	private DateTime createDate;
	private DateTime addressCreateDate;

	private GenderType gender;
	
	public void convertProfiletoFormModel(PersonProfile aProfile){
		if (aProfile == null){
			return;
		}
		if (aProfile.getPerson()!=null){
			person = aProfile.getPerson();
			personId = aProfile.getPerson().getId();
			phoneNumber = aProfile.getPerson().getPrimaryPhoneNumber();
			lastName= aProfile.getPerson().getLastName();
			firstName=aProfile.getPerson().getFirstName();
			birthday=aProfile.getPerson().getBirthDate().toString("MM-dd");		
			email=aProfile.getPerson().getEmail();
			twitter =aProfile.getPerson().getTwitter();
			this.person = aProfile.getPerson();
			createDate=person.getCreateDate();
		}
		if (CollectionUtils.isNotEmpty(aProfile.getAddresses())){
			for (Address addy : aProfile.getAddresses()){
				addreessId = addy.getId();
				line1=addy.getLine1();
				line2=addy.getLine2();
				city= addy.getCity();
				state=addy.getState();
				zipCode=addy.getZip();
				addressCreateDate=addy.getCreateDate();
				this.addy= addy;
				break;
			}
		}
//		if (CollectionUtils.isNotEmpty(aProfile.getContacts())){
//			for (Contact addy : aProfile.getContacts()){
//				if (addy.getContactType().getName().equals("EMAIL")){
//					email = addy.getLabel();
//				}else if (addy.getContactType().getName().equals("TWITTER")){
//					twitter= addy.getLabel();
//				}
//			}
//		}
	}
	

	
	public PersonProfile extractProfilefromModel() throws ValidationException {
		if (this.person ==null)
		 this.person = new Person();
		
		person.setId(personId);
		person.setPrimaryPhoneNumber(	ServiceUtils.cleanPhoneNumber(phoneNumber ));//)aProfile.getPerson().getPrimaryPhoneNumber();
		person.setLastName(	lastName);
		person.setFirstName(firstName);
		person.setBirthDate(new LocalDate("2014-"+	birthday));//=aProfile.getPerson().getBirthDate().toString("MM-dd");		
		person.setEmail(email);
		person.setTwitter(twitter );
		person.setUpdateDate(new DateTime());
		person.setCreateDate(createDate);
		person.setPrefix(prefix);
		person.setGender(gender);
		if (this.addy==null)
			addy = new Address();
		addy.setId(addreessId);
		addy.setLine1(	line1);
		addy.setLine2(	line2);
		addy.setCity(city);
		addy.setState(state);
		addy.setZip(zipCode);
		addy.setBillingAddress(1);
		addy.setUpdateDate(new DateTime());
		addy.setCreateDate(addressCreateDate);
		addy.setPerson(person);
				
		PersonProfile p = new PersonProfile();
		p.setPerson(person);
		p.setAddress(addy);
		return p;
	}
	


	public Person lookupByPhoneNumber(String phoneNum) throws PersonNotFoundException, ValidationException{
		ServiceUtils.assertNotNull("Phone number cannot be null", phoneNum);
		return this.lookupCustomer(phoneNum);
	}
	
	public Person lookupByLastName(String lastName) throws PersonNotFoundException, ValidationException{
		ServiceUtils.assertNotNull("Last Name cannot be null", lastName);
		return this.lookupCustomer(lastName);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getAddreessId() {
		return addreessId;
	}

	public void setAddreessId(Long addreessId) {
		this.addreessId = addreessId;
	}

	public Address getAddy() {
		return addy;
	}

	public void setAddy(Address addy) {
		this.addy = addy;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}

	public DateTime getAddressCreateDate() {
		return addressCreateDate;
	}

	public void setAddressCreateDate(DateTime addressCreateDate) {
		this.addressCreateDate = addressCreateDate;
	}
	
}
