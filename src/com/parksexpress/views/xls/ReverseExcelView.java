package com.parksexpress.views.xls;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import com.parksexpress.domain.item.ReverseLookupItem;

public class ReverseExcelView extends AbstractExcelView{
	public static final String TOTAL_RETAIL = "TOTAL_RETAIL";
	public static final String TOTAL_UNITS = "TOTAL_UNITS";
	public static final String TOTAL_COST = "TOTAL_COST";

	public ReverseExcelView() {}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void renderData(Map model, Workbook workBook) {
		Sheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName("ReverseLookUp"));
		Row header = sheet.createRow(0);
	
		createCell("Item #", header, (short)0);
		createCell("Vendor Name", header, (short)1);
		createCell("Pack", header, (short)2);
		createCell("Size", header, (short)3);
		createCell("Description", header, (short)4);
		createCell("Carton UPC", header, (short)5);
		createCell("Retail UPC", header, (short)6);
		createCell("Quantity", header, (short)7);
		createCell("Cost", header, (short)8);
		createCell("Retail", header, (short)9);
		createCell("PFT %", header, (short)10);
		
		final List<ReverseLookupItem> list = (List<ReverseLookupItem>)model.get(AbstractExcelView.DATA);
		int i = 1;
		
		for(ReverseLookupItem item : list){
			Row row = sheet.createRow(i);
			
			createCell(item.getCheckDigitItemNumber(), row, (short)0);
			createCell(item.getVendorName(), row, (short)1);
			createCell(item.getPack(), row, (short)2);
			createCell(item.getSize(), row, (short)3);
			createCell(item.getDescription(), row, (short)4);
			createCell(item.getCartonUPCNumber(), row, (short)5);
			createCell(item.getRetailUPCNumber(), row, (short)6);
			createCell(item.getQuantity(), row, (short)7);
			createCell(item.getCostAmount(), row, (short)8);
			createCell(item.getSrpAmount(), row, (short)9);
			createCell(item.getProfitPercent(), row, (short)10);
			
			i++;
		}
		
		createCell("", sheet.createRow(i), (short)0);
		createCell("", sheet.createRow(i), (short)1);
		createCell("", sheet.createRow(i), (short)2);
		createCell("", sheet.createRow(i), (short)3);
		createCell("", sheet.createRow(i), (short)4);
		createCell("", sheet.createRow(i), (short)5);
		createCell("", sheet.createRow(i), (short)6);
		createCell(model.get(ReverseExcelView.TOTAL_UNITS) + "", sheet.createRow(i), (short)7);
		createCell(model.get(ReverseExcelView.TOTAL_COST) + "", sheet.createRow(i), (short)8);
		createCell(model.get(ReverseExcelView.TOTAL_RETAIL) + "", sheet.createRow(i), (short)9);
		createCell("", sheet.createRow(i), (short)10);
	}

}
