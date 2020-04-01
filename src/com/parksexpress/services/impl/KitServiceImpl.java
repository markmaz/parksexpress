package com.parksexpress.services.impl;

import java.util.List;

import com.parksexpress.dao.KitDAO;
import com.parksexpress.domain.Kit;
import com.parksexpress.services.KitService;

public class KitServiceImpl implements KitService {
	private KitDAO kitDAO;
	
	public KitServiceImpl(){}
	
	public void setKitDAO(KitDAO kitDAO){
		this.kitDAO = kitDAO;
	}
	
	@Override
	public Kit getKit(String kitNumber) {
		return this.kitDAO.getKit(kitNumber);
	}

	@Override
	public List<Kit> search(String criteria) {
		return this.kitDAO.search(criteria);
	}

}
