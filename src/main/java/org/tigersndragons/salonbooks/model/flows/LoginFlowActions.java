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

	
}
