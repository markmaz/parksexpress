package com.parksexpress.views.csv;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.parksexpress.domain.item.ReverseLookupItem;

public class ReverseLookupCommaSeparatedView extends AbstractCommaSeparatedView{
	public ReverseLookupCommaSeparatedView() {}

	@SuppressWarnings("unchecked")
	@Override
	public void renderData(Map model, PrintWriter writer) {
		writer.println("Item #, Vendor Name, Pack, Size, Description, Carton UPC, Retail UPC, Quantity, Cost, Retail, PFT%");
		final List<ReverseLookupItem> list = (List<ReverseLookupItem>)model.get(AbstractCommaSeparatedView.DATA);
		
		for(ReverseLookupItem item : list){
			final StringBuffer row = new StringBuffer("'" + item.getCheckDigitItemNumber() + "', '");
			row.append(item.getVendorName() + "', '");
			row.append(item.getPack() + "', '");
			row.append(item.getSize() + "', '");
			row.append(item.getDescription() + "', '");
			row.append(item.getCartonUPCNumber() + "', '");
			row.append(item.getRetailUPCNumber() + "', ");
			row.append(item.getQuantity() + ",");
			row.append(item.getCostAmount() + ",");
			row.append(item.getSrpAmount() + ",");
			row.append(item.getProfitPercent());
			writer.println(row.toString());
		}
	}

}
