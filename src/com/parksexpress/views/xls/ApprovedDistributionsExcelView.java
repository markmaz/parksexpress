package com.parksexpress.views.xls;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import com.parksexpress.domain.item.ApprovedDistributionsItem;

public class ApprovedDistributionsExcelView extends AbstractExcelView {
	private boolean isChain = false;
	
	public ApprovedDistributionsExcelView(boolean isChain) {
		this.isChain = isChain;
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void renderData(Map model, Workbook workBook) {
		Sheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName("Approved Distributions"));
		Row header = sheet.createRow(0);
		
		if(this.isChain){
			createCell("Item #", header, (short) 0);
			createCell("Pack", header, (short) 1);
			createCell("Size", header, (short) 2);
			createCell("Description", header, (short) 3);
			createCell("Carton UPC", header, (short) 4);
			createCell("Retail UPC", header, (short) 5);
			createCell("Quantity", header, (short) 6);
			createCell("Store", header, (short) 7);
			createCell("Ship Date", header, (short) 8);
		}else{
			createCell("Item #", header, (short) 0);
			createCell("Pack", header, (short) 1);
			createCell("Size", header, (short) 2);
			createCell("Description", header, (short) 3);
			createCell("Carton UPC", header, (short) 4);
			createCell("Retail UPC", header, (short) 5);
			createCell("Quantity", header, (short) 6);
			createCell("Price", header, (short) 7);
			createCell("Retail", header, (short) 8);
			createCell("Ship Date", header, (short) 9);
			createCell("Inventory Level", header, (short) 10);
		}
		
		final List<ApprovedDistributionsItem> items = (List<ApprovedDistributionsItem>) model.get(AbstractExcelView.DATA);
		int i = 1;
		
		for(ApprovedDistributionsItem item : items){
			Row row = sheet.createRow(i);
			if(this.isChain){
				createCell(item.getCheckDigitItemNumber(), row, (short) 0);
				createCell(item.getPack(), row, (short) 1);
				createCell(item.getSize(), row, (short) 2);
				createCell(item.getDescription(), row, (short) 3);
				createCell(item.getCartonUPCNumber(), row, (short) 4);
				createCell(item.getRetailUPCNumber(), row, (short) 5);
				createCell(item.getQuantity(), row, (short) 6);
				createCell(item.getStoreName(), row, (short) 7);
				createCell(item.getShipDate(), row, (short) 8);
			}else{
				createCell(item.getCheckDigitItemNumber(), row, (short) 0);
				createCell(item.getPack(), row, (short) 1);
				createCell(item.getSize(), row, (short) 2);
				createCell(item.getDescription(), row, (short) 3);
				createCell(item.getCartonUPCNumber(), row, (short) 4);
				createCell(item.getRetailUPCNumber(), row, (short) 5);
				createCell(item.getQuantity(), row, (short) 6);
				createCell(item.getPrice(), row, (short) 7);
				createCell(item.getRetail(), row, (short) 8);
				createCell(item.getShipDate(), row, (short) 9);
				createCell(item.getInventoryLevel(), row, (short) 10);
			}
			
			i++;
		}
		
	}
}