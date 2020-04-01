package com.parksexpress.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Salesman implements Serializable {
	private static final long serialVersionUID = -3272389115637069126L;
	private Long salesmanID;
	private String name;
	
	public Salesman(){
	}

	public Long getSalesmanID() {
		return this.salesmanID;
	}

	public void setSalesmanID(final Long salesmanID) {
		this.salesmanID = salesmanID;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Chain) {
			return ((Chain) obj).getName().equalsIgnoreCase(this.name);
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int first = 11;
		final int last = 31;
		return new HashCodeBuilder(first, last).append(this.name).toHashCode();
	}
}
