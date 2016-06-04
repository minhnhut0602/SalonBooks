package org.tigersndragons.salonbooks.dao;

import java.util.List;

import org.tigersndragons.salonbooks.model.Address;
import org.tigersndragons.salonbooks.model.Contact;
import org.tigersndragons.salonbooks.model.Person;

public interface AddressDAO  extends SalonEntityDAO<Address>{

	public List<Address> getAddressForPerson(Person p);

	public List<Address> getActiveAddresses();

	public Address getBillingAddressForPerson(Person p);

}
