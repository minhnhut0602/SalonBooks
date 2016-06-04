package org.tigersndragons.salonbooks.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.tigersndragons.salonbooks.model.Item;

@Component
public interface ItemDAO extends SalonEntityDAO<Item> {

	public List<Item> getListOfItems();
	
	public Item getItemBySku(String sku);
}
