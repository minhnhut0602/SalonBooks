package org.tigersndragons.salonbooks.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.tigersndragons.salonbooks.model.Employee;
@Component
public interface EmployeeDAO extends SalonEntityDAO<Employee>{
	
	public Employee getEmployeeByCredentials(String uname, String pswd);
	
	public Employee getEmployeeByUsername(String pwrd);

	public List<Employee> getAllEmployees();
}
