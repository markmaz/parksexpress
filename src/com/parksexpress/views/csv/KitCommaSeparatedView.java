package com.parksexpress.views.csv;

import java.io.PrintWriter;
import java.util.Map;

import com.parksexpress.domain.Kit;
import com.parksexpress.domain.item.KitItem;

public class KitCommaSeparatedView extends AbstractCommaSeparatedView {
	public KitCommaSeparatedView(){}
	
	@SuppressWarnings("unchecked")
	@Override
	public void renderData(Map model, PrintWriter writer) {
		writer.println("Kit Number, Kit Description, Component Number, Pack, Size, Description, Price, Quantity");

		final Kit kit = (Kit) model.get(AbstractCommaSeparatedView.DATA);
		writer.println("'" + kit.getCheckDigitItemNumber() + "','" + kit.getDescription() + "', , , , , ,");
		
		for(KitItem item : kit.getComponents()){
			writer.println(", ,'" + item.getCheckDigitItemNumber() + "','" + item.getPack() + "','" + item.getSize() + "','" + 
					item.getDescription() + "'," + item.getFormattedPrice() + "," + item.getQuantity());
		}
	}
}
