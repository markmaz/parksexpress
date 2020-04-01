package com.parksexpress.domain.item;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.parksexpress.domain.User;

public class HistoryItem implements Serializable {
	private static final long serialVersionUID = 2186610096778749244L;
	private String details;
	private String transactionDate;
	private String chainCode;
	private User user = new User();

	public HistoryItem(){}
	
	public String getChainCode() {
		return this.chainCode;
	}

	public void setChainCode(String chainCode) {
		this.chainCode = chainCode;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HistoryItem) {
			final HistoryItem item = (HistoryItem) obj;
			return item.getDetails().equalsIgnoreCase(this.details)
					&& item.getTransactionDate().equalsIgnoreCase(
							this.transactionDate)
					&& item.getUser().getId() == this.getUser().getId();
		}

		return false;
	}

	@Override
	public String toString() {
		return this.transactionDate + " - " + this.details + " - "
				+ this.user.getLastName();
	}

	@Override
	public int hashCode() {
		final int start = 11;
		final int end = 31;
		
		return new HashCodeBuilder(start, end).append(this.transactionDate).append(
				this.details).append(this.user.hashCode()).toHashCode();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
