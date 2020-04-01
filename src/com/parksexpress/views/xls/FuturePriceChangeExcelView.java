package com.parksexpress.views.xls;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.item.FuturePriceChangeItem;
import com.parksexpress.views.pdf.AbstractParksexpressPrintView;

public class FuturePriceChangeExcelView extends AbstractExcelView {
	public FuturePriceChangeExcelView(){}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void renderData(Map model, Workbook workBook) {
		Sheet sheet = workBook.createSheet(WorkbookUtil.createSafeSheetName("Future Price Change"));
		Row header = sheet.createRow(0);
		
		createCell("Header", header, (short)0);
		createCell("Item #", header, (short)1);
		createCell("Pack", header, (short)2);
		createCell("Size", header, (short)3);
		createCell("Description", header, (short)4);
		createCell("Retail UPC", header, (short)5);
		createCell("Carton UPC", header, (short)6);
		createCell("Old Price", header, (short)7);
		createCell("New Price", header, (short)8);
		createCell("Diff", header, (short)9);
		createCell("Effective Date", header, (short)10);
		
		final Map<PriceBookHeader, List<FuturePriceChangeItem>> convertedMap = 
			(Map<PriceBookHeader, List<FuturePriceChangeItem>>)model.get(AbstractParksexpressPrintView.DATA);
		
		int i = 1;
		for(Map.Entry<PriceBookHeader, List<FuturePriceChangeItem>> entry : convertedMap.entrySet()){			
			for(FuturePriceChangeItem item : entry.getValue()){
				Row row = sheet.createRow(i);
				
				createCell(entry.getKey().getDescription().trim(), row, (short)0);
				createCell(item.getCheckDigitItemNumber(), row, (short)1);
				createCell(item.getPack(), row, (short)2);
				createCell(item.getSize(), row, (short)3);
				createCell(item.getDescription(), row, (short)4);
				createCell(item.getRetailUPCNumber(), row, (short)5);
				createCell(item.getCartonUPCNumber(), row, (short)6);
				createCell(item.getOldPrice(), row, (short)7);
				createCell(item.getNewPrice(), row, (short)8);
				createCell(item.getDifference(), row, (short)9);
				createCell(item.getEffectiveDate(), row, (short)10);
				
				i++;
			}
		}
	}

}
