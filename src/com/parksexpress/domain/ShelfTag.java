package com.parksexpress.domain;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.parksexpress.domain.item.Item;

public class ShelfTag {

	private String itemType;
	private String itemNumber;
	private String customerType;
	private String storeOrChainNumber;
	private String storeOrChainName;
	private String pack;
	private String price;
	private String percent;
	private String mUpt = "P";
	private String mCode = "S";
	private String roundingCode;
	private String effectiveDate;
	private String endDate;
	private String labelType = "9";
	private String web = "W";
	private String user;
	private Item item;
	
	public ShelfTag() {
	}

	
	
	public String getStoreOrChainName() {
		return this.storeOrChainName;
	}



	public void setStoreOrChainName(final String storeOrChainName) {
		this.storeOrChainName = storeOrChainName;
	}



	public Item getItem() {
		return this.item;
	}


	public void setItem(final Item item) {
		this.item = item;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemNumber() {
		return this.itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getStoreOrChainNumber() {
		return this.storeOrChainNumber;
	}

	public void setStoreOrChainNumber(String storeOrChainNumber) {
		this.storeOrChainNumber = storeOrChainNumber;
	}

	public String getPack() {
		return this.pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPercent() {
		return this.percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getmUpt() {
		return this.mUpt;
	}

	public void setmUpt(String mUpt) {
		this.mUpt = mUpt;
	}

	public String getmCode() {
		return this.mCode;
	}

	public void setmCode(String mCode) {
		this.mCode = mCode;
	}

	public String getRoundingCode() {
		return this.roundingCode;
	}

	public void setRoundingCode(String roundingCode) {
		this.roundingCode = roundingCode;
	}

	public String getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLabelType() {
		return this.labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ShelfTag){
			final ShelfTag tag = (ShelfTag)obj;
			
			return tag.getCustomerType().equalsIgnoreCase(this.customerType) &&
				tag.getEffectiveDate().equalsIgnoreCase(this.effectiveDate) &&
				tag.getEndDate().equalsIgnoreCase(this.endDate) &&
				tag.getItem().equals(this.item) &&
				tag.getItemNumber().equalsIgnoreCase(this.itemNumber) &&
				tag.getLabelType().equalsIgnoreCase(this.labelType) &&
				tag.getStoreOrChainName().equalsIgnoreCase(this.storeOrChainName) &&
				tag.getStoreOrChainNumber().equalsIgnoreCase(this.storeOrChainNumber) &&
				tag.getUser().equalsIgnoreCase(this.user);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(11, 31).append(this.customerType).append(this.customerType)
			.append(this.effectiveDate).append(this.endDate).append(this.item)
			.append(this.itemNumber).append(this.labelType).append(this.storeOrChainName).append(this.storeOrChainNumber)
			.append(this.user).toHashCode();
	}
	
}
