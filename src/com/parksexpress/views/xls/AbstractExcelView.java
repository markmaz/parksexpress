package com.parksexpress.views.xls;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.View;

import com.parksexpress.views.pdf.AbstractParksexpressPrintView;


public abstract class AbstractExcelView implements View {
	public AbstractExcelView(){}
	public static final String DATA = "data";
	public static final String COLUMNS = "columns";
	 
	@Override
	public String getContentType() {
		return "application/vnd.ms-excel";
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void render(final Map model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"" + model.get(AbstractParksexpressPrintView.REPORT_TITLE) +  ".xls\"");
		Workbook workBook = new HSSFWorkbook();
		this.renderData(model, workBook);
		workBook.write(response.getOutputStream());
		response.flushBuffer();
	}
	
	@SuppressWarnings({ "rawtypes" })
	public abstract void renderData(Map model, Workbook workBook);
	
	public void createCell(String cellValue, Row row, short column){
		Cell cell = row.createCell(column);
		cell.setCellValue(cellValue);
	}
	
	public void createCell(BigDecimal cellValue, Row row, short column){
		Cell cell = row.createCell(column);
		cell.setCellValue(cellValue.doubleValue());
	}
	
	public void createCell(int cellValue, Row row, short column) {
		Cell cell = row.createCell(column);
		cell.setCellValue(cellValue);
	}
	
	public void createCell(float cellValue, Row row, short column) {
		Cell cell = row.createCell(column);
		cell.setCellValue(cellValue);
	}
}
