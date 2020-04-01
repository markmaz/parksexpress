package com.parksexpress.services.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.parksexpress.dao.MovementDAO;
import com.parksexpress.domain.item.MovementItem;
import com.parksexpress.services.MovementService;

public class MovementImpl implements MovementService{
	private MovementDAO movementDAO;
	
	public MovementImpl(){}
	
	public void setMovementDAO(MovementDAO movementDAO){
		this.movementDAO = movementDAO;
	}
	
	@Override
	public List<MovementItem> getMovement(String storeNumber, String endDate, String startDate, String priceBookCode) throws Exception {
		if(StringUtils.isBlank(priceBookCode) || priceBookCode.equalsIgnoreCase("-1")){
			return this.movementDAO.getMovement(startDate, endDate, storeNumber);
		}else{
			return this.movementDAO.getMovement(startDate, endDate, priceBookCode, storeNumber);
		}
	}

}
