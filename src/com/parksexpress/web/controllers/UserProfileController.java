package com.parksexpress.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.SimpleFormController;

import com.parksexpress.domain.User;

public class UserProfileController extends SimpleFormController {
	private User user;
	
	public UserProfileController(){}
	
	@Override
	protected Object formBackingObject(final HttpServletRequest request)
			throws Exception {

		this.user = (User)request.getSession().getAttribute("user");
		return this.user;
	}
}
