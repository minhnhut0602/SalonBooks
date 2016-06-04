package org.tigersndragons.salonbooks.dao;

import org.tigersndragons.salonbooks.model.SalonObject;


public interface SalonEntityDAO  <T extends SalonObject>  {

	public T getObjectById(Long id);
	
	public void saveObject(T obj);
	public void updateObject(T obj);
}
