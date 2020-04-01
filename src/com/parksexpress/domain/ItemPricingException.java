package com.parksexpress.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.parksexpress.domain.item.Item;

public class ItemPricingException implements Serializable {
	private static final long serialVersionUID = 7597298197396167657L;
	private Item item;
	private PriceBook book;
	
	public ItemPricingException(){
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(final Item item) {
		this.item = item;
	}

	public PriceBook getBook() {
		return this.book;
	}

	public void setBook(final PriceBook book) {
		this.book = book;
	}

	@Override
	public String toString(){
		return this.book.getNumber() + " - " + this.book.getDescription();
	}
	
	@Override
	public boolean equals(final Object obj) {
		if(obj instanceof ItemPricingException){
			final ItemPricingException pe = (ItemPricingException)obj;		
			return pe.getBook().getNumber().equalsIgnoreCase(this.getBook().getNumber());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		final int first = 11;
		final int last = 31;
		return new HashCodeBuilder(first, last).append(this.book.hashCode())
			.append(this.item.hashCode()).hashCode();
	}
}
