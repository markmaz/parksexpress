package com.parksexpress.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.parksexpress.domain.item.Item;

public class PriceBookFamily implements Serializable, Comparable<PriceBookFamily>{
	private static final long serialVersionUID = 8288587814578471440L;
	private Long priceBookFamilyID;
	private String description;
	private String familyCode;
	private Pricing pricing;
	private String pack;
	private Float marketCost;
	
	private List<Item> items = new ArrayList<Item>();

	public PriceBookFamily(){
	}

	public Pricing getPricing() {
		return this.pricing;
	}

	public void setPricing(final Pricing pricing) {
		this.pricing = pricing;
	}

	@Override
	public String toString() {
		return this.familyCode + " - " + this.description;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Chain) {
			return ((Chain) obj).getName().equalsIgnoreCase(this.familyCode);
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int first = 11;
		final int last = 31;
		return new HashCodeBuilder(first, last).append(this.familyCode).toHashCode();
	}

	public Long getPriceBookFamilyID() {
		return this.priceBookFamilyID;
	}

	public void setPriceBookFamilyID(final Long priceBookClassID) {
		this.priceBookFamilyID = priceBookClassID;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description.replace("'", "");
	}

	public String getFamilyCode() {
		return this.familyCode;
	}

	public void setFamilyCode(final String familyCode) {
		this.familyCode = familyCode;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(final List<Item> items) {
		this.items = items;
	}

	@Override
	public int compareTo(PriceBookFamily family) {
		return this.familyCode.compareTo(family.getFamilyCode());
	}
	
	public Float getGrossProfit(){
		if(this.pricing == null || this.items.size() == 0){
			return new Float(0);
		}
		
		float profit = 0F;

		try {
			float fCost = new Float(this.items.get(0).getMarketCost()).floatValue();
			float fPack = new Float(this.items.get(0).getPack()).floatValue();
			float fCostPerItem = fCost / fPack;
			float price = 0F;
			float fAddendum = 0F;

			if (this.pricing.getPercent().floatValue() == 0) {
				profit = 0;
			} else {
				profit = this.pricing.getPercent().floatValue();
			}

			if (this.pricing.getPrice().floatValue() == 0) {
				price = 0;
			} else {
				price = this.pricing.getPrice().floatValue();
			}

			if (price == 0.0 && profit != 0) {
				fAddendum = 1 - profit; // (profit/100);
				price = fCostPerItem / fAddendum;
			}

			if (price != 0 && profit == 0.0) {
				fAddendum = price - fCostPerItem;
				profit = (fAddendum / price) * 100;
			}

			if (price == 0 && profit == 0) {
				price = 0;
				profit = 0;
			}

		} catch (NumberFormatException nfe) {
			System.out.println("FigureCurrentPrices: " + nfe.getMessage());
		}
		
		return new Float(profit);
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public Float getMarketCost() {
		return marketCost;
	}

	public void setMarketCost(Float marketCost) {
		this.marketCost = marketCost;
	}
}
