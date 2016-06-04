package org.tigersndragons.salonbooks.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.tigersndragons.salonbooks.dao.AddressDAO;
import org.tigersndragons.salonbooks.model.Address;
import org.tigersndragons.salonbooks.model.Person;

@Transactional
public class AddressDAOImpl implements AddressDAO , Serializable{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(AddressDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public Address getObjectById(Long id) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.idEq(id));
		return (Address) crit.uniqueResult();
	}

	public void saveObject(Address obj) {
		getSession().save(obj);
	}
	public void updateObject(Address obj) {
		getSession().saveOrUpdate(obj);
		getSession().flush();
	}

	public Class<Address> getClazz() {
		return Address.class;
	}

	@SuppressWarnings("unchecked")
	public List<Address> getActiveAddresses() {
		Criteria crit = getSession().createCriteria(getClazz());
		//crit.add(Restrictions.eq("isActive", "N"));
		crit.addOrder(Order.desc("updateDate"));
		return (List<Address>)crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<Address> getAllAddresses() {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.addOrder(Order.desc("updateDate"));
		return (List<Address>)crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<Address> getAddressForPerson(Person emp) {
		Criteria crit = getSession().createCriteria(getClazz());
		//crit.add(Restrictions.eq("isActive", "N"));
		crit.add(Restrictions.eq("person",emp));
		crit.addOrder(Order.desc("updateDate"));
		return (List<Address>)crit.list();
	}

	public Address getBillingAddressForPerson(Person p) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("person", p));
		crit.add(Restrictions.eq("billingAddress", 1));
		crit.addOrder(Order.desc("updateDate"));
		return (Address) crit.uniqueResult();
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


}
