package com.parksexpress.views.csv;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.item.PriceChangeItem;

public class PriceChangeCommaSeparatedView extends AbstractCommaSeparatedView {
	public PriceChangeCommaSeparatedView(){}
	
	@SuppressWarnings("unchecked")
	@Override
	public void renderData(Map model, PrintWriter writer) {
		writer.println("Header, Family, Item #, Description, Old Price, New Price, Diff, Old SRP, New SRP, Effective Date, F/%");
		
		final Map<PriceBookHeader, List<PriceChangeItem>> convertedMap = 
			(Map<PriceBookHeader, List<PriceChangeItem>>)model.get(AbstractCommaSeparatedView.DATA);
		
		for(Map.Entry<PriceBookHeader, List<PriceChangeItem>> entry : convertedMap.entrySet()){			
			for(PriceChangeItem item : entry.getValue()){
				final StringBuffer buffer = new StringBuffer("'" + entry.getKey().getDescription().trim() + "', '");
				buffer.append(item.getPriceBookFamilyCode().trim() + "','");
				buffer.append(item.getCheckDigitItemNumber().trim() + "','");
				buffer.append(item.getDescription().trim() + "',");
				buffer.append(item.getOldPrice() + ",");
				buffer.append(item.getNewPrice() + ",");
				buffer.append(item.getDifference() + ",");
				buffer.append(item.getOldSrp() + ",");
				buffer.append(item.getNewSrp() + ",'");
				buffer.append(item.getEffectiveDate() + "',");
				buffer.append(item.getFixedOrPercent());
				writer.println(buffer.toString());
			}
		}
	}
}