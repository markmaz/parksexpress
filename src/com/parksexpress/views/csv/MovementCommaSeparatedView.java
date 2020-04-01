package com.parksexpress.views.csv;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.parksexpress.domain.item.MovementItem;

public class MovementCommaSeparatedView extends AbstractCommaSeparatedView {
	public MovementCommaSeparatedView(){}
	
	@SuppressWarnings("unchecked")
	@Override
	public void renderData(Map model, PrintWriter writer) {
		writer.println("Item Number, Pack, Size, Description, Carton UPC, Retail UPC, Units, Cost Amount, " +
				"SRP Amount, Profit %, Profit $, Vendor Number");
		final List<MovementItem> list = (List<MovementItem>)model.get(AbstractCommaSeparatedView.DATA);
		
		for(MovementItem item : list){
			final StringBuffer row = new StringBuffer("'" + item.getCheckDigitItemNumber() + "','");
			row.append(item.getPack() + "','");
			row.append(item.getSize() + "','");
			row.append(item.getDescription() + "','");
			row.append(item.getCartonUPCNumber() + "','");
			row.append(item.getRetailUPCNumber() + "',");
			row.append(item.getFullCase() + ",");
			row.append(item.getExtendedCostAmount() + ",");
			row.append(item.getSrp() + ",");
			row.append(item.getProfitPercent() + ",");
			row.append(item.getProfitDollars() + ",'");
			row.append(item.getVendorNumber() + "'");
			
			writer.println(row.toString());
		}
	}
}
