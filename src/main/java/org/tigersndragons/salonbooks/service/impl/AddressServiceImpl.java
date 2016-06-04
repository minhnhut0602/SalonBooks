package org.tigersndragons.salonbooks.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.tigersndragons.salonbooks.dao.AddressDAO;
import org.tigersndragons.salonbooks.model.Address;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.service.AddressService;

@Transactional
public class AddressServiceImpl extends BaseServiceImpl implements AddressService {

	private static final long serialVersionUID = 1L;
	@Autowired
	private AddressDAO addressDAO;
	//private PersonService personService;
//	private EmployeeService employeeService;
	public Address getAddressbyId(Long id) {
		return addressDAO.getObjectById(id);
	}

	public List<Address> getAddressByPerson(Person p) {
		return addressDAO.getAddressForPerson(p);
	}

	public void saveAddress(Address addy) {
		if (addy.getId()==null){
			addressDAO.saveObject(addy);
		}else{
			addressDAO.updateObject(addy);
		}
	}

	public Address getBillingAddressForPerson(Person p) {
		return addressDAO.getBillingAddressForPerson(p);
	}

	public Address createDefaultAddress() {
		Address emp = new Address();
		//emp.setId(0L);
		emp.setLine1("default");
		emp.setCity("Des Moines");
		emp.setState("IA");
		emp.setZip("50315");
		//emp.setPerson(personService.getDefaultPerson());
		emp.setCreateDate(new DateTime());
		emp.setUpdateDate(new DateTime());
		return emp;	
	}
	
	public Address createDefaultAddressForPerson(Person p) {
		Address addy = createDefaultAddress();
		addy.setPerson(p);
		return addy;
	}
	@Required
	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}

//	@Required
//	public void setPersonService(PersonService personService) {
//		this.personService = personService;
//	}
//	@Required
//	public void setEmployeeService(EmployeeService employeeService) {
//		this.employeeService = employeeService;
//	}
	

}
