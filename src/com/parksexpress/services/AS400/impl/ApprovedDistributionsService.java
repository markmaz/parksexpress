package com.parksexpress.services.AS400.impl;

import java.util.List;

import com.parksexpress.dao.ItemDAO;
import com.parksexpress.domain.Chain;
import com.parksexpress.domain.item.ApprovedDistributionsItem;

public class ApprovedDistributionsService extends ItemServiceAdapter {
	private ItemDAO itemDAO;
	
	public ApprovedDistributionsService(){}
	
	public void setItemDAO(ItemDAO itemDAO){
		this.itemDAO = itemDAO;
	}
	
	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String itemNumber) {
		return this.itemDAO.getApprovedDistributions(storeNumber, itemNumber);
	}
	
    @Override
    public List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String startDate, String endDate) {
    	return this.itemDAO.getApprovedDistributions(storeNumber, startDate, endDate);
    }
	
	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String itemNumber, String startDate, 
			String endDate) {
		return this.itemDAO.getApprovedDistributions(storeNumber, itemNumber, startDate, endDate);
	}

	public List<ApprovedDistributionsItem> getApprovedDistributions(Chain chain, String start, String end) {
		return this.itemDAO.getApprovedDistributions(chain, start, end);
	}

	public List<ApprovedDistributionsItem> getApprovedDistributions(Chain chain, String item) {
		return this.itemDAO.getApprovedDistributions(chain, item);
	}

	public List<ApprovedDistributionsItem> getApprovedDistributions(Chain chain, String item, String start, String end) {
		return this.itemDAO.getApprovedDistributions(chain, item, start, end);
	}
}
