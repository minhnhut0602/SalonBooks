package org.tigersndragons.salonbooks.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.tigersndragons.salonbooks.model.ShippingMethod;
@Component
public interface ShipperDAO extends SalonEntityDAO<ShippingMethod> {
	public List<ShippingMethod> getActiveShipperList();
}
