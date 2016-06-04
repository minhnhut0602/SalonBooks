package org.tigersndragons.salonbooks.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tigersndragons.salonbooks.model.flows.LoginFlowActions;
import org.tigersndragons.salonbooks.service.flow.LoginService;


@Controller
//@RequestMapping("/")
public class LoginController {
  	@Autowired
	private LoginService loginService;
//	@Autowired
//	LoginFlowActions loginActionFlows;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLogin(
			//@ModelAttribute("loginFlowActions") LoginFlowActions loginFlowActions, 
			Model model){

		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String doLogin(@ModelAttribute("loginFlowActions") LoginFlowActions loginFlowActions, 
			Model model){
		if (loginService.doLogin()!=null){
			model.addAttribute("employee", loginService.doLogin());
			return "home";
		}else{
			return "login";
		}
	}

//	public LoginService getLoginService() {
//		return loginService;
//	}
//
////	@Required
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	 
}
