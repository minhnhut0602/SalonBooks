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
import org.springframework.transaction.annotation.Transactional;
import org.tigersndragons.salonbooks.dao.PersonDAO;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.ProcessingContext;

@Transactional
public class PersonDAOImpl implements PersonDAO, Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(PersonDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public Person getObjectById(Long id) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.idEq(id));
		return (Person) crit.uniqueResult();
	}
	
	public void saveObject(Person obj) {
		getSession().saveOrUpdate(obj);
	}
	public Class<Person> getClazz() {
		return Person.class;
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
	public List<Person> getListOfActivePersons() {
		Criteria crit = getSession().createCriteria(getClazz());
		//crit.add(Restrictions.eq("status", PersonStatus.);
		return (List<Person>) crit.list();
	}
	public void updateObject(Person obj) {
		getSession().saveOrUpdate(obj);
		getSession().flush();
	}

}
