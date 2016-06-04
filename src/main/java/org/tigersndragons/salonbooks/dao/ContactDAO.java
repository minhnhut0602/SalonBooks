package org.tigersndragons.salonbooks.dao;

import java.util.List;

import org.tigersndragons.salonbooks.model.Contact;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.type.ContactType;

public interface ContactDAO  extends SalonEntityDAO<Contact>{

	public List<Contact> getContactsForPerson(Person p);

	public List<Contact> getActiveContacts();

	public List<ContactType> getContactTypeList();

}
