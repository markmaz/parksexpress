/**
 * 
 */
package com.parksexpress.domain;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author mark
 * 
 */
public class Job implements Serializable {
	private static final long serialVersionUID = -2041482134257663762L;

	private String name;
	private String description;
	private String systemPID;
	private String fatherPID;
	private String rootPID;
	private String moment;
	private long duration;
	private String message;
	private String messageType;

	public Job(){
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

	public String getSystemPID() {
		return this.systemPID;
	}

	public void setSystemPID(final String systemPID) {
		this.systemPID = systemPID;
	}

	public String getFatherPID() {
		return this.fatherPID;
	}

	public void setFatherPID(final String fatherPID) {
		this.fatherPID = fatherPID;
	}

	public String getRootPID() {
		return this.rootPID;
	}

	public void setRootPID(final String rootPID) {
		this.rootPID = rootPID;
	}

	public String getMoment() {
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = "";
		
		try {
			date = sdf.format(sdf2.parse(this.moment));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
	}

	public void setMoment(final String moment) {
		this.moment = moment;
	}

	public String getDuration() {
		final DecimalFormat format = new DecimalFormat("####.##");
		final double time = .001;
		return format.format(this.duration * time);
	}

	public void setDuration(final long duration) {
		this.duration = duration;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getMessageType() {
		return this.messageType;
	}

	public void setMessageType(final String messageType) {
		this.messageType = messageType;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int first = 11;
		final int last = 31;
		
		return new HashCodeBuilder(first, last).append(
				new Object[] { this.name, this.rootPID, this.fatherPID })
				.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return new EqualsBuilder().append(this, obj).isEquals();
	}
}
