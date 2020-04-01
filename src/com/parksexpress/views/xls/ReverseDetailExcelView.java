package com.parksexpress.views.xls;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import com.parksexpress.domain.item.ReverseLookupItem;

public class ReverseDetailExcelView extends AbstractExcelView {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void renderData(Map model, Workbook workBook) {
		Sheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName("ReverseLookUp"));
		Row header = sheet.createRow(0);
	
		createCell("Store #", header, (short)0);
		createCell("Item #", header, (short)1);
		createCell("Vendor Name", header, (short)2);
		createCell("Pack", header, (short)3);
		createCell("Size", header, (short)4);
		createCell("Description", header, (short)5);
		createCell("Carton UPC", header, (short)6);
		createCell("Retail UPC", header, (short)7);
		createCell("Quantity", header, (short)8);
		createCell("Cost", header, (short)9);
		createCell("Retail", header, (short)10);
		createCell("PFT %", header, (short)11);
		
		final List<ReverseLookupItem> list = (List<ReverseLookupItem>)model.get(AbstractExcelView.DATA);
		int i = 1;
		
		for(ReverseLookupItem item : list){
			Row row = sheet.createRow(i);
			createCell(item.getCustomerNumber(), row, (short)0);
			createCell(item.getCheckDigitItemNumber(), row, (short)1);
			createCell(item.getVendorName(), row, (short)2);
			createCell(item.getPack(), row, (short)3);
			createCell(item.getSize(), row, (short)4);
			createCell(item.getDescription(), row, (short)5);
			createCell(item.getCartonUPCNumber(), row, (short)6);
			createCell(item.getRetailUPCNumber(), row, (short)7);
			createCell(item.getQuantity(), row, (short)8);
			createCell(item.getCostAmount(), row, (short)9);
			createCell(item.getSrpAmount(), row, (short)10);
			createCell(item.getProfitPercent(), row, (short)11);
			
			i++;
		}
	}
}
