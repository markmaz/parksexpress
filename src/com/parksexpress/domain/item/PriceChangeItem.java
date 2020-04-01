package com.parksexpress.domain.item;

import java.text.ParseException;

import com.parksexpress.as400.util.DateChanger;

public class PriceChangeItem extends Item implements Comparable<PriceChangeItem> {
	private static final long serialVersionUID = -800097872277312841L;

	private float oldPrice;
	private float newPrice;
	private float oldSrp;
	private float newSrp;
	private String effectiveDate;
	private String fixedOrPercent;
	
	public PriceChangeItem(){}
	
	public float getOldPrice() {
		return this.oldPrice;
	}

	public void setOldPrice(final float oldPrice) {
		this.oldPrice = oldPrice;
	}

	public float getNewPrice() {
		return this.newPrice;
	}

	public void setNewPrice(final float newPrice) {
		this.newPrice = newPrice;
	}

	public float getDifference() {
		return this.newPrice - this.oldPrice;
	}

	public float getOldSrp() {
		return this.oldSrp;
	}

	public void setOldSrp(final float oldSrp) {
		this.oldSrp = oldSrp;
	}

	public float getNewSrp() {
		return this.newSrp;
	}

	public void setNewSrp(final float newSrp) {
		this.newSrp = newSrp;
	}

	public String getEffectiveDate() {
		try {
			return DateChanger.convertDateFromAS400(this.effectiveDate);
		} catch (ParseException e) {
			return "";
		}
	}

	public void setEffectiveDate(final String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getFixedOrPercent() {
		if(this.fixedOrPercent.equalsIgnoreCase("D")){
			return "F";
		}else{
			return "%";
		}
	}

	public void setFixedOrPercent(final String fixedOrPercent) {
		this.fixedOrPercent = fixedOrPercent;
	}

	@Override
	public int compareTo(PriceChangeItem o) {
		return this.getPriceBookFamilyCode().compareTo(o.getPriceBookFamilyCode());
	}

}
