package com.parksexpress.services;

import java.util.List;

import com.parksexpress.domain.item.MovementItem;

public interface MovementService {
	List<MovementItem> getMovement(String storeNumber, String endDate, String startDate, String priceBookCode) throws Exception;
}
