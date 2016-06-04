package org.tigersndragons.salonbooks.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.tigersndragons.salonbooks.dao.EmployeeDAO;
import org.tigersndragons.salonbooks.dao.PersonDAO;
import org.tigersndragons.salonbooks.model.Employee;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.ProcessingContext;

public class EmployeeDAOImpl implements EmployeeDAO , Serializable{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public Employee getObjectById(Long id) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.idEq(id));
		return (Employee) crit.uniqueResult();
	}
	
	public Employee getEmployeeByUsername(String uname){
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("username", uname));
		return (Employee) crit.uniqueResult();
	}
	
	public Employee getEmployeeByCredentials(String uname, String pwrd){
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("username", uname));
		crit.add(Restrictions.eq("password", pwrd));
		return (Employee) crit.uniqueResult();
	}
	public void saveObject(Employee obj) {
		getSession().saveOrUpdate(obj);
	}


	public Class<Employee> getClazz() {
		return Employee.class;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public Session getSession(){
		Session session ;
		try {
				session = getSessionFactory().getCurrentSession();		
		}catch(HibernateException e ){
			return getSessionFactory().openSession();
		}
		return session;//getSessionFactory().getCurrentSession();
	}
	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Person lookupByPhoneNumber(String phoneNumber) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("primaryPhoneNumber", phoneNumber));
		return (Person) crit.uniqueResult();
	}

	public Person lookupByLastName(String lastName) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("lastName", lastName));
		return (Person) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Employee> getListOfActivePersons() {
		Criteria crit = getSession().createCriteria(getClazz());
		//crit.add(Restrictions.eq("status", PersonStatus.);
		return (List<Employee>) crit.list();
	}

	public void updateObject(Employee obj) {

		throw new IllegalStateException("save is not supported here");
	}

	public List<Employee> getAllEmployees() {
		// TODO Auto
		return getListOfActivePersons();
	}


}
