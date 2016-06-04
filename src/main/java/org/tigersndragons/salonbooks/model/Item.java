package org.tigersndragons.salonbooks.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema="SALONBOOKS",name="ITEM")
@AttributeOverride(name="id", column=@Column(name="ITEM_ID"))
public class Item extends SalonObject {

	private static final long serialVersionUID = 1L;
	private String sku;
	private int isService;
	private String label;
	private String description;
	private BigDecimal price;
	private BigDecimal unitCost;

	private String deletedFlag ="N";
	private Set<OrderItem> orderItems = new HashSet<OrderItem>(0);
	
	@Column(name="SKU")
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	@Column(name="IS_SERVICE")
	public int getIsService() {
		return isService;
	}
	public void setIsService(int isService) {
		this.isService = isService;
	}
	@Column(name="LABEL")
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="PRICE")
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	@Column(name="DELETED_FLAG")
	public String getDeletedFlag() {
		return deletedFlag;
	}
	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}
	@Column(name="UNIT_COST")
	public BigDecimal getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}
	@OneToMany(fetch= FetchType.LAZY, mappedBy="pk.item",cascade=CascadeType.ALL)
	public Set <OrderItem> getOrderItems(){
		return orderItems;
	}
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	@Override
	public String toString() {
		return this.getId() + " | " + this.getLabel() + " | " + this.getSku() + " | " + this.getPrice();
	}
	
	
}
