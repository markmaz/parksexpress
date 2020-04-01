package com.parksexpress.views.xls;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import com.parksexpress.domain.item.MovementItem;

public class MovementExcelView extends AbstractExcelView {
	public MovementExcelView(){}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void renderData(Map model, Workbook workBook) {
		Sheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName("Movement"));
		Row header = sheet.createRow(0);
		
		createCell("Item Number", header, (short)0);
		createCell("Pack", header, (short)1);
		createCell("Size", header, (short)2);
		createCell("Description", header, (short)3);
		createCell("Carton UPC", header, (short)4);
		createCell("Retail UPC", header, (short)5);
		createCell("Units", header, (short)6);
		createCell("Cost Amount", header, (short)7);
		createCell("SRP Amount", header, (short)8);
		createCell("Profit %", header, (short)9);
		createCell("Profit $", header, (short)10);
		createCell("Vendor Number", header, (short)11);
		
		final List<MovementItem> list = (List<MovementItem>)model.get(AbstractExcelView.DATA);
		int i = 1;
		
		for(MovementItem item : list){
			Row row = sheet.createRow(i);
			
			createCell(item.getCheckDigitItemNumber(), row, (short)0);
			createCell(item.getPack(), row, (short)1);
			createCell(item.getSize(), row, (short)2);
			createCell(item.getDescription(), row, (short)3);
			createCell(item.getCartonUPCNumber(), row, (short)4);
			createCell(item.getRetailUPCNumber(), row, (short)5);
			createCell(item.getFullCase(), row, (short)6);
			createCell(item.getExtendedCostAmount(), row, (short)7);
			createCell(item.getSrp(), row, (short)8);
			createCell(item.getProfitPercent(), row, (short)9);
			createCell(item.getProfitDollars(), row, (short)10);
			createCell(item.getVendorNumber(), row, (short)11);

			i++;
		}
	}
}
