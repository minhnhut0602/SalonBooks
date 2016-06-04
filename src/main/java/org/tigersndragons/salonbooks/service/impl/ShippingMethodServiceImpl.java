package org.tigersndragons.salonbooks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.tigersndragons.salonbooks.dao.ShipperDAO;
import org.tigersndragons.salonbooks.model.ShippingMethod;
import org.tigersndragons.salonbooks.model.type.ShipperType;
import org.tigersndragons.salonbooks.service.ShippingMethodService;

public class ShippingMethodServiceImpl extends BaseServiceImpl  implements ShippingMethodService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ShipperDAO shipperDAO;
	@Required
	public void setShipperDAO(ShipperDAO shipperDAO) {
		this.shipperDAO = shipperDAO;
	}

	public List<ShippingMethod> getListOfActiveShippers() {
		return shipperDAO.getActiveShipperList();
	}

	public ShippingMethod getShipperById(Long id) {
		return shipperDAO.getObjectById(id);
	}

	public ShippingMethod createShipper() {
		return new ShippingMethod();
	}

	public ShippingMethod getDefaultShipper() {
		ShippingMethod shipper = new ShippingMethod();
		shipper.setId(0L);
		shipper.setName(ShipperType.WALKIN);
		return null;
	}

}
