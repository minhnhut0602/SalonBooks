package org.tigersndragons.salonbooks.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(schema="SALONBOOKS",name="ORDER_ITEMS")
@AssociationOverrides({
	@AssociationOverride(name="pk.item", joinColumns=@JoinColumn(name="ITEM_ID")),
	@AssociationOverride(name="pk.order", joinColumns=@JoinColumn(name="ORDER_ID"))
})
//@AttributeOverride(name="id", column=@Column(name="ORDER_ID"))
public class OrderItem implements Serializable  {

	private static final long serialVersionUID = 1L;
	private OrderItemId pk = new OrderItemId();
	
	@EmbeddedId
	public OrderItemId getPk(){
		return pk;
	}
	
	protected DateTime createDate;
	protected DateTime updateDate;
	private String notes;
	private BigDecimal quantity;
	
	@Transient
	public Order getOrder() {
		return getPk().getOrder();
	}
	public void setOrder(Order order) {
		getPk().setOrder(order);
	}
	@Transient
	public Item getItem() {
		return getPk().getItem();
	}
	public void setItem(Item item) {
		getPk().setItem(item);
	}
	public void setPk( OrderItemId pk){
		this.pk = pk;
	}
	@Column(name="NOTES")
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	@Column(name="QUANTITY")
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj== null )
			return false;
		if (!(obj instanceof OrderItem) )
			return false;
		OrderItem other=  (OrderItem)obj ;
		if (pk == null){
			if (other.pk != null)
				return false;
		}else if (!pk.equals(other.pk))
			return false;
		//if () other
		return true;
	}
	
	@Override 
	public int hashCode(){
		return (getPk() !=null ? getPk().hashCode():0);
	}

	@Column(name="CREATE_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(DateTime dateTime) {
		this.createDate = dateTime;
	}

	@Column(name="UPDATE_DATE")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	public DateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(DateTime updateDate) {
		this.updateDate = updateDate;
	}
}
