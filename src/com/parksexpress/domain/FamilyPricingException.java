package com.parksexpress.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class FamilyPricingException implements Serializable {
	private static final long serialVersionUID = 5999728952274603082L;
	private PriceBookFamily family;
	private PriceBook priceBook;
	private List<ItemPricingException> itemExceptions = new ArrayList<ItemPricingException>();

	public FamilyPricingException() {
	}

	public FamilyPricingException(final PriceBookFamily family, final PriceBook book) {
		this.priceBook = book;
		this.family = family;
	}

	public List<ItemPricingException> getItemExceptions() {
		return this.itemExceptions;
	}

	public void setItemExceptions(final List<ItemPricingException> itemExceptions) {
		this.itemExceptions = itemExceptions;
	}

	public PriceBookFamily getFamily() {
		return this.family;
	}

	public void setFamily(final PriceBookFamily family) {
		this.family = family;
	}

	public PriceBook getPriceBook() {
		return this.priceBook;
	}

	public void setPriceBook(final PriceBook book) {
		this.priceBook = book;
	}

	@Override
	public String toString() {
		return this.priceBook.getNumber() + " - " + this.priceBook.getDescription();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FamilyPricingException) {
			final FamilyPricingException pe = (FamilyPricingException) obj;
			return pe.getPriceBook().getNumber().equalsIgnoreCase(
					this.getPriceBook().getNumber());
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int first = 11;
		final int last = 31;
		
		return new HashCodeBuilder(first, last).append(this.priceBook.hashCode()).append(
				this.family.hashCode()).hashCode();
	}
}
