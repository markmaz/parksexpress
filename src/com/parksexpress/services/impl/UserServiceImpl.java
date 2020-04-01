package com.parksexpress.services.impl;

import java.util.List;

import com.parksexpress.dao.UserDAO;
import com.parksexpress.domain.Permission;
import com.parksexpress.domain.User;
import com.parksexpress.services.UserService;

public class UserServiceImpl implements UserService{
	private UserDAO userDAO;

	public UserServiceImpl(){}
	
	public void setUserDAO(final UserDAO userDAO){
		this.userDAO = userDAO;
	}
	
	public List<User> search(final String searchCriteria, final String searchType) {
		return this.userDAO.search(searchCriteria, new Integer(searchType).intValue());
	}

	public List<User> getUserList() {
		return this.userDAO.getAll();
	}

	public User getUser(final String userID) {
		final User user = this.userDAO.getUser(new Integer(userID).intValue());
		user.setPermissions(userDAO.getPermissions(new Long(user.getId()).intValue()));
		return user;
	}

	public Number saveUser(final User user) {
		return this.userDAO.saveOrUpdate(user);
	}

	public void deleteUser(final User user) {
		this.userDAO.delete(user);
	}

	public void addStoreToUser(final long userID, final String storeNumber) {
		this.userDAO.addStoreToUser(new Long(userID).intValue(), storeNumber);
	}

	public void removeStoreFromUser(final long userID, final String storeNumber) {
		this.userDAO.removeStoreFromUser(new Long(userID).intValue(), storeNumber);
	}

	public void removeAllStoresFromUser(final long userID) {
		this.userDAO.removeAllStoresFromUser(userID);
	}

	public void addPermission(final String permission, final int userID) {
		this.userDAO.addPermission(permission, userID);
	}

	public Permission getPermission(final String name) {
		return this.userDAO.getPermission(name);
	}

	public List<Permission> getPermissions(final int userID) {
		return this.userDAO.getPermissions(userID);
	}

	public List<Permission> getPermissions() {
		return this.userDAO.getPermissions();
	}

	public boolean hasPermission(final String permission, final int userID) {
		return this.userDAO.hasPermission(permission, userID);
	}

	public void removeAllPermissions(final int userID) {
		this.userDAO.removeAllPermissions(userID);
	}

	public void removePermission(final String permission, final int userID) {
		this.userDAO.removePermission(permission, userID);
	}

	public String getOrderGuideNumber(final String chainCode) {
		return this.userDAO.getOrderGuideNumber(chainCode);
	}

}
