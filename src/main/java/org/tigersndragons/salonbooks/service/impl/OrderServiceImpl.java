package org.tigersndragons.salonbooks.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.tigersndragons.salonbooks.ServiceUtils;
import org.tigersndragons.salonbooks.dao.OrderDAO;
import org.tigersndragons.salonbooks.model.Appointment;
import org.tigersndragons.salonbooks.model.Employee;
import org.tigersndragons.salonbooks.model.Item;
import org.tigersndragons.salonbooks.model.MonthlyTotals;
import org.tigersndragons.salonbooks.model.Order;
import org.tigersndragons.salonbooks.model.OrderItem;
import org.tigersndragons.salonbooks.model.PaymentMethod;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.type.OrderStatusType;
import org.tigersndragons.salonbooks.service.AppointmentService;
import org.tigersndragons.salonbooks.service.EmployeeService;
import org.tigersndragons.salonbooks.service.ItemService;
import org.tigersndragons.salonbooks.service.OrderService;
import org.tigersndragons.salonbooks.service.PaymentService;
import org.tigersndragons.salonbooks.service.ShippingMethodService;

@Transactional
public class OrderServiceImpl extends BaseServiceImpl  implements OrderService {

	private static final long serialVersionUID = 1L;
	@Autowired
	private OrderDAO orderDAO ;

	private ItemService itemService;

	private PaymentService paymentService;

	private ShippingMethodService shipperService;

	private EmployeeService employeeService;

	private AppointmentService appointmentService;
	
	private static BigDecimal taxRate =new BigDecimal("0.06");
	
	public List<Order> getListOfActiveOrders() {
		List<Order> orders = orderDAO.getOrdersForEmployee(employeeService.getDefaultEmployee());
		return orders;
	}

	public Order getOrderById(Long id) {
		return orderDAO.getObjectById(id);
	}

	public Order createOrderForPerson(Person person, Appointment appointment) {
		Order order =  new Order();
		order = orderDAO.getOrderForAppointment(appointment);
		if (order!=null){
			return order;
		}
		order =  new Order();
		order.setPerson(person);
		order.setCurrency("USD");
		order.setNumOfItems(0);
		order.setSubTotal(new BigDecimal("0.00"));
		order.setCreateDate(new DateTime());
		order.setUpdateDate(new DateTime());
		order.setTax(new BigDecimal("0.00"));
		order.setShipper(shipperService.getDefaultShipper());
//		order.setPaymentMethod(null);//getPaymentMethods().get(0));
		if (appointment == null){
			order.setAppointment(appointmentService.createAppointmentForPerson(person));
		}else{
			Appointment appt = new Appointment();
			appt.setId(appointment.getId());
			order.setAppointment(appt);
		}
		order.setStatus(OrderStatusType.OPEN);
		orderDAO.saveObject(order);
		return order;
	}

	public Order startOrder(Order order) {
		order.setStatus(OrderStatusType.PENDING);
		orderDAO.updateObject(order);
		return order;
	}
	
	public Order saveOrder(Order order) {
		//order.setStatus(OrderStatusType.PENDING);
		orderDAO.updateObject(order);
		return order;
	}
	public Order payForOrder(Order order) {
		order.setStatus(OrderStatusType.PAID);
		//paymentService.takePaymentForOrder(payment, order);
		orderDAO.updateObject(order);
		return order;
	}
	public Order closeOrder(Order order) {
		order = getOrderById(order.getId());
		appointmentService.closeAppointment(order.getAppointment());
		order.setStatus(OrderStatusType.CLOSED);
		orderDAO.updateObject(order);
		return order;
	}

	public List<Order> getOrdersForPerson(Person person) {

		return orderDAO.getOrdersForPerson(person);
	}

	public List<Order> getOrdersForEmployee(Employee emp) {
		return orderDAO.getOrdersForEmployee(employeeService.getDefaultEmployee());
	}
	public Order updateOrderItem(Order order , OrderItem orderItem){
		for (OrderItem orderItem1:order.getOrderItems()){
			if (orderItem1.equals(orderItem)){
				removeOrderItemFromOrder(order,orderItem1 );
				//orderItem1.setQuantity(orderItem.getQuantity());
				//orderItem1.setNotes(orderItem.getNotes());
				//orderItem1.setUpdateDate(new DateTime());
			}
		}
		order.getOrderItems().add(orderItem);
		order.setNumOfItems(order.getNumOfItems()+orderItem.getQuantity().intValue());
		order.setSubTotal(updateSubTotal(order.getSubTotal(), orderItem.getItem().getPrice(), orderItem.getQuantity()));
		
		order.setTax(getTaxAmount(order.getSubTotal()));

		order.setTotal(order.getSubTotal().add(order.getTax()).setScale(2, BigDecimal.ROUND_DOWN));
		order.setUpdateDate(new DateTime());
		orderDAO.updateObject(order);
		return order;
	}
	public Order removeOrderItemFromOrder(Order order, OrderItem orderItem) {
		BigDecimal previousQuantity=new BigDecimal("1.00");
		for (OrderItem orderItem1:order.getOrderItems()){
			if (orderItem1.equals(orderItem)
					&& orderItem1.getQuantity().intValue()>0){
				previousQuantity =orderItem1.getQuantity();
			}
		}
		order.getOrderItems().remove(orderItem);
		
		order.setNumOfItems(order.getNumOfItems()-1);
		BigDecimal unitPrice =orderItem.getItem().getPrice().multiply(previousQuantity);
		order.setSubTotal(order.getSubTotal().subtract(unitPrice).setScale(2, BigDecimal.ROUND_DOWN));
		order.setTax(getTaxAmount(order.getSubTotal()));
		order.setTotal(order.getSubTotal().add(order.getTax()).setScale(2, BigDecimal.ROUND_DOWN));
		orderDAO.removeOrderItem(orderItem);
		orderDAO.updateObject(order);
		return order;
	}

