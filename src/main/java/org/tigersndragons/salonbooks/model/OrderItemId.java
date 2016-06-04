package org.tigersndragons.salonbooks.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderItemId implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Item item;
	private Order order;
	
	@ManyToOne
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	@ManyToOne
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	@Override
	public int hashCode() {
		int hashCode;
		hashCode = (order.getId() !=null ? order.getId().hashCode():0);
		hashCode = 31 * hashCode + (item.getId() != null ? item.getId().hashCode():0);
		return hashCode;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj== null 
				|| !(obj instanceof OrderItemId) )
			return false;
		
		OrderItemId other=  (OrderItemId)obj ;
		if (order !=null ? !order.getId().equals(other.order.getId()): other.order.getId()!=null ) return false;
		if (item !=null ? !item.getId().equals(other.item.getId()) : other.item.getId() !=null) return false;
		
//		if (order == null){
//			if (other.order != null)
//				return false;
//			
//		}else if (!order.equals(other.order))
//			return false;
//		
//		if (item == null){
//			if (other.item != null)
//				return false;
//		}else if (!item.equals(other.item))
//			return false;
		return true;
	}
	
	

}
