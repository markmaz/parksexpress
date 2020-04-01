package com.parksexpress.services.AS400.impl;

import java.util.List;

import com.parksexpress.dao.ItemDAO;
import com.parksexpress.domain.item.AllowanceItem;
import com.parksexpress.domain.item.FuturePriceChangeItem;
import com.parksexpress.domain.item.Item;
import com.parksexpress.domain.item.PriceChangeItem;

public class AS400ItemService extends ItemServiceAdapter {
	private ItemDAO itemDAO;

	public AS400ItemService(){}
	
	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	@Override
	public List<AllowanceItem> getAllowances(String startDate, String endDate,
			String number, String type) {
		return this.itemDAO.getAllowances(startDate, endDate, number, type);
	}
	
	@Override
	public List<Item> getNewItems(String startDate, String endDate,
			String number, String type) {
		return this.itemDAO.getNewItems(startDate, endDate, number, type);
	}
	
	@Override
	public List<Item> getDiscontinuedItems(String startDate, String endDate,
			String number, String type, String orderGuide) {
		return this.itemDAO.getDiscontinuedItems(startDate, endDate, number, type, orderGuide);
	}
	
	@Override
	public List<Item> getSuspendedItems(String orderGuideNumber) {
		return this.itemDAO.getSuspendedItems(orderGuideNumber);
	}
	
	@Override
	public List<PriceChangeItem> getPriceChangeReport(String startDate,
			String endDate, String storeNumber, String code, String codeType, String orderGuideNumber) {
		return this.itemDAO.getPriceChanges(startDate, endDate, code, codeType, storeNumber, orderGuideNumber);
	}
	
	@Override
	public List<FuturePriceChangeItem> getFuturePriceChangeReport(String orderGuideNumber) {
		return this.itemDAO.getFuturePriceChanges(orderGuideNumber);
	}
	
	@Override
	public List<String> getAllBrands() {
		return this.itemDAO.getAllBrands();
	}
	
	@Override
	public Item getItem(String itemNumber) {
		return this.itemDAO.getItem(itemNumber);
	}
}
