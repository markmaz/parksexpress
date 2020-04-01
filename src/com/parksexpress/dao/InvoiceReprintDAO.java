package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.InvoiceReprint;

public interface InvoiceReprintDAO {
	List<InvoiceReprint> getInvoicesForStore(String storeNumber, String startDate, String endDate);
	List<InvoiceReprint> getInvoicesForChain(String chainCode, String startDate, String endDate);
}
