package com.parksexpress.views.csv;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.parksexpress.domain.item.Item;

public class DefaultHotSheetCommaSeparatedView extends AbstractCommaSeparatedView {
	public DefaultHotSheetCommaSeparatedView(){}
	
	@SuppressWarnings("unchecked")
	@Override
	public void renderData(Map model, PrintWriter writer) {
		writer.println("Item #, Pack, Size, Description, Carton UPC, Retail UPC, Regular Price");
		
		final List<Item> items = (List<Item>) model.get(AbstractCommaSeparatedView.DATA);
		for(Item item : items){
			writer.println("'" + item.getCheckDigitItemNumber() + "','" + item.getSize() + "','" + item.getDescription() + "','" 
					+ item.getCartonUPCNumber() + "','" + item.getRetailUPCNumber() + "'," + item.getMarketCost());
		}
	}
}
