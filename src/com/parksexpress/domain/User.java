/**
 * 
 */
package com.parksexpress.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author mark
 * 
 */
public class User implements Serializable {
	private static final long serialVersionUID = 6833685761953054664L;

	private String orderGuideNumber;
	
	private String firstName;

	private String lastName;

	private String emailAddress;
	
	private String username;
	
	private String password;
	
	private long id;

	private List<Store> stores = new ArrayList<Store>();
	
	private List<Permission> permissions = new ArrayList<Permission>();
	
	private List<Zone> zones = new ArrayList<Zone>();
	
	public User(){
	}
	
	public List<Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(final List<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(final String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return StringUtils.capitalize(this.firstName);
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return StringUtils.capitalize(this.lastName);
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return this.lastName + ", " + this.firstName + " : " + this.emailAddress;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof User) {
			return ((User) obj).getEmailAddress().equalsIgnoreCase(
					this.emailAddress)
					&& ((User) obj).getFirstName().equalsIgnoreCase(
							this.firstName)
					&& ((User) obj).getLastName()
							.equalsIgnoreCase(this.lastName);
		}

		return false;
	}

	@Override
	public int hashCode() {
		final int first = 11; 
		final int last = 31;
		
		return new HashCodeBuilder(first, last).append(this.emailAddress).append(
				this.firstName).append(this.lastName).toHashCode();
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public long getId() {
		return this.id;
	}

	public void setId(final long userID) {
		this.id = userID;
	}

	public List<Store> getStores() {
		return this.stores;
	}

	public void setStores(final List<Store> stores) {
		this.stores = stores;
	}
	
	public List<Store> getStoresWithoutZones(){
		final List<Store> zoneFreeList = new ArrayList<Store>();
		final int zoneLevel = 8000;
		
		for(Store store : this.stores){
			if(StringUtils.isNotBlank(store.getNumber())){
				if(Integer.valueOf(store.getNumber()).intValue() < zoneLevel){
					zoneFreeList.add(store);
				}
			}
		}
		
		return zoneFreeList;
	}
	
	public Store getStore(String storeNumber){
		for(Store store : this.stores){
			if(StringUtils.isNotBlank(store.getNumber())){
				if(store.getNumber().equalsIgnoreCase(storeNumber)){
					return store;
				}
			}
		}
		
		return null;
	}
	
	public boolean hasPermission(final String name){
		return this.permissions.contains(new Permission(name));
	}
	
	public boolean isSrpPermissible(){
		return this.hasPermission("SRP");
	}
	
	public boolean isReportsPermissible(){
		return this.hasPermission("Reports");
	}
	
	public boolean isShelfLabelPermissible(){
		return this.hasPermission("Shelf Labels");
	}
	
	public boolean isFtpPermissible(){
		return this.hasPermission("FTP");
	}
	
	public boolean isAccountsPayablePermissible(){
		return this.hasPermission("Accounts Payable");
	}
	
	public boolean isAdmin(){
		return this.hasPermission("Admin");
	}

	public String getAdmin(){
		if(this.hasPermission("Admin")){
			return "Y";
		}else{
			return "N";
		}
	}
	
	public String getOrderGuideNumber() {
		return this.orderGuideNumber;
	}

	public void setOrderGuideNumber(final String orderGuideNumber) {
		this.orderGuideNumber = orderGuideNumber;
	}

	public List<Zone> getZones() {
		return this.zones;
	}

	public void setZones(final List<Zone> zones) {
		this.zones = zones;
	}
}
