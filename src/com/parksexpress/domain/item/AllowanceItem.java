package com.parksexpress.domain.item;


public class AllowanceItem extends Item {
	private static final long serialVersionUID = 6788922118130256271L;
	private Float costAllowance;
	private String expirationDate;
	private String startDate;

	public AllowanceItem(){}
	
	public Float getCostAllowance() {
		return this.costAllowance;
	}

	public void setCostAllowance(Float costAllowance) {
		this.costAllowance = costAllowance;
	}

	public String getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public Float getSpecialPrice() {
		return this.marketCost - this.costAllowance;
	}
}
