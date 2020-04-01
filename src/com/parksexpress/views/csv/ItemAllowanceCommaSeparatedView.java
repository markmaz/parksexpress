package com.parksexpress.views.csv;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.parksexpress.domain.item.AllowanceItem;

public class ItemAllowanceCommaSeparatedView extends AbstractCommaSeparatedView {
	public ItemAllowanceCommaSeparatedView(){}
	
	@SuppressWarnings("unchecked")
	@Override
	public void renderData(Map model, PrintWriter writer) {
		writer.println("Item #, Unit, Description, Carton UPC, Retail UPC, Regular $, Allowance, Special $, Start Date, End Date");
		
		final List<AllowanceItem> items = (List<AllowanceItem>) model.get(AbstractCommaSeparatedView.DATA);
		for(AllowanceItem item : items){
			writer.println("'" + item.getCheckDigitItemNumber() + "','" + item.getSize() + "','" + item.getDescription() + "'," 
					+ item.getCartonUPCNumber() + "," + item.getRetailUPCNumber() + "," + item.getMarketCost() + ","
					+ item.getCostAllowance() + "," + item.getSpecialPrice() +  ",'" + item.getStartDate() + "','"
					+ item.getExpirationDate() + "'");
		}
	}
}
