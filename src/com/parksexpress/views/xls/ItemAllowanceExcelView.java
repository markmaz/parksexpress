package com.parksexpress.views.xls;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import com.parksexpress.domain.item.AllowanceItem;

public class ItemAllowanceExcelView extends AbstractExcelView {
	public ItemAllowanceExcelView(){}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void renderData(Map model, Workbook workBook) {
		Sheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName("ItemALlowance"));
		Row header = sheet.createRow(0);
		
		createCell("Item #", header, (short)0);
		createCell("Unit", header, (short)1);
		createCell("Description", header, (short)2);
		createCell("Carton UPC", header, (short)3);
		createCell("Retail UPC", header, (short)4);
		createCell("Regular $", header, (short)5);
		createCell("Allowance", header, (short)6);
		createCell("Special $", header, (short)7);
		createCell("Start Date", header, (short)8);
		createCell("End Date", header, (short)9);
		
		final List<AllowanceItem> items = (List<AllowanceItem>) model.get(AbstractExcelView.DATA);
		int i = 1;
		
		for(AllowanceItem item : items){
			Row row = sheet.createRow(i);
			createCell(item.getCheckDigitItemNumber(), row, (short)0);
			createCell(item.getSize(), row, (short)1);
			createCell(item.getDescription(), row, (short)2);
			createCell(item.getCartonUPCNumber(), row, (short)3);
			createCell(item.getRetailUPCNumber(), row, (short)4);
			createCell(item.getMarketCost(), row, (short)5);
			createCell(item.getCostAllowance(), row, (short)6);
			createCell(item.getSpecialPrice(), row, (short)7);
			createCell(item.getStartDate(), row, (short)8);
			createCell(item.getExpirationDate(), row, (short)9);
			i++;
		}
	}
}
