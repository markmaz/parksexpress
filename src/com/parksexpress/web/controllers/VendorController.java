package com.parksexpress.web.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.Vendor;
import com.parksexpress.services.VendorService;

public class VendorController extends ParksController {
	private VendorService vendorService;
	
	public void setVendorService(VendorService vendorService){
		this.vendorService = vendorService;
	}
	
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response){
		String criteria = request.getParameter("vendor");
		List<Vendor> vendors = this.vendorService.findVendor(criteria);
		return new ModelAndView("vendorSearchResults", "vendors", vendors);
	}
}
