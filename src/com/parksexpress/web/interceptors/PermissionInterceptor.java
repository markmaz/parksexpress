package com.parksexpress.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.parksexpress.domain.Permission;
import com.parksexpress.domain.User;

public class PermissionInterceptor extends HandlerInterceptorAdapter {
	private String redirectPage;
	private String permissionName;
	
	public PermissionInterceptor(){}
	
	public void setRedirectPage(final String name){
		this.redirectPage = name;
	}
	
	public String getRedirectPage(){
		return this.redirectPage;
	}
	
	public String getPermissionName() {
		return this.permissionName;
	}

	public void setPermissionName(final String permissionName) {
		this.permissionName = permissionName;
	}

	@Override
	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler) throws Exception {
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		
		if(user == null){
			response.sendRedirect(this.redirectPage);
			return false;
		}
		
		
		if(user.getPermissions().contains(new Permission(this.permissionName))){
			return true;
		}else{
			response.sendRedirect(this.redirectPage);
			return false;
		}
		
	}
}
