package com.parksexpress.services;

import java.util.List;

import com.parksexpress.domain.Invoice;

public interface InvoiceService {
	List<Invoice> getInvoicesByChain(String chainCode) throws Exception;
	List<Invoice> getInvoicesByStore(String storeNumber) throws Exception;
}
