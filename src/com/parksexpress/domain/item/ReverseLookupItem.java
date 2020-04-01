package com.parksexpress.domain.item;

import java.math.BigDecimal;

public class ReverseLookupItem extends Item implements Comparable<ReverseLookupItem>{
	private static final long serialVersionUID = -2840480286909234472L;

	private String vendorID;
	private String vendorName;
	private long quantity;
	private String customerNumber;
	private String customerName;
	private BigDecimal costAmount;
	private BigDecimal srpAmount;
	private float profitPercent;
	private String brand;
	
	public ReverseLookupItem(){}

	public String getVendorID() {
		return this.vendorID;
	}

	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}

	public String getVendorName() {
		return this.vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getCustomerNumber() {
		return this.customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getCostAmount() {
		return this.costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	public BigDecimal getSrpAmount() {
		return this.srpAmount;
	}

	public void setSrpAmount(BigDecimal srpAmount) {
		this.srpAmount = srpAmount;
	}

	public float getProfitPercent() {
		return this.profitPercent;
	}

	public void setProfitPercent(float profitPercent) {
		this.profitPercent = profitPercent;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public int compareTo(ReverseLookupItem o) {
		return this.getCustomerName().compareToIgnoreCase(o.getCustomerName());
	}
}
