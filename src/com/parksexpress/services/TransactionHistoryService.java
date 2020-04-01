package com.parksexpress.services;

import java.util.List;

import com.parksexpress.domain.item.HistoryItem;

public interface TransactionHistoryService {
	List<HistoryItem> getTransactionHistory(String date, String chainCode, long userID);
	List<HistoryItem> getTransactionHistory(String startDate, int limit, String chainCode, long userID);
	List<HistoryItem> getTransactionHistory(String startDate, String endDate, String chainCode, long userID);
	List<HistoryItem> getTransactionHistory(String date, String chainCode);
	boolean addTransaction(String date, String chainCode, String details, long userID);
}
