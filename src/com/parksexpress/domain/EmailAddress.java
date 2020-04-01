package com.parksexpress.domain;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class EmailAddress {
	private String emailAddress;
	private String chainCode;
	private int id;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getChainCode() {
		return chainCode;
	}

	public void setChainCode(String chainCode) {
		this.chainCode = chainCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.emailAddress;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EmailAddress) {
			return ((EmailAddress) obj).getId() == this.id;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.id).append(this.chainCode)
				.append(this.emailAddress).toHashCode();
	}
}
