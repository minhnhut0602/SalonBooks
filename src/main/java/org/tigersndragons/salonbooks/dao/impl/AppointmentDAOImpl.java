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
import org.tigersndragons.salonbooks.dao.AppointmentDAO;
import org.tigersndragons.salonbooks.model.Appointment;
import org.tigersndragons.salonbooks.model.Employee;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.type.AppointmentStatusType;

@Transactional
public class AppointmentDAOImpl implements AppointmentDAO , Serializable{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(AppointmentDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	public Appointment getObjectById(Long id) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.idEq(id));
		return (Appointment) crit.uniqueResult();
	}

	public void saveObject(Appointment obj) {
		getSession().saveOrUpdate(obj);
		getSession().flush();
	}

	public Class<Appointment> getClazz() {
		return Appointment.class;
	}

	public List<Appointment> getOpenAppointments() {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("appointmentStatusType",AppointmentStatusType.OPEN));
		crit.addOrder(Order.desc("appointmentDate"));
		return (List<Appointment>)crit.list();
	}

	public List<Appointment> getAllAppointments() {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.addOrder(Order.desc("appointmentDate"));
		return (List<Appointment>)crit.list();
	}

	public List<Appointment> getOpenAppointmentsForEmployee(Employee emp) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("appointmentStatusType",AppointmentStatusType.OPEN));
		crit.add(Restrictions.eq("employee",emp));
		crit.addOrder(Order.desc("appointmentDate"));
		return (List<Appointment>)crit.list();
	}

	public List<Appointment> getAllAppointmentsForEmployee(Employee emp) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("employee",emp));
		crit.addOrder(Order.desc("appointmentDate"));
		crit.addOrder(Order.desc("appointmentStatusType"));
		return (List<Appointment>)crit.list();
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

	@SuppressWarnings("unchecked")
	public List<Appointment> getAppointmentsForPerson(Person p, Employee emp) {
		Criteria crit = getSession().createCriteria(getClazz());
		crit.add(Restrictions.eq("employee",emp));
		crit.add(Restrictions.eq("person",p));
		crit.addOrder(Order.desc("appointmentDate"));
		crit.addOrder(Order.desc("appointmentStatusType"));
		return (List<Appointment>)crit.list();
		
	}

	public void updateObject(Appointment obj) {

		getSession().update(obj);
		getSession().flush();
	}

}
