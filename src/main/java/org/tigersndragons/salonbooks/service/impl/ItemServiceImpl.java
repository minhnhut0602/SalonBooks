package org.tigersndragons.salonbooks.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.tigersndragons.salonbooks.ServiceUtils;
import org.tigersndragons.salonbooks.dao.ItemDAO;
import org.tigersndragons.salonbooks.model.Item;
import org.tigersndragons.salonbooks.service.ItemService;

@Transactional
public class ItemServiceImpl extends BaseServiceImpl implements ItemService {


	private static final long serialVersionUID = 1L;
	@Autowired
	private ItemDAO itemDAO;
	public List<Item> getListOfItems() {
		return itemDAO.getListOfItems();
	}

	public Item getItemById(Long id) {
		ServiceUtils.assertNotNull("id cannot be null", id);
		return itemDAO.getObjectById(id);
	}

	public Item createItem() {
		return getDefaultItem();
	}

	public void saveItem(Item item) {
		ServiceUtils.assertNotNull("item cannot be null", item);
		itemDAO.saveObject(item);
	}

	public Item getItemBySku(String sku) {
		ServiceUtils.assertNotNull("sku cannot be null", sku);
		return itemDAO.getItemBySku(sku);
	}
	
	private Item getDefaultItem(){
		Item item = new Item();
		//item.setId(0L);
		item.setIsService(1);
		item.setSku("TEST01");
		item.setLabel("IS FOR TEST");
		item.setDeletedFlag("N");
		item.setPrice(new BigDecimal("0.01"));
		item.setUpdateDate(new DateTime());
		item.setCreateDate(new DateTime());
		return item;
	}
	@Required
	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}
}
