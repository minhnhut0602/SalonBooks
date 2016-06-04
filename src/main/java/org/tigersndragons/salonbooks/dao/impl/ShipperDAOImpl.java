package org.tigersndragons.salonbooks.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.tigersndragons.salonbooks.dao.ShipperDAO;
import org.tigersndragons.salonbooks.model.ShippingMethod;

@Transactional
public class ShipperDAOImpl implements ShipperDAO, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private SessionFactory sessionFactory;
	public ShippingMethod getObjectById(Long id) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.idEq(id));
		return (ShippingMethod) crit.uniqueResult();
	}

	public Class<ShippingMethod> getClazz() {
		return ShippingMethod.class;
	}
	public void saveObject(ShippingMethod obj) {
		throw new IllegalStateException("save is not supported here");
		//return;

	}

	@SuppressWarnings("unchecked")
	public List<ShippingMethod> getActiveShipperList() {
		Criteria crit = getSession().createCriteria(getClazz());

		return (List<ShippingMethod>) crit.list();
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

	public void updateObject(ShippingMethod obj) {
		return;
	}
}
