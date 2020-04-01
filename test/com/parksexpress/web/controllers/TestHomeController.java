package com.parksexpress.web.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class TestHomeController {

	@Test
	public void testNoAuthenticatedUserHandleRequestInternalHttpServletRequestHttpServletResponse() {
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HomeController homeController = new HomeController();
		HttpSession session = mock(HttpSession.class);
		
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("authenticatedUser")).thenReturn(null);
		try {
			ModelAndView view = homeController.handleRequestInternal(request, response);
			assertEquals("logonForm", view.getViewName());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testAuthenticatedUserInSystem(){
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HomeController homeController = new HomeController();
		HttpSession session = mock(HttpSession.class);
		
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("authenticatedUser")).thenReturn("");
		try {
			ModelAndView view = homeController.handleRequestInternal(request, response);
			assertEquals("homePage", view.getViewName());
		} catch (Exception e) {
			fail(e.getMessage());
		}		
	}
}
