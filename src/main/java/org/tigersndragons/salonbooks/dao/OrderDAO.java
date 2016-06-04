package org.tigersndragons.salonbooks.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.tigersndragons.salonbooks.model.Appointment;
import org.tigersndragons.salonbooks.model.Employee;
import org.tigersndragons.salonbooks.model.Order;
import org.tigersndragons.salonbooks.model.OrderItem;
import org.tigersndragons.salonbooks.model.PaymentMethod;
import org.tigersndragons.salonbooks.model.Person;

@Component
public interface OrderDAO extends SalonEntityDAO<Order> {

	public List<Order> getOrdersForPerson(Person person);
	
	public List<Order> getOrdersForEmployee(Employee emp);

	public List<PaymentMethod> getPaymentMethods();
	
	public void saveOrderItem(OrderItem orderItem);
	
	public void removeOrderItem (OrderItem orderItem);

	public Order getOrderForAppointment(Appointment appointment);
}
