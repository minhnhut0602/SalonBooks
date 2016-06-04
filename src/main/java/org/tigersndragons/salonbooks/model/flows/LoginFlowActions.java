package org.tigersndragons.salonbooks.model.flows;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tigersndragons.salonbooks.model.Employee;
import org.tigersndragons.salonbooks.service.EmployeeService;

@Component
public class LoginFlowActions implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private Employee employee;
	
	@Autowired
	private EmployeeService employeeService;
	
	public Employee checkEmployee (String user, String pswd){
		return employeeService.getEmployee(user, pswd);
	}
	
	public Employee doLogin(){
		if (StringUtils.isEmpty(username)
				|| StringUtils.isEmpty(password)){
			return null;
		}else{
			return checkEmployee(username, password);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
}
