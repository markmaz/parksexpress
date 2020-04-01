package com.parksexpress.services.impl;

import java.util.List;

import com.parksexpress.as400.util.DateChanger;
import com.parksexpress.as400.util.PDFGenerator;
import com.parksexpress.dao.InvoiceReprintDAO;
import com.parksexpress.domain.InvoiceReprint;
import com.parksexpress.services.InvoiceReprintService;

public class InvoiceReprintServiceImpl implements InvoiceReprintService{
	private PDFGenerator pdfGenerator;
	private InvoiceReprintDAO invoiceReprintDAO;
	
	public InvoiceReprintServiceImpl(){};
	
	public void setPDFGenerator(final PDFGenerator pdfGenerator){
		this.pdfGenerator = pdfGenerator;
	}
	
	public void setInvoiceReprintDAO(final InvoiceReprintDAO invoiceReprintDAO){
		this.invoiceReprintDAO = invoiceReprintDAO;
	}
	
	@Override
	public void createInvoicePDF(final String invoiceNumber, final String orderNumber, final String customerNumber) throws Exception {
		this.pdfGenerator.createPDF(invoiceNumber, customerNumber, orderNumber);
	}

	@Override
	public List<InvoiceReprint> getInvoicesForChain(final String chainCode, final String startDate, 
			final String endDate) throws Exception {
		return this.invoiceReprintDAO.getInvoicesForChain(chainCode, DateChanger.convertDateToAS400(startDate), 
				DateChanger.convertDateToAS400(endDate));
	}

	@Override
	public List<InvoiceReprint> getInvoicesForStore(final String storeNumber, final String startDate, 
			final String endDate) throws Exception {
		return this.invoiceReprintDAO.getInvoicesForStore(storeNumber, DateChanger.convertDateToAS400(startDate), 
				DateChanger.convertDateToAS400(endDate));
	}
}
