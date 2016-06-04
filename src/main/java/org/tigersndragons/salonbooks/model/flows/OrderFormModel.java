package org.tigersndragons.salonbooks.model.flows;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tigersndragons.salonbooks.ServiceUtils;
import org.tigersndragons.salonbooks.exception.PersonNotFoundException;
import org.tigersndragons.salonbooks.exception.ValidationException;
import org.tigersndragons.salonbooks.model.Appointment;
import org.tigersndragons.salonbooks.model.Item;
import org.tigersndragons.salonbooks.model.Order;
import org.tigersndragons.salonbooks.model.OrderItem;
import org.tigersndragons.salonbooks.model.PaymentMethod;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.ShippingMethod;
import org.tigersndragons.salonbooks.model.type.OrderStatusType;
import org.tigersndragons.salonbooks.service.OrderService;

@Component
public class OrderFormModel extends SalonFlows {
	private static final long serialVersionUID = 1L;
	private Long orderId;
	private Order order;
	private Person person;
	private Long personId;
	private BigDecimal shipping=new BigDecimal(0.00);

	private BigDecimal subTotal=new BigDecimal(0.00);
	private BigDecimal tax=new BigDecimal(0.00);
	private BigDecimal total=new BigDecimal(0.00);
	private OrderItem[] orderItems;
	private OrderItem anOrderItem;
	private Item itemSelect;
	private BigDecimal quantity;
	private Long appointmentId;
	private List<Item> itemList;
	private String saveAndClose;
	private Long paymentMethodId;
	private Long shipperId;

	private DateTime createDate;

	private int numOfItems;

	public int getNumOfItems() {
		return numOfItems;
	}

	public void setNumOfItems(int numOfItems) {
		this.numOfItems = numOfItems;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}



//	public Order startOrder(Long orderId) throws PersonNotFoundException, ValidationException{
//		ServiceUtils.assertNotNull("orderId cannot be null", orderId);
//		return orderService.startOrder(orderService.getOrderById(orderId));
//	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
		this.personId = person.getId();
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
		convertOrdertoModel();
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	
	public BigDecimal getShipping() {
		return shipping;
	}

	public void setShipping(BigDecimal shipping) {
		this.shipping = shipping;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public OrderItem[] getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(OrderItem[] orderItems) {
		this.orderItems = orderItems;
	}

	public OrderItem getAnOrderItem() {
		return anOrderItem;
	}

	public void setAnOrderItem(OrderItem anOrderItem) {
		this.anOrderItem = anOrderItem;
	}

	public Item getItemSelect() {
		return itemSelect;
	}

	public void setItemSelect(Item itemSelect) {
		this.itemSelect = itemSelect;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getSaveAndClose() {
		return saveAndClose;
	}

	public void setSaveAndClose(String saveAndClose) {
		this.saveAndClose = saveAndClose;
	}

	public Order convertModelToOrder() {
		Order order = new Order();
		order.setId(orderId);
		Appointment appointment = new Appointment();
		appointment.setId(appointmentId);
		order.setAppointment(appointment);
		Person person1 = new Person();
		person1.setId(personId);
		order.setPerson(person1);
		order.setStatus(OrderStatusType.valueOf(saveAndClose));
		order.setSubTotal(subTotal);
		order.setTax(tax);
		order.setTotal(total);
		order.setNumOfItems(numOfItems);
		order.setUpdateDate(new DateTime());
		order.setShippingCost(shipping);
		order.setCreateDate(createDate);
		PaymentMethod payment = new PaymentMethod();
		payment.setId(paymentMethodId);
		order.setPaymentMethod(payment);
		ShippingMethod shipping = new ShippingMethod();
		shipping.setId(shipperId);
		order.setShipper(shipping);
		return order;
		
	}
	
	public void convertOrdertoModel() {
		Order order = this.order;
		setOrderId(order.getId());
		setAppointmentId(order.getAppointment().getId());
		setCreateDate(order.getCreateDate());
		setPersonId(order.getPerson().getId());
		//setStatus(OrderStatusType.valueOf(saveAndClose));
		setSubTotal(order.getSubTotal());
		setTax(order.getTax());
		setTotal(order.getTotal());
		setNumOfItems(order.getNumOfItems());
		setShipping(order.getShippingCost());
		setPaymentMethodId(order.getPaymentMethod().getId());
		setShipperId(order.getShipper().getId());
		
	}

	public Long getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(Long paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public Long getShipperId() {
		return shipperId;
	}

	public void setShipperId(Long shipperId) {
		this.shipperId = shipperId;
	}

	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime createDate) {
		this.createDate = createDate;
	}
	
}
