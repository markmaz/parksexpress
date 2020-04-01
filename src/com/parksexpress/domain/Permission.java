package com.parksexpress.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Permission implements Serializable, Comparable<Permission> {
	private static final long serialVersionUID = 6838265541817545354L;
	private int id;
	private String name;
	private String description;
	private int priority;
	
	public Permission() {
	}

	public Permission(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Permission(String name){
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(final int priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return this.name + " - " + this.description;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Permission) {
			final Permission p = (Permission) obj;

			return p.getName().equalsIgnoreCase(this.name);
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int first = 11;
		final int last = 37;
		return new HashCodeBuilder(first, last).append(this.name).toHashCode();
	}

	public int compareTo(final Permission o) {
		return this.priority - o.getPriority();
	}
}
