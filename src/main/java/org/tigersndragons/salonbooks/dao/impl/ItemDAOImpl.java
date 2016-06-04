package org.tigersndragons.salonbooks.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.tigersndragons.salonbooks.dao.ItemDAO;
import org.tigersndragons.salonbooks.model.Item;

@Transactional
public class ItemDAOImpl implements ItemDAO, Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(ItemDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public Item getObjectById(Long id) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.idEq(id));
		return (Item) crit.uniqueResult();
	}

	public void saveObject(Item obj) {
		getSession().save(obj);
	}
	public Item getItemBySku(String sku){
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("sku",sku));
		return (Item) crit.uniqueResult();
	}

	public List<Item> getListOfItems() {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("deletedFlag","N"));
		crit.addOrder(org.hibernate.criterion.Order.asc("id"));
		return (List<Item>)crit.list();
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
	public Class<Item> getClazz() {
		return Item.class;
	}

	public void updateObject(Item obj) {

		getSession().update(obj);
	}
}
