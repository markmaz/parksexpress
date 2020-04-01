package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.PriceBookClass;
import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.Vendor;
import com.parksexpress.domain.item.Item;
import com.parksexpress.domain.item.ReverseLookupItem;

public interface ReverseLookupDAO {
	List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] stores, boolean isSummary, int sortOrder);
	List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] stores, Vendor vendor, boolean isSummary, int sortOrder);
	List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] stores, PriceBookClass clazz, boolean isSummary, int sortOrder);
	List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] stores, PriceBookHeader header, boolean isSummary, int sortOrder);
	List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] stores, PriceBookFamily family, boolean isSummary, int sortOrder);
	List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] stores, Item item, boolean isSummary, int sortOrder);
	List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] stores, String brand, boolean isSummary, int sortOrder);
	List<ReverseLookupItem> lookUp(String startDate, String endDate,
			String[] stores, List<Item> items, boolean isSummary, int sortOrder);
}
