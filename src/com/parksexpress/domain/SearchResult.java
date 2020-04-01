package com.parksexpress.domain;

import java.io.Serializable;

public class SearchResult implements Serializable{
	private static final long serialVersionUID = 5811221356944386649L;
	private String name;
	private String number;
	private String type;
	private String upc;
	private String pack;
	private String size;
	
	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}
	
	public SearchResult(String name, String number, String type, String upc, String pack, String size) {
		this.name = name;
		this.number = number;
		this.type = type;
		this.upc = upc;
		this.pack = pack;
		this.size = size;
	}

	public SearchResult(String name, String number, String type) {
		this.name = name;
		this.number = number;
		this.type = type;
	}

	public SearchResult() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}
}