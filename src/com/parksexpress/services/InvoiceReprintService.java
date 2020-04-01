package com.parksexpress.services;

import java.util.List;

import com.parksexpress.domain.InvoiceReprint;

public interface InvoiceReprintService {
	void createInvoicePDF(String invoiceNumber, String orderNumber, String customerNumber) throws Exception;
	List<InvoiceReprint> getInvoicesForStore(String storeNumber, String startDate, String endDate) throws Exception;
	List<InvoiceReprint> getInvoicesForChain(String chainCode, String startDate, String endDate) throws Exception;
}
