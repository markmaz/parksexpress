package com.parksexpress.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class PriceBookClass implements Serializable {
	private static final long serialVersionUID = 8170949019444294015L;
	private Long priceBookClassID;
	private String description;
	private String classCode;
	private List<PriceBookFamily> families = new ArrayList<PriceBookFamily>();
	private Pricing pricing;
	
	public PriceBookClass(){
	}
	
	public Pricing getPricing() {
		return this.pricing;
	}

	public void setPricing(final Pricing pricing) {
		this.pricing = pricing;
	}

	public List<PriceBookFamily> getPriceBookFamilies() {
		return this.families;
	}
	
	public void setPriceBookFamilies(final List<PriceBookFamily> families){
		this.families = families;
	}
	
	@Override
	public String toString() {
		return this.classCode + " - " + this.description;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Chain) {
			return ((Chain) obj).getName().equalsIgnoreCase(this.classCode);
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int first = 11;
		final int last = 31;
		return new HashCodeBuilder(first, last).append(this.classCode).toHashCode();
	}

	public Long getPriceBookClassID() {
		return this.priceBookClassID;
	}

	public void setPriceBookClassID(final Long priceBookClassID) {
		this.priceBookClassID = priceBookClassID;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getClassCode() {
		return this.classCode;
	}

	public void setClassCode(final String classCode) {
		this.classCode = classCode;
	}
}
