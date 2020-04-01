package com.parksexpress.services.impl;

import java.util.List;

import com.parksexpress.dao.StoreDAO;
import com.parksexpress.domain.Store;
import com.parksexpress.services.StoreService;

public class StoreServiceImpl implements StoreService {
	private StoreDAO storeDAO;
	
	public StoreServiceImpl(){}
	
	public void setStoreDAO(final StoreDAO storeDAO){
		this.storeDAO = storeDAO;
	}
	
	public Store getStore(final String storeNumber) {
		return this.storeDAO.getStore(storeNumber);
	}

	public List<Store> search(final String criteria) {
		return this.storeDAO.search(criteria);
	}

	public List<Store> getStoresForUser(final long userID, final boolean withZones) {
		return this.storeDAO.getStoresForUser(new Long(userID).longValue(), withZones);
	}

	public List<Store> getStoresInChain(final String chainCode, final boolean withZones) {
		return this.storeDAO.getStoresInChain(chainCode, withZones);
	}

	public List<Store> getStoresForZone(final String srpBook) {
		return this.storeDAO.getStoresForZone(srpBook);
	}
}