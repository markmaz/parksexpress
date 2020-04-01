package com.parksexpress.services;

import java.util.List;

import com.parksexpress.domain.Permission;
import com.parksexpress.domain.User;

public interface UserService {
	List<User> search(String searchCriteria, String searchType);
	List<User> getUserList();
	User getUser(String userID);
	Number saveUser(User user);
	void deleteUser(User user);
	void addStoreToUser(long userID, String storeNumber);
	void removeStoreFromUser(long userID, String storeNumber);
	void removeAllStoresFromUser(long longValue);
	List<Permission> getPermissions(int userID);
	boolean hasPermission(String permission, int userID);
	void addPermission(String permission, int userID);
	void removePermission(String permission, int userID);
	void removeAllPermissions(int userID);
	Permission getPermission(String name);
	List<Permission> getPermissions();
	String getOrderGuideNumber(String chainCode);
}
