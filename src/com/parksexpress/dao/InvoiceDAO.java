package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.Invoice;

public interface InvoiceDAO {
	List<Invoice> getInvoicesByStore(String storeNumber);
	List<Invoice> getInvoicesByChain(String chainCode);
}
