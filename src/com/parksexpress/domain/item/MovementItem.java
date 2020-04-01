package com.parksexpress.domain.item;

import java.math.BigDecimal;

public class MovementItem extends Item {
	private static final long serialVersionUID = 5742179758152850610L;
	private BigDecimal srp;
	private int fullCase;
	private BigDecimal profitDollars;
	private String profitPercent;
	private BigDecimal extendedCostAmount;
	
	public MovementItem(){}

	public BigDecimal getSrp() {
		return this.srp;
	}

	public void setSrp(BigDecimal srp) {
		this.srp = srp;
	}

	public int getFullCase() {
		return this.fullCase;
	}

	public void setFullCase(int fullCase) {
		this.fullCase = fullCase;
	}

	public BigDecimal getProfitDollars() {
		return this.profitDollars;
	}

	public void setProfitDollars(BigDecimal profitDollars) {
		this.profitDollars = profitDollars;
	}

	public String getProfitPercent() {
		return this.profitPercent;
	}

	public void setProfitPercent(String profitPercent) {
		this.profitPercent = profitPercent;
	}

	public BigDecimal getExtendedCostAmount() {
		return this.extendedCostAmount;
	}

	public void setExtendedCostAmount(BigDecimal extendedCostAmount) {
		this.extendedCostAmount = extendedCostAmount;
	}
	
	
}
