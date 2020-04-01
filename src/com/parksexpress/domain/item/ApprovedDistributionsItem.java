package com.parksexpress.domain.item;

import java.math.BigDecimal;
import java.text.ParseException;

import com.parksexpress.as400.util.DateChanger;

public class ApprovedDistributionsItem extends Item {
	private static final long serialVersionUID = 1091803726110217554L;
	private String shipDate;
	private String inventoryLevel;
	private int quantity;
	private BigDecimal price;
	private BigDecimal retail;
	private String storeName;
	
	public ApprovedDistributionsItem(){}

	public String getShipDate() {
		try {
			return DateChanger.convertDateFromAS400(this.shipDate);
		} catch (ParseException e) {
			return this.shipDate;
		}
	}

	public void setShipDate(final String shipDate) {
		this.shipDate = shipDate;
	}

	public String getInventoryLevel() {
		return this.inventoryLevel;
	}

	public void setInventoryLevel(final String inventoryLevel) {
		this.inventoryLevel = inventoryLevel;
	}
	
	public int getQuantity(){
		return this.quantity;
	}
	
	public void setQuantity(final int quantity){
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getRetail() {
		return this.retail;
	}

	public void setRetail(final BigDecimal retail) {
		this.retail = retail;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
}