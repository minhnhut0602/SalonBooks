package org.tigersndragons.salonbooks.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.tigersndragons.salonbooks.ServiceUtils;
import org.tigersndragons.salonbooks.dao.EmployeeDAO;
import org.tigersndragons.salonbooks.model.Employee;
import org.tigersndragons.salonbooks.service.EmployeeService;
import org.tigersndragons.salonbooks.service.EncryptionService;

public class EmployeeServiceImpl extends BaseServiceImpl  implements EmployeeService {
	
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private EmployeeDAO employeeDAO;
	private EncryptionService encryptionService;
	
	public Employee getDefaultEmployee() {
		Employee emp = new Employee();
		emp.setId(0L);
		return emp;
	}

	public Employee getEmployee(String uname, String pwrd) {
		// TODO Auto-generated method stub
		return getDefaultEmployee();
	}

	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	@Required
	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	public Employee getEmployeeById(Long id) {
		ServiceUtils.assertNotNull("ID cannot be null", id);
		Employee e=  employeeDAO.getObjectById(id);
		e.setPassword(e.getPassword());//this.getDecryptedStringForString(e.getPassword()));
		return e;
	}

	private String getEncryptedStringForString(String clear){
		return encryptionService.encryptString(clear);
	}
	
	private String getDecryptedStringForString(String clear){
		return encryptionService.decryptString(clear);
	}
	@Required
	public void setEncryptionService(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}

	public java.util.List<Employee> getAllEmployees() {
		return employeeDAO.getAllEmployees();
		
	}
}
