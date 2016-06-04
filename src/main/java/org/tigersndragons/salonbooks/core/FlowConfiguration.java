package org.tigersndragons.salonbooks.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.convert.service.GenericConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.mvc.servlet.FlowController;
import org.springframework.webflow.mvc.servlet.FlowHandler;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.security.SecurityFlowExecutionListener;
import org.springframework.webflow.expression.spel.WebFlowSpringELExpressionParser ;
import org.tigersndragons.salonbooks.model.flows.LoginFlowActions;
import org.tigersndragons.salonbooks.model.flows.SalonFlowHandler;
import org.tigersndragons.salonbooks.model.utils.StringToClassConverter;

@Configuration
public class FlowConfiguration extends AbstractFlowConfiguration{

	@Autowired 
	WebMVCConfiguration webMVCConfiguration;
	
	@Bean
	public FlowExecutor flowExecutor() {
		return getFlowExecutorBuilder(flowRegistry())
//				.addFlowExecutionListener(new SecurityFlowExecutionListener(), "*")
				.build();
	}
	@Bean
	public FlowHandlerMapping flowHandlerMapping() {
		FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
		handlerMapping.setFlowRegistry(flowRegistry());
		handlerMapping.setOrder(-1);
		return handlerMapping;
	}

	@Bean
	public FlowHandlerAdapter flowHandlerAdapter() {
		FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
		handlerAdapter.setFlowExecutor(flowExecutor());
		handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
		return handlerAdapter;
	}
	@Bean 
	public FlowController flowController (){
		FlowController flowController = new FlowController();
		flowController.setFlowExecutor(flowExecutor());		
		flowController.setFlowHandlerAdapter(flowHandlerAdapter());
		Map<String , FlowHandler> flowhandlers = new HashMap<String , FlowHandler> ();
		flowhandlers.put("", salonFlowHandler());
		flowController.setFlowHandlers(flowhandlers);
		return flowController;
	}

	@Bean
	public FlowDefinitionRegistry flowRegistry() {
		return getFlowDefinitionRegistryBuilder(flowBuilderServices())
				//.setBasePath("/WEB-INF/flows")
				//.addFlowLocationPattern("/**/*-flow.xml")
				.addFlowLocation("/WEB-INF/flows/login-flow.xml","login")
				.addFlowLocation("/WEB-INF/flows/home/home-flow.xml","home")
				.addFlowLocation("/WEB-INF/flows/person/person-flow.xml","person")
				.addFlowLocation("/WEB-INF/flows/appointment/appointment-flow.xml","appointment")
				.addFlowLocation("/WEB-INF/flows/order/order-flow.xml","order")
				.build();
	}

	@Bean
	public FlowBuilderServices flowBuilderServices() {
		return getFlowBuilderServicesBuilder()
//				.setConversionService(webflowConversionService())
//				.setExpressionParser(webFlowExpressionParser())
				.setViewFactoryCreator(mvcViewFactoryCreator())
				.setValidator(validator())
				.setDevelopmentMode(true)
				.build();
	}

	@Bean
	public SalonFlowHandler salonFlowHandler() {
		return new SalonFlowHandler();
	}
	@Bean
	public MvcViewFactoryCreator mvcViewFactoryCreator() {
		MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
		factoryCreator.setDefaultViewSuffix(".jsp");
		factoryCreator.setViewResolvers(Arrays.<ViewResolver>asList(webMVCConfiguration.viewResolver()));
		factoryCreator.setUseSpringBeanBinding(true);
		return factoryCreator;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}
	


//	@Bean
//	public ConfigurableWebBindingInitializer webBindingInitializer(){
//		ConfigurableWebBindingInitializer initializer = new ConfigurableWebBindingInitializer();
//		initializer.setConversionService( conversionService().getObject());
//		initializer.setValidator(validator());
//		return initializer;
//	}
//	@Bean 
//	public GenericConversionService webflowConversionService(){
//		GenericConversionService bindingService = new GenericConversionService(conversionService().getObject());
//		return bindingService;
//	}
//	@Bean
//	public FormattingConversionServiceFactoryBean conversionService() {
//		FormattingConversionServiceFactoryBean bean = new FormattingConversionServiceFactoryBean();
//		@SuppressWarnings("rawtypes")
//		Set<Converter> converters = new HashSet<Converter>();
//		converters.add(new StringToClassConverter());
//		bean.setConverters(converters);
//		return bean;
//	}
//	@Bean 
//	public SpelParserConfiguration spelConfiguration(){
//		SpelParserConfiguration config = new SpelParserConfiguration(true,true);
//		return config;
//	}
//	@Bean 
//	public SpelExpressionParser spelExpressionParser(){
//		SpelExpressionParser config = new SpelExpressionParser(spelConfiguration());
//		return config;
//	}
//	@Bean
//	public WebFlowSpringELExpressionParser webFlowExpressionParser(){
//		WebFlowSpringELExpressionParser parser = new WebFlowSpringELExpressionParser(spelExpressionParser(),webflowConversionService());
//		return parser;
//	}
}
