package com.parksexpress.jms.mdp;

import java.io.Serializable;

import com.parksexpress.domain.item.Item;

public class OrderGuideMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	private String chainCode;
	private Item item;
	
	public OrderGuideMessage(){}
	
	public OrderGuideMessage(String chainCode, Item item){
		this.item = item;
		this.chainCode = chainCode;
	}
	
	public String getChainCode() {
		return this.chainCode;
	}
	public void setChainCode(String chainCode) {
		this.chainCode = chainCode;
	}
	public Item getItem() {
		return this.item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
	
}
