package com.parksexpress.services.impl;

import java.util.List;

import com.parksexpress.dao.ReverseLookupDAO;
import com.parksexpress.domain.PriceBookClass;
import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.Vendor;
import com.parksexpress.domain.item.Item;
import com.parksexpress.domain.item.ReverseLookupItem;
import com.parksexpress.services.ReverseLookupService;

public class ReverseLookupServiceImpl implements ReverseLookupService {
	private ReverseLookupDAO reverseLookupDAO;
	
	public ReverseLookupServiceImpl(){}
	
	public void setReverseLookupDAO(ReverseLookupDAO reverseLookupDAO){
		this.reverseLookupDAO = reverseLookupDAO;
	}
	
	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String stores[], boolean isSummary, int sortOrder) {
		return this.reverseLookupDAO.lookUp(startDate, endDate, stores, isSummary, sortOrder);
	}

	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String stores[], Vendor vendor, boolean isSummary, int sortOrder) {
		return this.reverseLookupDAO.lookUp(startDate, endDate, stores, vendor, isSummary, sortOrder);
	}

	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String stores[], PriceBookClass clazz, boolean isSummary, int sortOrder) {
		return this.reverseLookupDAO.lookUp(startDate, endDate, stores, clazz, isSummary, sortOrder);
	}

	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String stores[], PriceBookHeader header, 
			boolean isSummary, int sortOrder) {
		return this.reverseLookupDAO.lookUp(startDate, endDate, stores, header, isSummary, sortOrder);
	}

	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String stores[], PriceBookFamily family, 
			boolean isSummary, int sortOrder) {
		return this.reverseLookupDAO.lookUp(startDate, endDate, stores, family, isSummary, sortOrder);
	}

	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String stores[], Item item, boolean isSummary, int sortOrder) {
		return this.reverseLookupDAO.lookUp(startDate, endDate, stores, item, isSummary, sortOrder);
	}

	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String stores[], String brand, boolean isSummary, int sortOrder) {
		return this.reverseLookupDAO.lookUp(startDate, endDate, stores, brand, isSummary, sortOrder);
	}

	@Override
	public List<ReverseLookupItem> lookUp(String startDate, String endDate,
			String[] stores, List<Item> items, boolean isSummary, int sortOrder) {
		return this.reverseLookupDAO.lookUp(startDate, endDate, stores, items, isSummary, sortOrder);
	}
}