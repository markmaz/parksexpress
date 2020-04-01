package com.parksexpress.dao;

import java.util.List;

import org.apache.commons.httpclient.auth.InvalidCredentialsException;

import com.parksexpress.domain.Permission;
import com.parksexpress.domain.User;

public interface UserDAO {
	int USERNAME = 0;
	int FIRSTNAME = 1;
	int LASTNAME = 2;
	int EMAIL = 3;
	
	User isAuthorized(String username, String password) throws InvalidCredentialsException;
	List<User> search(String searchCriteria, int searchType);
	List<User> getAll();
	User getUser(int userID);
	Number saveOrUpdate(User user);
	void delete(User user);
	void addStoreToUser(int userID, String storeNumber);
	void removeStoreFromUser(int userID, String storeNumber);
	void removeAllStoresFromUser(long userID);
	List<Permission> getPermissions(int userID);
	boolean hasPermission(String permission, int userID);
	void addPermission(String permission, int userID);
	void removePermission(String permission, int userID);
	void removeAllPermissions(int userID);
	Permission getPermission(String name);
	List<Permission> getPermissions();
	String getOrderGuideNumber(String chainCode);
}
