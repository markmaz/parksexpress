package com.parksexpress.as400.util;

public interface PDFGenerator {
	void createPDF(String invoiceNumber, String customerNumber, String orderNumber) throws Exception;
}