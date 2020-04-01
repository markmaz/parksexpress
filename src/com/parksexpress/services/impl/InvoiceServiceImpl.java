package com.parksexpress.services.impl;

import java.util.List;

import com.parksexpress.dao.InvoiceDAO;
import com.parksexpress.domain.Invoice;
import com.parksexpress.services.InvoiceService;

public class InvoiceServiceImpl implements InvoiceService{
	private InvoiceDAO invoiceDAO;
	
	public InvoiceServiceImpl(){}
	
	@Override
	public List<Invoice> getInvoicesByChain(String chainCode) throws Exception {
		return this.invoiceDAO.getInvoicesByChain(chainCode);
	}

	@Override
	public List<Invoice> getInvoicesByStore(String storeNumber) throws Exception{
		return this.invoiceDAO.getInvoicesByStore(storeNumber);
	}

	public void setInvoiceDAO(InvoiceDAO invoiceDAO){
		this.invoiceDAO = invoiceDAO;
	}
}
