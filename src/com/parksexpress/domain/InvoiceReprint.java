package com.parksexpress.domain;

public class InvoiceReprint {
	private String invoiceNumber;
	private String orderNumber;
	private String invoiceDate;
	private String storeNumber;
	
	public InvoiceReprint(){}

	public String getInvoiceNumber() {
		return this.invoiceNumber;
	}

	public void setInvoiceNumber(final String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(final String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(final String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getStoreNumber() {
		return this.storeNumber;
	}

	public void setStoreNumber(final String storeNumber) {
		this.storeNumber = storeNumber;
	}
}
