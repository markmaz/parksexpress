package com.parksexpress.jms.sender.messages;

import java.io.Serializable;

import com.parksexpress.domain.PriceBookFamily;

public class FamilyPricingMessage implements Serializable{
	private static final long serialVersionUID = 4745363360862741253L;
	private PriceBookFamily family;
	private String srpBook;
	
	public FamilyPricingMessage(){}
	
	public FamilyPricingMessage(PriceBookFamily family, String srpBook){
		this.family = family;
		this.srpBook = srpBook;
	}

	public PriceBookFamily getFamily() {
		return this.family;
	}

	public void setFamily(final PriceBookFamily family) {
		this.family = family;
	}

	public String getSrpBook() {
		return this.srpBook;
	}

	public void setSrpBook(final String srpBook) {
		this.srpBook = srpBook;
	}

	@Override
	public String toString() {
		return this.family.toString() + ":" + this.srpBook;
	}	
}