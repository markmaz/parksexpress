package com.parksexpress.services;

import java.util.List;

import com.parksexpress.domain.Vendor;

public interface VendorService {
	List<Vendor> getAllVendors();
	Vendor getVendorByName(String name);
	Vendor getVendorByNumber(String number);
	List<Vendor> findVendor(String vendorPart);
}
