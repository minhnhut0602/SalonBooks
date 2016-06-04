package org.tigersndragons.salonbooks.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.tigersndragons.salonbooks.model.Person;

@Component
public interface PersonDAO extends SalonEntityDAO<Person> {
	public Person lookupByPhoneNumber(String phoneNumber) ;

	public Person lookupByLastName(String lastName) ;

	public List<Person> getListOfActivePersons() ;
}
