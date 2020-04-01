package com.parksexpress.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Pricing implements Serializable{
	public static final String CLASS = "C";
	public static final String FAMILY = "F";
	public static final String HEADER = "P";
	
	private static final long serialVersionUID = 4950947138779956250L;
	private BigDecimal price = new BigDecimal("0");
	private BigDecimal percent = new BigDecimal("0");
	private boolean isFixed;
	private BigDecimal cost = new BigDecimal("0");
	private String effectiveDate = "";
	private String pricingLevel = "";
	
	public Pricing(){}
	
	public Pricing(BigDecimal price, BigDecimal percent, boolean isFixed, BigDecimal cost, String effectiveDate, String pricingLevel){
		this.cost = cost;
		this.effectiveDate = effectiveDate;
		this.isFixed = isFixed;
		this.percent = percent;
		this.price = price;
		this.pricingLevel = pricingLevel;
	}
	
	public String getPricingLevel() {
		return this.pricingLevel;
	}

	public void setPricingLevel(final String pricingLevel) {
		this.pricingLevel = pricingLevel;
	}

	public String getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(final String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPercent() {
		return this.percent;
	}

	public void setPercent(final BigDecimal percent) {
		this.percent = percent;
	}

	public boolean isFixed() {
		return this.isFixed;
	}

	public void setFixed(final boolean isFixed) {
		this.isFixed = isFixed;
	}

	public BigDecimal getCost() {
		return this.cost;
	}

	public void setCost(final BigDecimal cost) {
		this.cost = cost;
	}
}
