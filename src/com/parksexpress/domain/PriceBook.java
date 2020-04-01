package com.parksexpress.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class PriceBook implements Serializable{
	public static final int CHAIN_FAMILY_PRIORITY = 100;
	public static final int CHAIN_ITEM_PRIORITY = 90;
	public static final int ZONE_FAMILY_PRIORITY = 50;
	public static final int ZONE_ITEM_PRIORITY = 40;
	public static final int STORE_FAMILY_PRIORITY = 30;
	public static final int STORE_ITEM_PRIORITY = 20;
	public static final int CIG_ITEM_PRIORITY = 10;
	
	public static final String CHAIN_FAMILY_NAME = "100";
	public static final String CHAIN_ITEM_NAME = "90";
	public static final String ZONE_FAMILY_NAME = "50";
	public static final String ZONE_ITEM_NAME = "40";
	public static final String STORE_FAMILY_NAME = "30";
	public static final String STORE_ITEM_NAME = "20";
		
	private static final long serialVersionUID = -5359109977824340150L;
	private String description;
	private String number;
	private int priority;
	private List<String> stores = new ArrayList<String>();
	private PriceBook parent;
	private String customerNumber;
	
	public PriceBook(){}
	
	public PriceBook(String number, String description, int priority, String customerNumber){
		this.description = description;
		this.number = number;
		this.priority = priority;
		this.customerNumber = customerNumber;
	}
	
	public String getCustomerNumber() {
		return this.customerNumber;
	}

	public void setCustomerNumber(final String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public PriceBook getParent() {
		return this.parent;
	}

	public void setParent(final PriceBook parent) {
		this.parent = parent;
	}

	public List<String> getStores() {
		return this.stores;
	}

	public void setStores(final List<String> stores) {
		this.stores = stores;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(final int priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return this.number + " - " + this.description;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PriceBook) {
			return ((PriceBook) obj).getNumber().equalsIgnoreCase(this.number);
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int first = 11;
		final int last = 31;
		return new HashCodeBuilder(first, last).append(this.number).toHashCode();
	}
}
