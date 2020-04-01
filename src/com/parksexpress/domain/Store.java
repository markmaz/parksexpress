package com.parksexpress.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Store implements Serializable {
	private static final long serialVersionUID = 420819597199550755L;
	private Long storeID;
	private String name;
	private String number;
	private String terms;
	private String lastOrder;
	private String chainCode;
	private String address;
	private String city;
	private String state;
	private String zip;
	private BigDecimal amountDue;
	private Chain chain;
	private Salesman salesman;
	
	public Store(){
	}

	public Salesman getSalesman() {
		return this.salesman;
	}

	public void setSalesman(final Salesman salesman) {
		this.salesman = salesman;
	}

	public Long getStoreID() {
		return this.storeID;
	}

	public void setStoreID(final Long storeID) {
		this.storeID = storeID;
	}

	public String getName() {
		return this.name;
	}

	public String getWebSafeName(){
		return StringUtils.remove(this.name, "'");
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

	public String getTerms() {
		return this.terms;
	}

	public void setTerms(final String terms) {
		this.terms = terms;
	}

	public String getLastOrder() {
		return this.lastOrder;
	}

	public void setLastOrder(final String lastOrder) {
		this.lastOrder = lastOrder;
	}

	public String getChainCode() {
		return this.chainCode;
	}

	public void setChainCode(final String chainCode) {
		this.chainCode = chainCode;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getState() {
		return this.state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(final String zip) {
		this.zip = zip;
	}

	public BigDecimal getAmountDue() {
		return this.amountDue;
	}

	public void setAmountDue(final BigDecimal amountDue) {
		this.amountDue = amountDue;
	}

	public Chain getChain() {
		return this.chain;
	}

	public void setChain(final Chain chain) {
		this.chain = chain;
	}

	@Override
	public String toString() {
		return this.number + " - " + this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Store) {
			return ((Store) obj).getName().equalsIgnoreCase(this.name)
					&& ((Store) obj).getNumber().equalsIgnoreCase(this.number);
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
