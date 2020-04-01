package com.parksexpress.views.csv;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.parksexpress.domain.Invoice;

public class InvoiceCommaSeparatedView extends AbstractCommaSeparatedView {
	public InvoiceCommaSeparatedView(){}
	
	@SuppressWarnings("unchecked")
	@Override
	public void renderData(Map model, PrintWriter writer) {
		writer.println("Date, Invoice #, Type, Amount Due, Invoice Amount, Payment Amount, Adjustment Amount, Balance");
		final List<Invoice> list = (List<Invoice>)model.get(AbstractCommaSeparatedView.DATA);
		
		for(Invoice invoice : list){
			final StringBuffer row = new StringBuffer(invoice.getDate() + ", ");
			row.append(invoice.getNumber() + ",");
			row.append(invoice.getType() + ",");
			row.append("0,");
			row.append(invoice.getInvoiceAmount() + ",");
			row.append(invoice.getPaymentAmount() + ",");
			row.append(invoice.getAdjustmentAmount() + ",");
			row.append(invoice.getBalance());
			writer.println(row.toString());
		}
	}

}
