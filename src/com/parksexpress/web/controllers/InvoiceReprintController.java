package com.parksexpress.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.InvoiceReprint;
import com.parksexpress.domain.User;
import com.parksexpress.services.InvoiceReprintService;
import com.parksexpress.util.DateUtil;

public class InvoiceReprintController extends ParksController{
	private InvoiceReprintService invoiceReprintService;
	
	public InvoiceReprintController(){}
	
	public void setInvoiceReprintService(final InvoiceReprintService invoiceReprintService){
		this.invoiceReprintService = invoiceReprintService;
	}
	
	public ModelAndView show(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		final ModelAndView view = new ModelAndView("invoiceReprintShow", "stores", user.getStoresWithoutZones());
		view.addObject("startDate", DateUtil.getDefaultStartDate(2));
		view.addObject("endDate", DateUtil.getDefaultEndDate(0));
		return view;
	}
	
	public ModelAndView find(final HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String startDate = request.getParameter("start");
		final String endDate = request.getParameter("end");
		final String store = request.getParameter("code");
		
		if(this.isValidStoreNumber(this.getUser(request), store)){
    		final List<InvoiceReprint> invoices = this.invoiceReprintService.getInvoicesForStore(store, startDate, endDate);
    		return new ModelAndView("invoiceReprintData", "invoices", invoices);
		}else{
			return new ModelAndView("unauthorizedUserError");
		}
	}
	
	public ModelAndView create(final HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String invoiceNumber = request.getParameter("invoice");
		final String orderNumber = request.getParameter("order");
		final String customerNumber = request.getParameter("store");
		
		this.invoiceReprintService.createInvoicePDF(invoiceNumber, orderNumber, customerNumber);
		return new ModelAndView("message", "msg", "success");
	}
	
}
