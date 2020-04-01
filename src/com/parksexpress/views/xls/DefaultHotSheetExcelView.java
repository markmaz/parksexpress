package com.parksexpress.views.xls;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import com.parksexpress.domain.item.Item;

public class DefaultHotSheetExcelView extends AbstractExcelView {
	public DefaultHotSheetExcelView(){}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void renderData(Map model, Workbook workBook) {
		Sheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName(""));
		Row header = sheet.createRow(0);
		createCell("Item #", header, (short)0);
		createCell("Pack", header, (short)1);
		createCell("Size", header, (short)2);
		createCell("Description", header, (short)3);
		createCell("Carton UPC", header, (short)4);
		createCell("Retail UPC", header, (short)5);
		createCell("Regular Price", header, (short)6);
		
		final List<Item> items = (List<Item>) model.get(AbstractExcelView.DATA);
		int i = 1;
		
		for(Item item : items){
			Row row = sheet.createRow(i);
			createCell(item.getCheckDigitItemNumber(), row, (short)0);
			createCell(item.getPack(), row, (short)1);
			createCell(item.getSize(), row, (short)2);
			createCell(item.getDescription(), row, (short)3);
			createCell(item.getCartonUPCNumber(), row, (short)4);
			createCell(item.getRetailUPCNumber(), row, (short)5);
			createCell(item.getMarketCost(), row, (short)6);
			
			i++;
		}
	}
}
