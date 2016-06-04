package org.tigersndragons.salonbooks.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.tigersndragons.salonbooks.model.type.ContactType;

@Entity
@Table(schema="SALONBOOKS",name="CONTACT")
@AttributeOverride(name="id", column=@Column(name="CONTACT_ID"))
public class Contact extends SalonObject {

	private static final long serialVersionUID = 1L;
	private ContactType contactType;
	private String label;
	private Person person;
	private String isActive;
	
	private String isURL;
	private String format;
	

	//@Enumerated(EnumType.ORDINAL)
	@ManyToOne
	@JoinColumn(name="TYPE_ID")
	public ContactType getContactType() {
		return contactType;
	}
	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}
	@Column(name="LABEL")
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}


	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String string) {
		this.isActive = string;
	}
	@Column(name="ISURL")
	public String getIsURL() {
		return isURL;
	}
	public void setIsURL(String isURL) {
		this.isURL = isURL;
	}

	@Column(name="FORMAT")
	public String getFormat(){
		return format;
	}
	public void setFormat(String string) {
		this.format =string;
		
	}
	@ManyToOne
	@JoinColumn(name="PERSON_ID")
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}


}
