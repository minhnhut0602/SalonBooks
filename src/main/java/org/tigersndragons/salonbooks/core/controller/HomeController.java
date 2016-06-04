package org.tigersndragons.salonbooks.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.flows.HomeFlowActions;
import org.tigersndragons.salonbooks.model.flows.LoginFlowActions;
import org.tigersndragons.salonbooks.model.flows.PersonFormModel;
import org.tigersndragons.salonbooks.service.AppointmentService;
import org.tigersndragons.salonbooks.service.PersonService;
import org.tigersndragons.salonbooks.service.flow.LoginService;


@Controller
public class HomeController {
  	@Autowired
	private PersonService personService;
  	@Autowired
  	PersonFormModel personFlowActions;
  	@Autowired
  	private AppointmentService appointmentService;
  	@Autowired
  	HomeFlowActions homeFlowActions;
  	@Autowired
	private LoginService loginService;
	@Autowired
	LoginFlowActions loginActionFlows;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLogin(Model model){
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String doLogin(@ModelAttribute("loginFlowActions") LoginFlowActions loginFlowActions, 
			BindingResult result,
			Model model){
		if (loginService.doLogin()!=null 
				|| !result.hasErrors()){
			model.addAttribute("employee", loginService.doLogin());
			result.getModel().put("employee", loginService.doLogin());
			result.getModel().put("homeFlowActions", homeFlowActions);
			return "redirect:/home";
		}else{
			return "login";
		}
	}


	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String showHome(
			Model model){
		homeFlowActions.setAppointmentList(appointmentService.getOpenAppointments()); 
		model.addAttribute("openAppointments",homeFlowActions.getAppointmentList());
		Person person = new Person();
		model.addAttribute("person", person);
		model.addAttribute("homeFlowActions", homeFlowActions);
		return "home";
	}
	
	@RequestMapping(value="/home", method=RequestMethod.POST)
	public String lookupByPhoneNumber(
			@ModelAttribute("homeFlowActions")  HomeFlowActions homeFlowActions, 
			@RequestParam ("phoneNumberEntered") String phoneNumberEntered,
			BindingResult result,
			Model model){
		Person person=null;

		if (homeFlowActions.getPersonFlowActions()==null){
			homeFlowActions.setPersonFlowActions(personFlowActions);
		}
		person = homeFlowActions.lookupByPhoneNumber();//.lookupByPhoneNumber(phoneNumberEntered);

		if (person==null 
				|| person.getId()==null){
			person =personService.createPerson(phoneNumberEntered);
			personService.save(person);
		}
		model.addAttribute("person", person);
		return "redirect:/person/"+person.getPrimaryPhoneNumber();
	}
	
	@Required
	public void setPersonService(PersonService personService){
		this.personService= personService;
	}

	@Required
	public void setAppointmentService(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}
	public void  setHomeFlowActions(HomeFlowActions homeFlowActions){
		this.homeFlowActions = homeFlowActions;
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
}
