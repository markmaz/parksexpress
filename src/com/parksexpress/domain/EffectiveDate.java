package com.parksexpress.domain;

public class EffectiveDate {
	public boolean isEffective;
	public String startDate;
	public Float futureCost;

	public boolean isEffective() {
		return isEffective;
	}

	public void setEffective(boolean isEffective) {
		this.isEffective = isEffective;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Float getFutureCost() {
		return futureCost;
	}

	public void setFutureCost(Float futureCost) {
		this.futureCost = futureCost;
	}

}
