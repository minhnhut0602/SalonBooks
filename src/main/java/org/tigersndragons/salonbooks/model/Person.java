package org.tigersndragons.salonbooks.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.LocalDate;
import org.tigersndragons.salonbooks.model.type.GenderType;

@Entity
@Table(schema="SALONBOOKS",name="PERSON")
@AttributeOverride(name="id", column=@Column(name="PERSON_ID"))
public class Person extends SalonObject {
	
	private static final long serialVersionUID = 1L;
	
	private String firstName="Unknown";
	private String middleName="Unknown";
	private String lastName="Unknown";
	private LocalDate birthDate=null;
	private GenderType gender=GenderType.U;
	private String prefix="Ms";
	private String suffix="";

	private String email="default@email.ca";
	private String homePhoneNumber;
	private String twitter="@default";
	
	
	private String primaryPhoneNumber;
	
	@Column(name="FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}
	@Column(name="MIDDLE_NAME")
	public String getMiddleName() {
		return middleName;
	}
	@Column(name="LAST_NAME")
	public String getLastName() {
		return lastName;
	}
	@Column(name="PREFIX")
	public String getPrefix() {
		return prefix;
	}
	@Column(name="SUFFIX")
	public String getSuffix() {
		return suffix;
	}
	@Column(name="BIRTH_DATE")
	public LocalDate getBirthDate() {
		return birthDate;
	}
	@Column(name="GENDER")
	@Enumerated(EnumType.STRING)
	public GenderType getGender() {
		return gender;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public void setGender(String gender) {
		this.gender = GenderType.valueOf(gender);
	}
	
	public void setGender(GenderType gender) {
		this.gender = gender;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	@Column(name="PRIMARY_PHONENUMBER")
	public String getPrimaryPhoneNumber() {
		return primaryPhoneNumber;
	}
	public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
		this.primaryPhoneNumber = primaryPhoneNumber;
	}
	@Column(name="EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="HOME_PHONE")
	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}
	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}
	@Column(name="TWITTER")
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	@Override
	public String toString() {
		return this.id + "," + this.firstName + " " +this.getLastName() + "," +this.primaryPhoneNumber+" | ";
	}
	
}
