package com.parksexpress.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.EmailAddress;
import com.parksexpress.domain.User;
import com.parksexpress.services.EmailService;

public class EmaillAddressController extends ParksController {
	private EmailService emailService;
	
	public void setEmailService(EmailService service){
		this.emailService = service;
	}
	
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response){
		User user = this.getUser(request);
		String chain = user.getStores().get(0).getChainCode();
		List<EmailAddress> addresses = this.emailService.getEmailsByChain(chain);
		return new ModelAndView("emailAddressDisplay", "emailAddresses", addresses);
	}
	
	public ModelAndView newEmail(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("emailAddressNew");
	}
	
	public ModelAndView saveNewEmail(HttpServletRequest request, HttpServletResponse response){
		String email = request.getParameter("email");
		User user = this.getUser(request);
		String chainCode = user.getStores().get(0).getChainCode();

		this.emailService.add(email, chainCode);
		return new ModelAndView("message");
	}
	
	public ModelAndView deleteEmail(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		this.emailService.delete(Integer.parseInt(id));
		return new ModelAndView("message");
	}
	
	public ModelAndView sendAll(HttpServletRequest request, HttpServletResponse response) throws Exception{
		this.emailService.sendAll();
		return new ModelAndView("message");
	}
}
