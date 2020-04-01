package com.parksexpress.services.impl;

import java.util.List;

import com.parksexpress.dao.HistoryDAO;
import com.parksexpress.domain.item.HistoryItem;
import com.parksexpress.services.TransactionHistoryService;

public class TransactionHistoryServiceImpl implements TransactionHistoryService {
	private HistoryDAO historyDAO;

	public TransactionHistoryServiceImpl(){}
	
	public void setHistoryDAO(final HistoryDAO historyDAO) {
		this.historyDAO = historyDAO;
	}

	public boolean addTransaction(final String date, final String chainCode,
			final String details, final long userID) {
		return this.historyDAO.addHistoryItem(date, details, userID, chainCode);
	}

	public List<HistoryItem> getTransactionHistory(final String date,
			final String chainCode, final long userID) {
		return this.historyDAO.getHistoryItems(date, userID, chainCode);
	}

	@Override
	public List<HistoryItem> getTransactionHistory(String startDate, int limit, String chainCode, long userID) {
		return this.historyDAO.getHistoryItems(startDate, limit, userID, chainCode);
	}

	@Override
	public List<HistoryItem> getTransactionHistory(String startDate, String endDate, String chainCode, long userID) {
		return this.historyDAO.getHistoryItems(startDate, endDate, userID, chainCode);
	}

	@Override
	public List<HistoryItem> getTransactionHistory(String date, String chainCode) {
		return this.historyDAO.getHistoryItems(date, chainCode);
	}
}
