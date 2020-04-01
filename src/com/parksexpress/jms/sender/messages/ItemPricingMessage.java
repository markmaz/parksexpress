package com.parksexpress.jms.sender.messages;

import java.io.Serializable;

import com.parksexpress.domain.item.Item;

public class ItemPricingMessage implements Serializable{
	private static final long serialVersionUID = 8671153700086700637L;
	private Item item;
	private String book;
	
	public ItemPricingMessage(Item item, String book){
		this.item = item;
		this.book = book;
	}
	
	public Item getItem(){
		return this.item;
	}
	
	public String getPriceBook(){
		return this.book;
	}
}
