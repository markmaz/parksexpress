package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.item.MovementItem;

public interface MovementDAO {
	List<MovementItem> getMovement(String startDate, String endDate, String priceBookCode, String storeNumber) throws Exception;
	List<MovementItem> getMovement(String startDate, String endDate, String storeNumber) throws Exception;
}
