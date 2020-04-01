package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.Vendor;

public interface VendorDAO {
	List<Vendor> getAll();
	Vendor getVendorByNumber(String number);
	Vendor getVendorByName(String name);
	List<Vendor> findVendor(String vendorPart);
}
