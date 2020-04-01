package com.parksexpress.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Vendor {
	private String name;
	private String number;
	
	public Vendor(){}
	
	public Vendor(String name, String number){
		this.name = name;
		this.number = number;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Vendor){
			final Vendor vendor = (Vendor)obj;
			return new EqualsBuilder().append(this.name, vendor.getName()).append(this.number, vendor.getNumber()).isEquals();
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		final int first = 11;
		final int second = 31;
		
		return new HashCodeBuilder(first, second).append(this.name).append(this.number).toHashCode();
	}
	
	@Override
	public String toString() {
		return new StringBuffer().append("Name: " + this.name + " ").append("Number: " + this.number + " ").toString();
	}
}
