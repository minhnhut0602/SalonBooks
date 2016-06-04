package org.tigersndragons.salonbooks.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tigersndragons.salonbooks.dao.OrderDAO;
import org.tigersndragons.salonbooks.model.Appointment;
import org.tigersndragons.salonbooks.model.Employee;
import org.tigersndragons.salonbooks.model.Order;
import org.tigersndragons.salonbooks.model.OrderItem;
import org.tigersndragons.salonbooks.model.PaymentMethod;
import org.tigersndragons.salonbooks.model.Person;

@Repository
@Transactional
public class OrderDAOImpl implements OrderDAO , Serializable{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(OrderDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public Order getObjectById(Long id) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.idEq(id));
		crit.setFetchMode("orderItems", FetchMode.JOIN);
		return (Order) crit.uniqueResult();
	}
	public Set<OrderItem> getOrderItemsByOrderId(Long id) {
		Criteria crit = getSession().createCriteria(OrderItem.class);
		crit.add(Restrictions.eq("orderid",id));
		return (Set<OrderItem>) crit.list();
	}

	public void saveObject(Order obj) {
		obj.setUpdateDate(new DateTime());
		getSession().saveOrUpdate(obj);
		getSession().flush();
	}

	@SuppressWarnings("unchecked")
	public List<Order> getOrdersForPerson(Person person) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("person",person));
		crit.addOrder(org.hibernate.criterion.Order.desc("id"));
		return (List<Order>)crit.list();
	}
	
	public Order getOrderForAppointment(Appointment appointment) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("appointment",appointment));
		crit.addOrder(org.hibernate.criterion.Order.desc("id"));
		return (Order) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Order> getOrdersForEmployee(Employee emp) {
		Criteria crit = getSession().createCriteria(getClazz());
		//crit.add(Restrictions.eq("employee",emp));
		crit.addOrder(org.hibernate.criterion.Order.desc("id"));
		return (List<Order>)crit.list();
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
		return session;
	}
	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public Class<Order> getClazz() {
		return Order.class;
	}

	public void updateObject(Order obj) {
		obj.setUpdateDate(new DateTime());
		getSession().saveOrUpdate(obj);
		getSession().flush();
	}
	
	

	@SuppressWarnings("unchecked")
	public List<PaymentMethod> getPaymentMethods() {
		Criteria crit = getSession().createCriteria(PaymentMethod.class);
		return (List<PaymentMethod>)crit.list();
	}

	public void saveOrderItem(OrderItem orderItem) {
//		getSession().merge(orderItem) ;
		OrderItem existing =(OrderItem) getSession().get(OrderItem.class, orderItem.getPk());
		if (existing != null){
			getSession().saveOrUpdate(
					getSession().merge(orderItem));
			getSession().flush();
		}else{
			orderItem.setCreateDate(new DateTime());
			getSession().saveOrUpdate(orderItem);
			getSession().flush();
		}
	}

	public void removeOrderItem(OrderItem orderItem) {
		getSession().delete(orderItem);
		getSession().flush();		
	}
}
