package com.parksexpress.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Chain implements Serializable {
	private static final long serialVersionUID = 1433284634698994669L;
	private Long chainID;
	private String name;
	private String number;
	private String bookNumber;
	private List<Store> stores = new ArrayList<Store>();
	private OrderGuide orderGuide;
	
	public Chain(){
	}

	public List<Store> getStores() {
		return this.stores;
	}

	public void setStores(final List<Store> stores) {
		this.stores = stores;
	}

	public OrderGuide getOrderGuide() {
		return this.orderGuide;
	}

	public void setOrderGuide(final OrderGuide orderGuide) {
		this.orderGuide = orderGuide;
	}

	public Long getChainID() {
		return this.chainID;
	}

	public void setChainID(final Long chainID) {
		this.chainID = chainID;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public String getBookNumber() {
		return this.bookNumber;
	}

	public void setBookNumber(final String bookNumber) {
		this.bookNumber = bookNumber;
	}

	public String getWebSafeName(){
		return StringUtils.remove(this.name, "'");
	}
	
	@Override
	public String toString() {
		return this.number + " - " + this.name;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Chain) {
			return ((Chain) obj).getName().equalsIgnoreCase(this.name)
					&& ((Chain) obj).getNumber().equalsIgnoreCase(this.number);
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int first = 11;
		final int last = 31;
		return new HashCodeBuilder(first, last).append(this.number).append(this.name)
				.toHashCode();
	}
}
