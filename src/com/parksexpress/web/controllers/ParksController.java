package com.parksexpress.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.parksexpress.domain.Store;
import com.parksexpress.domain.User;

public class ParksController extends MultiActionController {
	public ParksController(){}
	
	protected boolean isValidStoreNumber(User user, String storeNumber){
		for(Store store : user.getStores()){
			if(store.getNumber().equalsIgnoreCase(storeNumber)){
				return true;
			}
		}
		
		return false;
	}
	
	protected User getUser(HttpServletRequest request){
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		return user;
	}
}
