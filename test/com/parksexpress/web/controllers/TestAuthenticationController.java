package com.parksexpress.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.User;
import com.parksexpress.services.AccountService;

public class TestAuthenticationController {

	@Test
	public void testOnSubmitHttpServletRequestHttpServletResponseObjectBindException() {
		AccountService service = mock(AccountService.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		
		User user = mock(User.class);
		User newUser = new User();
		newUser.setEmailAddress("test");
		newUser.setFirstName("Test");
		newUser.setLastName("test");
		
		try {
			when(service.isAuthorized(user)).thenReturn(newUser);
		} catch (InvalidCredentialsException e) {
			fail("Bad User");
		}
		
		AuthenticationController controller = new AuthenticationController(service);
		controller.setSuccessView("success");
		when(request.getSession()).thenReturn(session);
		
		try {
			ModelAndView view = controller.onSubmit(request, null, user, null);
			verify(session).setAttribute("authenticatedUser", newUser);
			assertEquals("success", view.getViewName());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testOnSubmitFailureHttpServletRequestHttpServletResponseObjectBindException() {
		AccountService service = mock(AccountService.class);
		HttpSession session = mock(HttpSession.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		
		User user = mock(User.class);
		User newUser = new User();
		newUser.setEmailAddress("test");
		newUser.setFirstName("Test");
		newUser.setLastName("test");
				
		AuthenticationController controller = new AuthenticationController(service);
		controller.setSuccessView("success");
		when(request.getSession()).thenReturn(session);
		
		try {
			ModelAndView view = controller.onSubmit(request, null, user, null);
/*
			try {
				when(service.isAuthorized(user)).thenThrow(new InvalidCredentialsException());
			} catch (InvalidCredentialsException e1) {
				fail("here");
			}
		*/	
			assertEquals("logonForm", view.getViewName());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Bad");
		}
		

	}	
}
