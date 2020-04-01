package com.parksexpress.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.Store;
import com.parksexpress.domain.User;
import com.parksexpress.services.StoreService;
import com.parksexpress.services.UserService;


public class UserController extends ParksController {
	private UserService userService;
	private StoreService storeService;
	private Logger log = Logger.getRootLogger();
	
	public UserController(){}
	
	public void setuserService(final UserService userService) {
		this.userService = userService;
	}
	
	public void setStoreService(final StoreService storeService){
		this.storeService = storeService;
	}
	
	public ModelAndView searchAhead(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final String searchCriteria = request.getParameter("searchCriteria");
		String searchType = request.getParameter("type");
		final ModelAndView view = new ModelAndView("userTypeAhead");
		
		if(StringUtils.isEmpty(searchType)){
			searchType = "0";
		}
		
		this.log.warn("search parameters: " + searchCriteria + ":" + searchType);
		
		view.addObject("users", this.userService.search(searchCriteria, searchType));
		return view;		
	}

	public ModelAndView search(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final String searchCriteria = request.getParameter("searchCriteria");
		String searchType = request.getParameter("type");
		final ModelAndView view = new ModelAndView("userSummary");
		
		if(StringUtils.isEmpty(searchCriteria)){
			view.addObject("users", this.userService.getUserList());
			return view;
		}
		
		if(StringUtils.isEmpty(searchType)){
			searchType = "0";
		}
		
		this.log.debug("search parameters: " + searchCriteria + ":" + searchType);
		
		view.addObject("users", this.userService.search(searchCriteria, searchType));
		return view;
	}
	
	public ModelAndView userDetail(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		final String userID = request.getParameter("id");
		
		if(StringUtils.isEmpty(userID)){
			return new ModelAndView("userSummary");
		}
		
		final User user = this.userService.getUser(userID);
		final ModelAndView view = new ModelAndView("userDetail", "user", user);
		view.addObject("permissions", this.userService.getPermissions());
		view.addObject("stores", this.storeService.getStoresForUser(user.getId(), true));
		return view;
	}
	
	public ModelAndView save(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		this.userService.saveUser(this.buildUser(request));
		return new ModelAndView("userSummary");
	}	
	
	public ModelAndView delete(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		this.userService.deleteUser(this.buildUser(request));
		return new ModelAndView("userSummary");
	}
	
	public ModelAndView create(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		return new ModelAndView("userDetail");
	}
	
	public ModelAndView addStore(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		final String type = request.getParameter("type");
		final String id = request.getParameter("storeID");
		final User user = this.buildUser(request);
		boolean isNew = false;
		
		if(user.getId() == 0){
			isNew = true;
		}
		
		final long userID = this.userService.saveUser(user).longValue();
		
		if(type.equalsIgnoreCase("chain")){
			final List<Store> stores = this.storeService.getStoresInChain(id, true);
			for(Store store : stores){
				this.userService.addStoreToUser(userID, store.getNumber());
			}
		}else{
			final Store store = this.storeService.getStore(id);
			this.userService.addStoreToUser(userID, store.getNumber());
		}
		
		if(isNew){
			user.setId(userID);
			final ModelAndView view = new ModelAndView("userDetail", "user", user);
			view.addObject("stores", this.storeService.getStoresForUser(user.getId(), true));
			return view;
		}else{
			final ModelAndView view = new ModelAndView("userstores", "stores", 
					this.storeService.getStoresForUser(new Long(userID).longValue(), true));
			view.addObject("userID", userID);
			return view;
		}
	}
	
	public ModelAndView removeStore(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		final String userID = request.getParameter("userID");
		final String storenumber = request.getParameter("storeID");
		this.userService.removeStoreFromUser(new Long(userID).longValue(), storenumber);
		final ModelAndView view =  new ModelAndView("userstores", "stores", 
				this.storeService.getStoresForUser(new Long(userID).longValue(), true));
		view.addObject("userID", userID);
		return view;
	}
	
	public ModelAndView removeAllStores(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		final String userID = request.getParameter("id");
		this.userService.removeAllStoresFromUser(new Long(userID).longValue());
		final ModelAndView view =  new ModelAndView("userstores", "stores", 
				this.storeService.getStoresForUser(new Long(userID).longValue(), true));
		view.addObject("userID", userID);
		return view;
	}	
	private User buildUser(final HttpServletRequest request){
		final User user = new User();
		
		if(StringUtils.isNotEmpty(request.getParameter("id"))){
			user.setId(new Long(request.getParameter("id")).longValue());
		}
		
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmailAddress(request.getParameter("emailAddress"));
		user.setPassword(request.getParameter("password"));
		user.setUsername(request.getParameter("username"));
		return user;
	}
	
	public ModelAndView logout(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		
		final ModelAndView view = new ModelAndView("logonForm");
		view.addObject("user", new User());
		return view;
	}
	
	public ModelAndView addPermission(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String name = request.getParameter("name");
		final String userID = request.getParameter("userID");
		final ModelAndView view = new ModelAndView("message");
		
		if(!StringUtils.isEmpty(userID)){
			this.userService.addPermission(name, new Integer(userID).intValue());
			view.addObject("message", "Permission Added.");
		}else{
			view.addObject("message", "Error adding permission.");
		}
		
		return view;
	}
	
	public ModelAndView removePermission(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String name = request.getParameter("name");
		final String userID = request.getParameter("userID");
		final ModelAndView view = new ModelAndView("message");
		
		if(!StringUtils.isEmpty(userID)){
			this.userService.removePermission(name, new Integer(userID).intValue());
			view.addObject("message", "Permission removed.");
		}else{
			view.addObject("message", "Error removing permission.");
		}
		
		return view;
	}	
}
