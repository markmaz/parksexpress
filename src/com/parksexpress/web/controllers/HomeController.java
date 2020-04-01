package com.parksexpress.web.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.User;
import com.parksexpress.domain.Zone;

public class HomeController extends ParksController {
	
	public HomeController(){}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if(request.getSession().getAttribute("authenticatedUser") == null){
			final ModelAndView view = new ModelAndView("logonForm");
			view.addObject("user", new User());
			return view;
		}else{	
			final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			final String currDate = sdf.format(Calendar.getInstance().getTime());
			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, -7);
			final String startDate = sdf.format(cal.getTime());
			
			final User user = this.getUser(request);
			final List<Zone> zones = user.getZones();
			final ModelAndView view = new ModelAndView("homePage");
			view.addObject("startDate", startDate);
			view.addObject("endDate", currDate);
			view.addObject("zone", zones.get(0).getNumber());
			return view;
		}
	}

}
