package com.parksexpress.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class PriceBookHeader implements Serializable, Comparable<PriceBookHeader> {
	private static final long serialVersionUID = 7502308660608662413L;
	private Long priceBookHeaderID;
	private String description;
	private String headerCode;
	private List<PriceBookClass> priceBookClasses = new ArrayList<PriceBookClass>();
	
	public PriceBookHeader(){
	}

	@Override
	public String toString() {
		return this.headerCode + " - " + this.description;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PriceBookHeader) {
			return ((PriceBookHeader) obj).getHeaderCode().equalsIgnoreCase(this.headerCode);
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int first = 11;
		final int last = 31;
		return new HashCodeBuilder(first, last).append(this.headerCode).toHashCode();
	}

	public Long getPriceBookHeaderID() {
		return this.priceBookHeaderID;
	}

	public void setPriceBookHeaderID(final Long priceBookClassID) {
		this.priceBookHeaderID = priceBookClassID;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getHeaderCode() {
		return this.headerCode;
	}

	public void setHeaderCode(final String headerCode) {
		this.headerCode = headerCode;
	}

	public List<PriceBookClass> getPriceBookClasses() {
		return this.priceBookClasses;
	}

	public void setPriceBookClasses(final List<PriceBookClass> priceBookClasses) {
		this.priceBookClasses = priceBookClasses;
	}

	@Override
	public int compareTo(PriceBookHeader o) {
		return this.headerCode.compareTo(o.getHeaderCode());
	}
}
