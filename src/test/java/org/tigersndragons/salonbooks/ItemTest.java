package org.tigersndragons.salonbooks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tigersndragons.salonbooks.exception.PersonNotFoundException;
import org.tigersndragons.salonbooks.exception.ValidationException;
import org.tigersndragons.salonbooks.model.Appointment;
import org.tigersndragons.salonbooks.model.Item;
import org.tigersndragons.salonbooks.model.Person;
import org.tigersndragons.salonbooks.model.flows.PersonFormModel;
import org.tigersndragons.salonbooks.model.type.AppointmentStatusType;
import org.tigersndragons.salonbooks.model.type.GenderType;
import org.tigersndragons.salonbooks.service.AppointmentService;
import org.tigersndragons.salonbooks.service.EmployeeService;
import org.tigersndragons.salonbooks.service.ItemService;
import org.tigersndragons.salonbooks.service.PersonService;

public class ItemTest extends BaseTestCase {

	@Autowired 
	ItemService itemService;

	
	private Item e1, e2;
	@Before
	public void setUp() throws Exception {
		e1= new Item();
		e2= new Item();
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
	public void testMatchingNotes(){
		e1.setId(0L);
		e1.setDescription("auser");
		e2.setId(0L);
		e2.setDescription("auser");
		assertTrue(e1.equals(e2));
		assertTrue(e1.getDescription().equals(e2.getDescription()));
		e2.setId(1L);
		assertFalse(e1.equals(e2));		
		assertTrue(e1.getDescription().equals(e2.getDescription()));
	}
	
	private Item getDefaultItem(){
		Item item = new Item();
		item.setId(0L);
		item.setIsService(1);
		item.setSku("TEST01");
		item.setLabel("IS FOR TEST");
		item.setDeletedFlag("N");
		item.setPrice(new BigDecimal("0.01"));
		item.setUpdateDate(new DateTime());
		item.setCreateDate(new DateTime());
		return item;
	}
	@Test 
	public void retrieveDefaultItem(){
		Item emp = itemService.createItem();

		assertTrue(StringUtils.equals(emp.getSku(), getDefaultItem().getSku()));
		
	}
	
	@Test 
	public void retrieveListOfItem(){
		List<Item > apptList = itemService.getListOfItems();
		assertTrue(CollectionUtils.isNotEmpty(apptList)
				&& apptList.size()>0);
		assertTrue(apptList.get(0).equals(this.getDefaultItem()));
	}
	
	@Test 
	public void retrieveItemById(){
		Item emp = itemService.getItemById(0L);
		Item e2 = getDefaultItem();
		assertTrue(emp.equals(e2 ));
		assertTrue(StringUtils.equals(emp.getSku(), e2.getSku()));
	}
	
	@Test
	public void testgetItemBySku() {
		Item anItem =itemService.getItemBySku("TEST01");
		assertTrue(anItem!=null);	
		assertTrue(getDefaultItem().equals(anItem));
	}
		
	@Test(expected=ConstraintViolationException.class)
	public void testSaveDefaultItemErr() {
		itemService.saveItem(getDefaultItem());
	}
	
	@Test
	public void testSaveDefaultItem() {
		Item io=getDefaultItem();
		io.setId(101L);
		io.setSku("TEST09");
		itemService.saveItem(io);
	}

}
