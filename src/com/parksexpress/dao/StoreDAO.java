package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.Store;
import com.parksexpress.domain.Zone;

public interface StoreDAO {
	List<Store> search(String criteria);
	Store getStore(String storeNumber);
	List<Store> getStoresForUser(long id, boolean withZones);
	List<Store> getStoresInChain(String chainCode, boolean withZones);
	List<Store> getStoresForZone(String zone);
	List<Zone> getZones(String chainCode);
}
