package org.tigersndragons.salonbooks.core;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.tigersndragons.salonbooks.core.controller.*;
import org.tigersndragons.salonbooks.model.flows.AddOrderItemActions;
import org.tigersndragons.salonbooks.model.flows.AppointmentFormModel;
import org.tigersndragons.salonbooks.model.flows.HomeFlowActions;
import org.tigersndragons.salonbooks.model.flows.LoginFlowActions;
import org.tigersndragons.salonbooks.model.flows.OrderFormModel;
import org.tigersndragons.salonbooks.model.flows.PersonFormModel;
import org.tigersndragons.salonbooks.service.flow.LoginService;
import org.tigersndragons.salonbooks.service.flow.impl.LoginServiceImpl;

@Configuration
public class WebMVCConfiguration extends WebMvcConfigurerAdapter {

	
	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
		SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
		Properties props = new Properties();
		props.put("org.tigersndragons.salonbooks.exception.CurrentErrorException", "error/generic_error");
		props.put("java.lang.Exception", "error/exception_error");
		resolver.setExceptionMappings(props);
		return resolver;
	}

	@Bean 
	public ControllerClassNameHandlerMapping controllerClassNameHandlerMapping(){
		ControllerClassNameHandlerMapping mapping  =  new ControllerClassNameHandlerMapping();
		 mapping.setPathPrefix("/view");
		return mapping;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/", "classpath:/META-INF/web-resources/");
		registry.addResourceHandler("/resources/**").addResourceLocations("/", "/resources/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {		
//		registry.addViewController("/");
//		registry.addViewController("/login");
//		registry.addViewController("/home");
//		registry.addViewController("/person");
	}

/**/	
	@Bean 
	public SimpleUrlHandlerMapping urlMappings(){
		SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
		Properties urlProperties = new Properties();
		urlProperties.put("/login",homeController());
		urlProperties.put("/home",homeController());
		urlProperties.put("/person",personController());
		urlProperties.put("/appointment",appointmentController());
		urlProperties.put("/order",orderController());
		urlProperties.put("/report",orderController());
		urlProperties.put("/item",itemController());
/*		urlProperties.put("/*", webFlowConfig.flowController());
		urlProperties.put("login", webFlowConfig.flowController());
		urlProperties.put("home", webFlowConfig.flowController());
		urlProperties.put("person", webFlowConfig.flowController());
		urlProperties.put("appointment", webFlowConfig.flowController());
		urlProperties.put("order", webFlowConfig.flowController());
		*/
		simpleUrlHandlerMapping.setMappings(urlProperties);
		simpleUrlHandlerMapping.setAlwaysUseFullPath(true);
		return simpleUrlHandlerMapping;
	}
/**/
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
//		org.springframework.web.servlet.view.UrlBasedViewResolver urlResolver = new org.springframework.web.servlet.view.UrlBasedViewResolver();
//		urlResolver.setPrefix("/WEB-INF/flows/");
//		urlResolver.setSuffix(".jsp");
//		urlResolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
//		return urlResolver;
	}
	
	/*@Bean
	public MvcViewFactoryCreator mvcViewFactoryCreator() {
		MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
		factoryCreator.setDefaultViewSuffix(".jsp");
		factoryCreator.setViewResolvers(Arrays.<ViewResolver>asList(viewResolver()));
		factoryCreator.setUseSpringBeanBinding(true);
		return factoryCreator;
	}*/

	@Bean
	public LoginService loginService(){
		LoginServiceImpl homeService = new LoginServiceImpl();
		return homeService;
	}
	@Bean
	public LoginFlowActions loginFlowActions(){
		return new LoginFlowActions();
	}
	@Bean
	public PersonFormModel personFlowActions(){
		return new PersonFormModel();
	}
	@Bean
	public HomeFlowActions homeFlowActions(){
		HomeFlowActions homeFlowActions =new HomeFlowActions();
		homeFlowActions.setPersonFlowActions(personFlowActions());
		return homeFlowActions;
	}
	@Bean
	public AppointmentFormModel appointmentFlowActions(){
		return new AppointmentFormModel();
	}
	@Bean
	public OrderFormModel orderFlowActions(){
		return new OrderFormModel();
	}
	@Bean
	public AddOrderItemActions addOrderItemActions(){
		return new AddOrderItemActions();
	}

    @Bean
    public LoginController loginController(){
        LoginController controller = new LoginController();
        return controller;
    }
	@Bean
	public HomeController homeController(){
		HomeController controller = new HomeController();
//		controller.setPersonService(serviceConfig.personService());
//		controller.setAppointmentService(serviceConfig.appointmentService());
		controller.setHomeFlowActions(homeFlowActions());

		return controller;
	}
	@Bean
	public PersonController personController(){
		PersonController controller = new PersonController();
		return controller;
	}
	
	@Bean
	public AppointmentController appointmentController(){
		AppointmentController controller = new AppointmentController();
		return controller;
	}
	@Bean
	public OrderController orderController(){
		OrderController controller = new OrderController();
		return controller;
	}
	@Bean
	public ItemController itemController(){
		ItemController controller = new ItemController();
		return controller;
	}
}
