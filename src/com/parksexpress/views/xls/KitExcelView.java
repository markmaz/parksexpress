package com.parksexpress.views.xls;

import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import com.parksexpress.domain.Kit;
import com.parksexpress.domain.item.KitItem;

public class KitExcelView extends AbstractExcelView {
	public KitExcelView(){}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void renderData(Map model, Workbook workBook) {
		Sheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName("Kit"));
		Row header = sheet.createRow(0);
		
		createCell("Kit Number", header, (short)0);
		createCell("Kit Description", header, (short)1);
		createCell("Component Number", header, (short)2);
		createCell("Pack", header, (short)3);
		createCell("Size", header, (short)4);
		createCell("Description", header, (short)5);
		createCell("Price", header, (short)6);
		createCell("Quantity", header, (short)7);

		int i = 1;
		final Kit kit = (Kit) model.get(AbstractExcelView.DATA);
		Row r = sheet.createRow(i);
		createCell(kit.getCheckDigitItemNumber(), r, (short)0);
		createCell(kit.getDescription(), r, (short)1);
		i++;
		
		for(KitItem item : kit.getComponents()){
			Row row = sheet.createRow(i);
			createCell(item.getCheckDigitItemNumber(), row, (short)2);
			createCell(item.getPack(), row, (short)3);
			createCell(item.getSize(), row, (short)4);
			createCell(item.getDescription(), row, (short)5);
			createCell(item.getFormattedPrice(), row, (short)6);
			createCell(item.getQuantity(), row, (short)7);
		}
	}
}
