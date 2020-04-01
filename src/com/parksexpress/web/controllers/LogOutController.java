package com.parksexpress.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.lifeway.ws.commons.domain.User;

public class LogOutController extends AbstractController {
	public LogOutController(){}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.getSession().invalidate();
		
		final ModelAndView view = new ModelAndView("logonForm");
		view.addObject("user", new User());
		return view;
	}

}
