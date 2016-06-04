package org.tigersndragons.salonbooks.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema="SALONBOOKS",name="ADDRESS")
@AttributeOverride(name="id", column=@Column(name="ADDRESS_ID"))
public class Address extends SalonObject {

	private static final long serialVersionUID = 1L;
	
	private String line1;
	private String line2;
	private String city;
	private String state;
	private String zip;
	private String zip4;
	private Person person;
	private int billingAddress;
	
	@Column(name="LINE1")
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	@Column(name="LINE2")
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	@Column(name="CITY")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@Column(name="STATE")
	public String getState() {
		return state;
	}
	public void setState(String stateCode) {
		this.state = stateCode;
	}
	
	@Column(name="ZIPCODE")
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	@Column(name="BILLING_ADDRESS")
	public int getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(int billingAddress) {
		this.billingAddress = billingAddress;
	}
	@ManyToOne
	@JoinColumn(name="PERSON_ID")
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person personId) {
		this.person = personId;
	}
	@Column(name="ZIP4")
	public String getZip4() {
		return zip4;
	}
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}
	
//	@Override
//	public Long getId(){
//		return id;
//	}
}
