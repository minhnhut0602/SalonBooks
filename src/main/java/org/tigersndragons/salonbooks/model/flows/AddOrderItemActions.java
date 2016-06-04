package org.tigersndragons.salonbooks.model.flows;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tigersndragons.salonbooks.ServiceUtils;
import org.tigersndragons.salonbooks.exception.PersonNotFoundException;
import org.tigersndragons.salonbooks.exception.ValidationException;
import org.tigersndragons.salonbooks.model.Item;
import org.tigersndragons.salonbooks.model.Order;
import org.tigersndragons.salonbooks.model.OrderItem;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.service.OrderService;

@Component
public class AddOrderItemActions extends SalonFlows {
	private static final long serialVersionUID = 1L;
	private Long orderId;
	private Order order;
	private Person person;
	private Long personId;

	private OrderItem anOrderItem;
	private Long itemSelect;
	@NotNull
	private BigDecimal quantity;
	private String addItemtoOrder;
	
	@Autowired
	OrderService orderService;

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}



	public Order startOrder(Long orderId) throws PersonNotFoundException, ValidationException{
		ServiceUtils.assertNotNull("orderId cannot be null", orderId);
		return orderService.startOrder(orderService.getOrderById(orderId));
	}

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
		this.orderId = order.getId();
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public OrderItem getAnOrderItem() {
		return anOrderItem;
	}

	public void setAnOrderItem(OrderItem anOrderItem) {
		this.anOrderItem = anOrderItem;
	}

	public Long getItemSelect() {
		return itemSelect;
	}

	public void setItemSelect(Long itemSelect) {
		this.itemSelect = itemSelect;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getAddItemtoOrder() {
		return addItemtoOrder;
	}

	public void setAddItemtoOrder(String addItemtoOrder) {
		this.addItemtoOrder = addItemtoOrder;
	}
	
}
