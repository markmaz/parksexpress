package com.parksexpress.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.parksexpress.domain.item.Item;

public class OrderGuide implements Serializable {
	private static final long serialVersionUID = 385599527719893165L;
	private Long orderguideID;
	private String number;
	private List<Item> items = new ArrayList<Item>();

	public OrderGuide(){
	}

	public Long getOrderguideID() {
		return this.orderguideID;
	}

	public void setOrderguideID(final Long orderguideID) {
		this.orderguideID = orderguideID;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(final List<Item> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return this.number;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Chain) {
			return ((Chain) obj).getNumber().equalsIgnoreCase(this.number);
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
