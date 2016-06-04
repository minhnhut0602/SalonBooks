package org.tigersndragons.salonbooks.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.tigersndragons.salonbooks.model.type.OrderStatusType;
@Entity
@Table(schema="SALONBOOKS",name="ORDER")
@AttributeOverride(name="id", column=@Column(name="ORDER_ID"))
public class Order extends SalonObject {

	private static final long serialVersionUID = 1L;
/*
 * SELECT ORDER_ID, TOTAL, CREATE_DATE, UPDATE_DATE, NUM_OF_ITEMS, TAX, 
 * CURRENCY, SUBTOTAL, SHIPPING, PAYMENT_METHOD_ID, SHIP_VIA, 
 * STATUS, APPOINTMENT_ID, PERSON_ID
FROM `salonbooks`.`order`;
 */
	private BigDecimal total= new BigDecimal(0.0);
	private int numOfItems=0;
	private BigDecimal tax= new BigDecimal(0.0);
	private String currency="USD";
	private BigDecimal subTotal= new BigDecimal(0.0);
	private PaymentMethod paymentMethod;
	private ShippingMethod shipper;
	private OrderStatusType status;
	private Appointment appointment ;
	private BigDecimal shippingCost ;
	private Person person;
	private Set<OrderItem> orderItems = new HashSet<OrderItem>(0);
	

	@Column(name="TOTAL")
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	@Column(name="NUM_OF_ITEMS")
	public int getNumOfItems() {
		return numOfItems;
	}
	public void setNumOfItems(int numOfItems) {
		this.numOfItems = numOfItems;
	}
	@Column(name="TAX")
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	@Column(name="CURRENCY")
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	@Column(name="SUBTOTAL")
	public BigDecimal getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	@ManyToOne
	@JoinColumn(name="PAYMENT_METHOD_ID", insertable=false,updatable=false)
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	@ManyToOne
	@JoinColumn(name="SHIPPER_ID", insertable=false,updatable=false)
	public ShippingMethod getShipper() {
		return shipper;
	}
	public void setShipper(ShippingMethod shipVia) {
		this.shipper = shipVia;
	}
	@Column(name="STATUS")
	@Enumerated(EnumType.STRING)
	public OrderStatusType getStatus() {
		return status;
	}
	public void setStatus(OrderStatusType status) {
		this.status = status;
	}
	@ManyToOne
	@JoinColumn(name="APPOINTMENT_ID", updatable=false)
	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	@ManyToOne
	@JoinColumn(name="PERSON_ID", updatable=false)
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	@OneToMany(fetch= FetchType.LAZY, mappedBy="pk.order")//( 
	public Set<OrderItem> getOrderItems(){
		return orderItems;
	}
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	@Column(name="SHIPPING")
	public BigDecimal getShippingCost() {
		return shippingCost;
	}
	public void setShippingCost(BigDecimal shippingCost) {
		this.shippingCost = shippingCost;
	}
	@Override
	public String toString() {
		return this.id + "," + this.total.toString() + ", " + this.getPerson();
	}
	
	
}
