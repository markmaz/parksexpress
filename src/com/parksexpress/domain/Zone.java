package com.parksexpress.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Zone implements Serializable {
	private static final long serialVersionUID = -5466856087036213273L;
	private String name;
	private String number;

	public Zone(){}

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

	@Override
	public String toString() {
		return this.name + " : " + this.number;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Zone) {
			final Zone zone = (Zone) obj;
			return zone.getName().equalsIgnoreCase(this.name)
					&& zone.getNumber().equalsIgnoreCase(this.number);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		final int first = 11; 
		final int last = 31;
		
		return new HashCodeBuilder(first, last).append(this.name).append(
				this.number).toHashCode();
	}
}
