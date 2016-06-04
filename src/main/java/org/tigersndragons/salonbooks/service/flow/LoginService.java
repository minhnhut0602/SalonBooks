package org.tigersndragons.salonbooks.service.flow;

import org.tigersndragons.salonbooks.model.Employee;

public interface LoginService {
	public Employee checkEmployee (String user, String pswd);
	public Employee doLogin();
	
}
