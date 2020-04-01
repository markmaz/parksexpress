package com.parksexpress.domain.item;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.parksexpress.domain.Pricing;

public class Item implements Serializable {
	public static final int ITEM_NUMBER_LENGTH = 6;
	private static final long serialVersionUID = -5154461674645272958L;
	protected Float marketCost;

	private Long itemID;
	private String number;
	private String retailUPCNumber;
	private String cartonUPCNumber;
	private String checkDigit;
	private String description;
	private String pack;
	private String size;
	private String vendorNumber;
	private String priceBookHeaderCode;
	private String priceBookFamilyCode;
	private String priceBookClassCode;
	private String status;
	private Pricing pricing;
	private Pricing futurePricing;

	public Pricing getFuturePricing() {
		return futurePricing;
	}

	public void setFuturePricing(Pricing futurePricing) {
		this.futurePricing = futurePricing;
	}

	private boolean isInOrderGuide;

	public Item() {
	}

	public boolean isInOrderGuide() {
		return this.isInOrderGuide;
	}

	public void setInOrderGuide(final boolean isInOrderGuide) {
		this.isInOrderGuide = isInOrderGuide;
	}

	public Pricing getPricing() {
		return this.pricing;
	}

	public void setPricing(final Pricing pricing) {
		this.pricing = pricing;
	}

	public Long getItemID() {
		return this.itemID;
	}

	public void setItemID(final Long itemID) {
		this.itemID = itemID;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public String getRetailUPCNumber() {
		return this.retailUPCNumber;
	}

	public void setRetailUPCNumber(final String retailUPCNumber) {
		this.retailUPCNumber = retailUPCNumber;
	}

	public String getCartonUPCNumber() {
		return this.cartonUPCNumber;
	}

	public void setCartonUPCNumber(final String cartonUPCNumber) {
		this.cartonUPCNumber = cartonUPCNumber;
	}

	public String getCheckDigit() {
		return this.checkDigit;
	}

	public void setCheckDigit(final String checkDigit) {
		this.checkDigit = checkDigit;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getPack() {
		return this.pack;
	}

	public void setPack(final String pack) {
		this.pack = pack;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(final String size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return this.number;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Item) {
			return ((Item) obj).getNumber().equalsIgnoreCase(this.number);
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int first = 11;
		final int next = 31;

		return new HashCodeBuilder(first, next).append(this.number)
				.toHashCode();
	}

	public String getVendorNumber() {
		return this.vendorNumber;
	}

	public void setVendorNumber(final String vendorNumber) {
		this.vendorNumber = vendorNumber;
	}

	public String getPriceBookHeaderCode() {
		return this.priceBookHeaderCode;
	}

	public void setPriceBookHeaderCode(final String priceBookHeaderCode) {
		this.priceBookHeaderCode = priceBookHeaderCode;
	}

	public String getPriceBookFamilyCode() {
		return this.priceBookFamilyCode;
	}

	public void setPriceBookFamilyCode(final String priceBookFamilyCode) {
		this.priceBookFamilyCode = priceBookFamilyCode;
	}

	public String getPriceBookClassCode() {
		return this.priceBookClassCode;
	}

	public void setPriceBookClassCode(final String priceBookClassCode) {
		this.priceBookClassCode = priceBookClassCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCheckDigitItemNumber() {
		if (this.number.length() < Item.ITEM_NUMBER_LENGTH) {
			return "0" + this.number + this.checkDigit;
		} else {
			return this.number + this.checkDigit;
		}
	}

	public static String getItemNumberFromCheckDigit(String number) {
		if (number.substring(0, 1).equals("0")) {
			return number.substring(1, number.length() - 1);
		}

		return number.substring(0, number.length() - 1);
	}

	public Float getMarketCost() {
		return this.marketCost;
	}

	public void setMarketCost(final Float marketCost) {
		this.marketCost = marketCost;
	}

	public Float getGrossProfit() {

		float profit = 0F;

		try {
			float fCost = new Float(this.marketCost).floatValue();
			float fPack = new Float(this.pack).floatValue();
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

}
