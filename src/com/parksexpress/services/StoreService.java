package com.parksexpress.services;

import java.util.List;

import com.parksexpress.domain.Store;

public interface StoreService {
	List<Store> search(final String criteria);
	Store getStore(final String storeNumber);
	List<Store> getStoresForUser(final long storeID, final boolean withZones);
	List<Store> getStoresInChain(final String chainCode, final boolean withZones);
	List<Store> getStoresForZone(final String srpBook);
}
