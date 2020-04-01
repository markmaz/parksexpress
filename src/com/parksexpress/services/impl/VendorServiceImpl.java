package com.parksexpress.services.impl;

import java.util.List;

import com.parksexpress.dao.VendorDAO;
import com.parksexpress.domain.Vendor;
import com.parksexpress.services.VendorService;

public class VendorServiceImpl implements VendorService {
	private VendorDAO vendorDAO;
	
	public VendorServiceImpl(){}
	
	public void setVendorDAO(VendorDAO vendorDAO){
		this.vendorDAO = vendorDAO;
	}
	
	@Override
	public List<Vendor> getAllVendors() {
		return this.vendorDAO.getAll();
	}

	@Override
	public Vendor getVendorByName(String name) {
		return this.getVendorByName(name);
	}

	@Override
	public Vendor getVendorByNumber(String number) {
		return this.vendorDAO.getVendorByNumber(number);
	}

	@Override
	public List<Vendor> findVendor(String vendorPart) {
		return this.vendorDAO.findVendor(vendorPart);
	}

}
