package com.parksexpress.domain;

import java.io.Serializable;
import java.util.List;

import com.parksexpress.domain.item.KitItem;

public class Kit extends KitItem implements Serializable{
	private static final long serialVersionUID = 5136674616770578035L;
	private List<KitItem> components;
	
	public Kit(){}

	public List<KitItem> getComponents() {
		return this.components;
	}

	public void setComponents(List<KitItem> components) {
		this.components = components;
	}
}
