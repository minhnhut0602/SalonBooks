package org.tigersndragons.salonbooks.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.tigersndragons.salonbooks.dao.ContactDAO;
import org.tigersndragons.salonbooks.model.Contact;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.type.ContactType;

public class ContactDAOImpl implements ContactDAO , Serializable{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(ContactDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public Contact getObjectById(Long id) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.idEq(id));
		return (Contact) crit.uniqueResult();
	}

	public void saveObject(Contact obj) {
		getSession().save(obj);
	}

	public Class<Contact> getClazz() {
		return Contact.class;
	}

	@SuppressWarnings("unchecked")
	public List<Contact> getActiveContacts() {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("isActive", "N"));
		crit.addOrder(Order.desc("updateDate"));
		return (List<Contact>)crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<Contact> getAllContacts() {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.addOrder(Order.desc("updateDate"));
		return (List<Contact>)crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<Contact> getContactsForPerson(Person emp) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("isActive", "N"));
		crit.add(Restrictions.eq("person",emp));
		crit.addOrder(Order.desc("updateDate"));
		return (List<Contact>)crit.list();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public Session getSession(){
		return getSessionFactory().getCurrentSession();
	}
	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<ContactType> getContactTypeList() {
		Criteria crit = getSession().createCriteria(ContactType.class);
		//crit.addOrder(Order.desc("updateDate"));
		return (List<ContactType>)crit.list();
	}

	public void updateObject(Contact obj) {

		getSession().update(obj);
	}

}
