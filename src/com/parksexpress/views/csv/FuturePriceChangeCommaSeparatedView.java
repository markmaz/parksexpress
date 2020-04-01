package com.parksexpress.views.csv;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.item.FuturePriceChangeItem;
import com.parksexpress.views.pdf.AbstractParksexpressPrintView;

public class FuturePriceChangeCommaSeparatedView extends AbstractCommaSeparatedView {
	public FuturePriceChangeCommaSeparatedView(){}
	
	@SuppressWarnings("unchecked")
	@Override
	public void renderData(Map model, PrintWriter writer) {
		writer.println("Header, Item #, Pack, Size, Description, Retail UPC, Carton UPC, " +
				"Old Price, New Price, Diff, Effective Date");
		
		final Map<PriceBookHeader, List<FuturePriceChangeItem>> convertedMap = 
			(Map<PriceBookHeader, List<FuturePriceChangeItem>>)model.get(AbstractParksexpressPrintView.DATA);
		
		for(Map.Entry<PriceBookHeader, List<FuturePriceChangeItem>> entry : convertedMap.entrySet()){			
			for(FuturePriceChangeItem item : entry.getValue()){
				final StringBuffer buffer = new StringBuffer("'" + entry.getKey().getDescription().trim() + "','");
				buffer.append(item.getCheckDigitItemNumber().trim() + ",");
				buffer.append(item.getPack() + "','");
				buffer.append(item.getSize()+ "','");
				buffer.append(item.getDescription().trim() + "','");
				buffer.append(item.getRetailUPCNumber() + "','");
				buffer.append(item.getCartonUPCNumber() + "',");
				buffer.append(item.getOldPrice() + ",");
				buffer.append(item.getNewPrice() + ",");
				buffer.append(item.getDifference() + ",'");
				buffer.append(item.getEffectiveDate() + "',");
				writer.println(buffer.toString());
			}
		}
	}

}
