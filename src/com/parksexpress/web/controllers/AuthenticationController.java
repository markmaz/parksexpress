package com.parksexpress.web.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.parksexpress.domain.User;
import com.parksexpress.domain.Zone;
import com.parksexpress.services.AccountService;

public class AuthenticationController extends SimpleFormController {
	private AccountService accountService;

	public AuthenticationController(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		try {
			final User user = this.accountService.isAuthorized((User) command);
			Collections.sort(user.getPermissions());
	
			if (user != null) {
				request.getSession().setAttribute("authenticatedUser", user);
				
				final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				final String currDate = sdf.format(Calendar.getInstance().getTime());
				final Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_YEAR, -7);
				final String startDate = sdf.format(cal.getTime());
				final List<Zone> zones = user.getZones();
				
				ModelAndView view = new ModelAndView(this.getSuccessView());
				view.addObject("startDate", startDate);
				view.addObject("endDate", currDate);
				
				if(zones.size() > 0){
					view.addObject("zone", zones.get(0).getNumber());
				}else{
					view.addObject("zone", "");
				}
				
				return view;
			}
		} catch (InvalidCredentialsException e) {
			final ModelAndView view = new ModelAndView("logonForm");
			view.addObject("user", new User());
			view.addObject("errMsg",
					"Username or Password is incorrect. Please try again.");
			return view;
		}
		
		return new ModelAndView("error");
	}
}
