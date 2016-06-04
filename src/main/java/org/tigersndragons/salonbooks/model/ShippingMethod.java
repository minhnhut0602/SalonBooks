package org.tigersndragons.salonbooks.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.tigersndragons.salonbooks.model.type.ShipperType;

@Entity
@Table(schema="SALONBOOKS",name="SHIPPING_METHOD")
@AttributeOverride(name="id", column=@Column(name="METHOD_ID"))
public class ShippingMethod extends SalonObject {
	
	private static final long serialVersionUID = 1L;
	private ShipperType name;//ShipperType.WALKIN;

	@Column(name="METHOD_NAME")
	@Enumerated(EnumType.STRING)
	public ShipperType getName() {
		return name;
	}

	public void setName(ShipperType name) {
		this.name = name;
	}
	
//	@Override
//	public Long getId(){
//		return id;
//	}
}
