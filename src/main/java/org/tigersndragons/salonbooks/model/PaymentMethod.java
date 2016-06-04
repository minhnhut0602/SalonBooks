package org.tigersndragons.salonbooks.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema="SALONBOOKS",name="PAYMENT_METHOD")
@AttributeOverride(name="id", column=@Column(name="PAYMENT_METHOD_ID"))
public class PaymentMethod extends SalonObject {

	private static final long serialVersionUID = 1L;
	private String name;
	
//	@Override
//	public Long getId(){
//		return id;
//	}
	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
