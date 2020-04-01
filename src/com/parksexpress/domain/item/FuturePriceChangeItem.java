package com.parksexpress.domain.item;

import java.text.ParseException;

import com.parksexpress.as400.util.DateChanger;

public class FuturePriceChangeItem extends Item implements Comparable<FuturePriceChangeItem>{
	private static final long serialVersionUID = 2204599290680365531L;
	private float oldPrice;
	private float newPrice;
	private float difference;
	private String effectiveDate;

	public FuturePriceChangeItem(){}

	public float getOldPrice() {
		return this.oldPrice;
	}

	public void setOldPrice(float oldPrice) {
		this.oldPrice = oldPrice;
	}

	public float getNewPrice() {
		return this.newPrice;
	}

	public void setNewPrice(float newPrice) {
		this.newPrice = newPrice;
	}

	public String getEffectiveDate() {
		try {
			return DateChanger.convertDateFromAS400(this.effectiveDate);
		} catch (ParseException e) {
			return "";
		}
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public float getDifference() {
		return this.difference;
	}

	public void setDifference(float difference) {
		this.difference = difference;
	}
	
	@Override
	public int compareTo(FuturePriceChangeItem o) {
		return this.getPriceBookFamilyCode().compareTo(o.getPriceBookFamilyCode());
	}
}