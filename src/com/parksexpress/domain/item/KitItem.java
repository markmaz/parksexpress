package com.parksexpress.domain.item;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class KitItem extends Item {
	private static final long serialVersionUID = -5641598644810485079L;
	private int quantity;
	private BigDecimal price;
	 
	public KitItem(){}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	
	public int getQuantity(){
		return this.quantity;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public String getFormattedPrice(){
		final DecimalFormat format = new DecimalFormat("#.00");
		return format.format(this.price.doubleValue());
	}
}