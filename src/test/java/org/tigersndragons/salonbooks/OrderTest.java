package org.tigersndragons.salonbooks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tigersndragons.salonbooks.exception.PersonNotFoundException;
import org.tigersndragons.salonbooks.exception.ValidationException;
import org.tigersndragons.salonbooks.model.Item;
import org.tigersndragons.salonbooks.model.Order;
import org.tigersndragons.salonbooks.model.OrderItem;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.flows.PersonFormModel;
import org.tigersndragons.salonbooks.model.type.GenderType;
import org.tigersndragons.salonbooks.model.type.OrderStatusType;
import org.tigersndragons.salonbooks.service.AppointmentService;
import org.tigersndragons.salonbooks.service.ItemService;
import org.tigersndragons.salonbooks.service.OrderService;
import org.tigersndragons.salonbooks.service.PersonService;
import org.tigersndragons.salonbooks.service.ShippingMethodService;

public class OrderTest extends BaseTestCase {

	@Autowired 
	OrderService orderService;
	@Autowired 
	PersonService personService;
	@Autowired 
	ItemService itemService;
	@Autowired
	ShippingMethodService shippingMethodService;
	@Autowired
	AppointmentService appointmentService;
	
	private Order e1, e2;
	@Before
	public void setUp() throws Exception {
		e1= new Order();
		e2= new Order();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMatchId() {
		e1.setId(0L);
		e2.setId(0L);
		assertTrue(e1.equals(e2));
		e2.setId(1L);
		assertFalse(e1.equals(e2));
	}
	
	@Test
	public void testMatchingStatus(){
		e1.setId(0L);
		e1.setStatus(OrderStatusType.OPEN);//("auser");
		e2.setId(0L);
		e2.setStatus(OrderStatusType.OPEN);//
		assertTrue(e1.equals(e2));
		assertTrue(e1.getStatus().equals(e2.getStatus()));
		e2.setId(1L);
		assertFalse(e1.equals(e2));		
		assertTrue(e1.getStatus().equals(e2.getStatus()));
	}
	
	private Order testEmptyOrder(){
		Order emp = new Order();
		emp.setId(0L);
		emp.setStatus(OrderStatusType.OPEN);
		emp.setPerson(personService.getDefaultPerson());
		emp.setNumOfItems(0);
		emp.setShipper(shippingMethodService.getDefaultShipper());
		emp.setCurrency("USD");
		emp.setSubTotal(new BigDecimal("0.00"));
		emp.setTax(new BigDecimal("0.00"));
		emp.setTotal(new BigDecimal("0.00"));
		emp.setCreateDate(new DateTime());
		emp.setUpdateDate(new DateTime());
		emp.setShippingCost(new BigDecimal("0.00"));
		//emp.setOrderItems(orderItems);
		return emp;
	}
	@Test
	public void getOrderWithItem(){
		Order order = testOrderwithItem();
		assertTrue(order!=null);
		Item item = itemService.createItem();
		item.setId(0L);
		OrderItem orderItem = new OrderItem ();
		orderItem.setOrder(order);
		orderItem.setItem(item);
		orderItem.setQuantity(new BigDecimal("2"));
		orderItem.setNotes("notes2");
		
		order = orderService.addItemToOrder(orderItem, order, item);
		assertTrue(order!=null);
		assertTrue(order.getNumOfItems()==2);
		assertTrue( order.getSubTotal().equals(new BigDecimal("0.02"))	);
		assertTrue(order.getOrderItems().size()==1)	;
	}
	
	public  Order testOrderwithItem(){
		Order emp = new Order();
		emp.setId(1L);
		emp.setStatus(OrderStatusType.OPEN);
		emp.setPerson(personService.getDefaultPerson());
		emp.setNumOfItems(0);
		emp.setShipper(shippingMethodService.getDefaultShipper());
		emp.setCurrency("USD");
		emp.setSubTotal(new BigDecimal("0.00"));
		emp.setTax(new BigDecimal("0.00"));
		emp.setTotal(new BigDecimal("0.00"));
		emp.setCreateDate(new DateTime());
		emp.setUpdateDate(new DateTime());
		Item item = itemService.createItem();
		item.setId(0L);
		OrderItem orderItem = new OrderItem ();
		orderItem.setOrder(emp);
		orderItem.setItem(item);
		orderItem.setQuantity(new BigDecimal(1));
		orderItem.setNotes("notes");
		
		emp = orderService.addItemToOrder(orderItem, emp, item);
		return emp;
	}

	
	@Test 
	public void retrieveListOfOrders(){
		List<Order > orderList = orderService.getListOfActiveOrders();
		assertTrue(CollectionUtils.isNotEmpty(orderList)
				&& orderList.size()>0);
		assertTrue(orderList.get(orderList.size()-1).equals(this.testEmptyOrder()));
	}
	
	@Test 
	public void retrieveOrderById(){
		Order emp = orderService.getOrderById(0L);
		Order e2 = testEmptyOrder();
		assertTrue(emp.equals(e2 ));
//		assertTrue(StringUtils.equals(emp.getFirstName(), e2.getFirstName()));
//		assertTrue(StringUtils.equals(emp.getPrimaryPhoneNumber(), e2.getPrimaryPhoneNumber()));
		//assertTrue(StringUtils.equals(emp.getPassword(), e2.getPassword()));
	}
	
	@Test
	public void testStartEmptyOrder() {
		Order emp = orderService.createOrderForPerson(
				personService.getDefaultPerson(), 
				appointmentService.createAppointmentForPerson(personService.getDefaultPerson()));
		assertTrue(emp!= null 
				&& emp.getId()!=null);
		emp =orderService.startOrder(testEmptyOrder());
		assertTrue(emp!= null 
				&& emp.getId()!=null
				&& emp.getStatus().equals(OrderStatusType.PENDING));
	}
	
	@Test
	public void testCloseEmptyOrder() {
		Order emp = orderService.createOrderForPerson(
				personService.getDefaultPerson(), 
				appointmentService.createAppointmentForPerson(personService.getDefaultPerson()));
		assertTrue(emp!= null 
				&& emp.getId()!=null);
		emp=orderService.closeOrder(testEmptyOrder());
		assertTrue(emp!= null );
		assertTrue( emp.getId()!=null);
		assertTrue( emp.getStatus().equals(OrderStatusType.CLOSED));
	}
	

}