	public Order addItemToOrder(OrderItem orderItem, Order order ,Item item) {
		
		if (order == null 
				|| order.getStatus().equals(OrderStatusType.CLOSED)){
			throw new IllegalArgumentException("cannot update null or closed orders");
		}
		item =verifyItem( item);
		if (orderItem == null){
			orderItem = createEmptyOrderItem();
			orderItem.setOrder(order);
			orderItem.setItem(item);
			orderItem.setQuantity(new BigDecimal("1"));
		}
		if (orderHasMatchingOrderItem(order,orderItem)){
			order.getOrderItems().remove(orderItem);
		}
		if (orderItem.getCreateDate()==null)
			orderItem.setCreateDate(new DateTime());
		
		orderItem.setUpdateDate(new DateTime());
		
		orderDAO.saveOrderItem(orderItem);
		order.getOrderItems().add(orderItem);
//		if (orderHasMatchingOrderItem(order,orderItem)
//				&& orderItem.getQuantity().compareTo(new BigDecimal("0"))==1){				
//			 return updateOrderItem(order,orderItem);
//		}
//
//		if (orderHasMatchingOrderItem(order,orderItem)
//				&& orderItem.getQuantity().compareTo(new BigDecimal("0"))<=0){
//			return removeOrderItemFromOrder( order ,  orderItem);		
//		}

		order.setNumOfItems(getQuantityOfAllOrderItems(order));//.getNumOfItems()+orderItem.getQuantity().intValue());
		order.setSubTotal(updateSubTotal(order.getOrderItems()));//order.getSubTotal(), item.getPrice(), orderItem.getQuantity()));
		order.setTax(getTaxAmount(order.getSubTotal()));
		order.setTotal(order.getSubTotal().add(order.getTax()).setScale(2, BigDecimal.ROUND_DOWN));

		orderDAO.updateObject(order);
		
		return order;
	}
	private int getQuantityOfAllOrderItems(Order order) {
		ServiceUtils.assertNotNull("order cannot be null", order);
		if (CollectionUtils.isEmpty(order.getOrderItems())){
			return 0;
		}
		int numCount =0;
		for (OrderItem oi :order.getOrderItems()){
			numCount = numCount+oi.getQuantity().intValue();
		}
		return numCount;
	}

	private boolean orderHasMatchingOrderItem(Order order , OrderItem orderItem){
		if (CollectionUtils.isNotEmpty(order.getOrderItems())){			
			for (OrderItem anOrderItem : order.getOrderItems()){
				if (anOrderItem.getItem().equals(orderItem.getItem())
						&& anOrderItem.getOrder().equals(orderItem.getOrder())){
					return true;
				}
			}
		}
		return false;
	}
	private Item verifyItem(Item item){
		if (item == null 
				|| item.getDeletedFlag().equals("Y")){
			throw new IllegalArgumentException("cannot update or add null - deleted items");
		}
		if (item.getId()==null
				&& item.getSku()!=null){
			item = itemService.getItemBySku(item.getSku());
			if (item ==null)
			itemService.saveItem(item);
		}
		return item;
	}
	private OrderItem createEmptyOrderItem(){
		OrderItem orderItem = new OrderItem();
			orderItem.setNotes("");
			orderItem.setQuantity(new BigDecimal ("1.00"));
			orderItem.setCreateDate(new DateTime());
			orderItem.setUpdateDate(new DateTime());
		return orderItem;
	}
	
	private BigDecimal updateSubTotal(
			BigDecimal current, 
			BigDecimal itemPrice, 
			BigDecimal itemQuantity){
		BigDecimal updated = current;
		updated = current.add(itemPrice.multiply(itemQuantity)).setScale(2, BigDecimal.ROUND_DOWN);
		return updated;
	}
	private BigDecimal updateSubTotal(Set<OrderItem> orderItems
			){
		BigDecimal updated = new BigDecimal("0.00");
		if (CollectionUtils.isEmpty(orderItems)){
			return updated;
		}
		for (OrderItem oi : orderItems){
			updated = updated.add(oi.getItem().getPrice().multiply(oi.getQuantity())).setScale(2, BigDecimal.ROUND_DOWN);
		}
		return updated;
	}
	private BigDecimal getTaxAmount(BigDecimal taxable){
		return taxable.multiply(taxRate).setScale(2, BigDecimal.ROUND_DOWN);
	}
	
	public List<PaymentMethod> getPaymentMethods(){
		return orderDAO.getPaymentMethods();
	}
	@Required
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	@Required
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	@Required
	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	@Required
	public void setShipperService(ShippingMethodService shipperService) {
		this.shipperService = shipperService;
	}
	@Required
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	@Required
	public void setAppointmentService(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}

	public List<MonthlyTotals> getMonthlyTotals(List<Order> orderList) {
		// TODO Auto-generated method stub
		return null;
	}

}
