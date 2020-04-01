package com.parksexpress.views.csv;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.parksexpress.domain.item.ApprovedDistributionsItem;

public class ApprovedDistributionsCommaSeparatedView extends AbstractCommaSeparatedView {
	private boolean isChain = false;
	
	public ApprovedDistributionsCommaSeparatedView(boolean isChain) {
		this.isChain = isChain;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void renderData(Map model, PrintWriter writer) {
		if(this.isChain){			
			writer.println("Item #, Pack, Size, Description, Carton UPC, Retail UPC, Quantity, Store, " +
					"Ship Date");
		}else{
			writer.println("Item #, Pack, Size, Description, Carton UPC, Retail UPC, Quantity, Price, " +
			"Retail, Ship Date, Inventory Level");			
		}
		
		final List<ApprovedDistributionsItem> items = (List<ApprovedDistributionsItem>) model.get(AbstractCommaSeparatedView.DATA);
		for(ApprovedDistributionsItem item : items){
			if(this.isChain){
				writer.println("'" + item.getCheckDigitItemNumber() + "','" + item.getPack() + "','" + item.getSize() + "','" +
						item.getDescription() + "','" + item.getCartonUPCNumber() + "','" + 
						item.getRetailUPCNumber() + "'," +
						item.getQuantity() + ",'" + item.getStoreName() + "','" + 
						item.getShipDate() + "'");
			}else{
				writer.println("'" + item.getCheckDigitItemNumber() + "','" + item.getPack() + "','" + item.getSize() + "','" +
						item.getDescription() + "','" + item.getCartonUPCNumber() + "','" + 
						item.getRetailUPCNumber() + "'," +item.getQuantity() + "," + 
						item.getPrice().toString() + "," + item.getRetail().toString() + ",'" +
						item.getShipDate() + "'," + item.getInventoryLevel());
			}
		}
		
	}

}
