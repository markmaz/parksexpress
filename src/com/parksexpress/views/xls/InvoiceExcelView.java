package com.parksexpress.views.xls;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import com.parksexpress.domain.Invoice;

public class InvoiceExcelView extends AbstractExcelView {
	public InvoiceExcelView(){}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void renderData(Map model, Workbook workBook) {
		Sheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName("Invoice"));
		Row header = sheet.createRow(0);
		
		createCell("Date", header, (short)0);
		createCell("Invoice #", header, (short)1);
		createCell("Type", header, (short)2);
		createCell("Amount Due", header, (short)3);
		createCell("Invoce Amount", header, (short)4);
		createCell("Payment Amount", header, (short)5);
		createCell("Adjustment Amount", header, (short)6);
		createCell("Balance", header, (short)7);
		
		final List<Invoice> list = (List<Invoice>)model.get(AbstractExcelView.DATA);
		int cnt = 1;
		
		for(Invoice invoice : list){
			Row row = sheet.createRow(cnt);
			createCell(invoice.getDate(), row, (short)0);
			createCell(invoice.getNumber(), row, (short)1);
			createCell(invoice.getType(), row, (short)2);
			createCell(0, row, (short)3);
			createCell(invoice.getInvoiceAmount(), row, (short)4);
			createCell(invoice.getPaymentAmount(), row, (short)5);
			createCell(invoice.getAdjustmentAmount(), row, (short)6);
			createCell(invoice.getBalance(), row, (short)7);
			cnt++;
		}
	}

}
