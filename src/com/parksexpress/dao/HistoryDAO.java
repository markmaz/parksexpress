package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.item.HistoryItem;

public interface HistoryDAO {
	List<HistoryItem> getHistoryItems(String date, long userID, String chainCode);
	List<HistoryItem> getHistoryItems(String startDate, int limit, long userID, String chainCode);
	boolean addHistoryItem(String date, String details, long userID, String chainCode);
	List<HistoryItem> getHistoryItems(String startDate, String endDate, long userID, String chainCode);
	List<HistoryItem> getHistoryItems(String date, String chainCode);
}
