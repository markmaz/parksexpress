package com.parksexpress.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Invoice implements Serializable{
	public static final String CREDIT_MEMO = "C/M";
	public static final String INVOICE = "INV";
	public static final String PAYMENT = "PMT";
	public static final String ADJUSTMENT = "ADJ";
	public static final String FINAL = "FIN";

	private static final long serialVersionUID = -1945167711586596842L;
	private String storeNumber;
	private String number;
	private String type;
	private String code;
	private String date;
	private BigDecimal amount = new BigDecimal(0);
	private BigDecimal invoiceAmount = new BigDecimal(0);
	private BigDecimal paymentAmount = new BigDecimal(0);
	private BigDecimal adjustmentAmount = new BigDecimal(0);
	private BigDecimal balance = new BigDecimal(0);
	private float id;
	
	public Invoice(){
	}

	public String getStoreNumber() {
		return this.storeNumber;
	}

	public void setStoreNumber(final String storeNumber) {
		this.storeNumber = storeNumber;
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

	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(final String date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getInvoiceAmount() {
		return this.invoiceAmount;
	}

	public void setInvoiceAmount(final BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public BigDecimal getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(final BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getAdjustmentAmount() {
		return this.adjustmentAmount;
	}

	public void setAdjustmentAmount(final BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	public BigDecimal getBalance() {
		BigDecimal total = new BigDecimal(0);
		total = total.add(this.adjustmentAmount);
		total = total.add(this.paymentAmount);
		total = total.add(this.invoiceAmount);
		return total;
	}

	public void setBalance(final BigDecimal balance) {
		this.balance = balance;
	}

	public void setID(final float id) {
		this.id = id;
	} 
}
