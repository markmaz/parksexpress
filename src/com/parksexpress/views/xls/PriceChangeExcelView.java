package com.parksexpress.views.xls;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.item.PriceChangeItem;

public class PriceChangeExcelView extends AbstractExcelView {
	public PriceChangeExcelView(){}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void renderData(Map model, Workbook workBook) {
		Sheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName("PriceChange"));
		Row header = sheet.createRow(0);
		
		createCell("Header", header, (short)0);
		createCell("Family", header, (short)1);
		createCell("Item #", header, (short)2);
		createCell("Description", header, (short)3);
		createCell("Old Price", header, (short)4);
		createCell("New Price", header, (short)5);
		createCell("Diff", header, (short)6);
		createCell("Old SRP", header, (short)7);
		createCell("New SRP", header, (short)8);
		createCell("Effective Date", header, (short)9);
		createCell("F/%", header, (short)10);
		
		int i = 1;
		
		final Map<PriceBookHeader, List<PriceChangeItem>> convertedMap = 
			(Map<PriceBookHeader, List<PriceChangeItem>>)model.get(AbstractExcelView.DATA);
		
		for(Map.Entry<PriceBookHeader, List<PriceChangeItem>> entry : convertedMap.entrySet()){			
			for(PriceChangeItem item : entry.getValue()){
				Row row = sheet.createRow(i);
				createCell(entry.getKey().getDescription().trim(), row, (short)0);
				createCell(item.getPriceBookFamilyCode().trim(), row, (short)1);
				createCell(item.getCheckDigitItemNumber().trim(), row, (short)2);
				createCell(item.getDescription().trim(), row, (short)3);
				createCell(item.getOldPrice(), row, (short)4);
				createCell(item.getNewPrice(), row, (short)5);
				createCell(item.getDifference(), row, (short)6);
				createCell(item.getOldSrp(), row, (short)7);
				createCell(item.getNewSrp(), row, (short)8);
				createCell(item.getEffectiveDate(), row, (short)9);
				createCell(item.getFixedOrPercent(), row, (short)10);
				
				i++;
			}
		}
	}
}