package com.parksexpress.views.pdf;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.parksexpress.domain.item.MovementItem;

public class MovementReportPrintView extends AbstractParksexpressPrintView {
	public MovementReportPrintView(){}
	
	@SuppressWarnings("unchecked")
	@Override
	public void renderData(Document document, Map map) throws Exception {
		final List<String> columns = new ArrayList<String>();
		columns.add("Item #");
		columns.add("Pack");
		columns.add("Size");
		columns.add("Description");
		columns.add("Carton UPC");
		columns.add("Retail UPC");
		columns.add("Units");
		columns.add("Cost Amount");
		columns.add("SRP Amount");
		columns.add("Profit %");
		columns.add("Profit $");
		columns.add("Vendor #");
		
		final PdfPTable table = this.createTable(columns, (List<MovementItem>) map
				.get(DATA), (String)map.get(REPORT_TITLE));
		document.add(table);
	}
	
	private PdfPTable createTable(final List<String> columns,
			final List<MovementItem> data, final String title) {
		final PdfPTable table = new PdfPTable(new float[]{.75f, .75f, .75f, 3.5f, 1.25f, 1.25f, 1f, 1f, 1f, 1f, 1f, 1f});
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		final int width = 100;
		table.setWidthPercentage(width);
		table.setHeaderRows(1);

		for (int i = 0; i < columns.size(); i++) {
			final float padding = 5f;
			final PdfPCell cell = new PdfPCell();
			cell.setPaddingBottom(padding);
			cell.setPaddingLeft(padding);

			final Paragraph p = new Paragraph((String) columns.get(i), DATA_FONT);
			cell.addElement(p);
			table.addCell(cell);
		}

		this.generateTableCells(data, table);

		return table;
	}
	
	private void generateTableCells(final List<MovementItem> data, final PdfPTable table) {
		for (int i = 0; i < data.size(); i++) {
			final MovementItem item = data.get(i);

			float shading = 0;
			final float darkerShading = .9f;
			
			if (i % 2 == 0) {
				shading = darkerShading;
			} else {
				shading = 0;
			}

			final DecimalFormat format = new DecimalFormat("#.00");
			
			final Paragraph p0 = new Paragraph(item.getCheckDigitItemNumber(),
					DefaultHotSheetPrintView.DATA_FONT);
			final Paragraph p1 = new Paragraph(item.getPack(), DefaultHotSheetPrintView.DATA_FONT);
			final Paragraph p2 = new Paragraph(item.getSize(), DefaultHotSheetPrintView.DATA_FONT);
			final Paragraph p3 = new Paragraph(item.getDescription(), DefaultHotSheetPrintView.DATA_FONT);
			final Paragraph p4 = new Paragraph(item.getCartonUPCNumber(), DefaultHotSheetPrintView.DATA_FONT);
			final Paragraph p5 = new Paragraph(item.getRetailUPCNumber(), DefaultHotSheetPrintView.DATA_FONT);
			final Paragraph p6 = new Paragraph(item.getFullCase() + "", DefaultHotSheetPrintView.DATA_FONT);
			final Paragraph p7 = new Paragraph("$" + format.format(item.getSrp().doubleValue()), DefaultHotSheetPrintView.DATA_FONT);
			final Paragraph p8 = new Paragraph("$" + format.format(item.getExtendedCostAmount().doubleValue()), DefaultHotSheetPrintView.DATA_FONT);
			final Paragraph p9 = new Paragraph(item.getProfitPercent().toString(), DefaultHotSheetPrintView.DATA_FONT);
			final Paragraph p10 = new Paragraph("$" + format.format(item.getProfitDollars().doubleValue()), DefaultHotSheetPrintView.DATA_FONT);
			final Paragraph p11 = new Paragraph(item.getVendorNumber(), DefaultHotSheetPrintView.DATA_FONT);
			
			final PdfPCell cell0 = new PdfPCell(p0);
			cell0.setGrayFill(shading);

			final PdfPCell cell1 = new PdfPCell(p1);
			cell1.setGrayFill(shading);

			final PdfPCell cell2 = new PdfPCell(p2);
			cell2.setGrayFill(shading);

			final PdfPCell cell3 = new PdfPCell(p3);
			cell3.setGrayFill(shading);

			final PdfPCell cell4 = new PdfPCell(p4);
			cell4.setGrayFill(shading);

			final PdfPCell cell5 = new PdfPCell(p5);
			cell5.setGrayFill(shading);

			final PdfPCell cell6 = new PdfPCell(p6);
			cell6.setGrayFill(shading);
			cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
			
			final PdfPCell cell7 = new PdfPCell(p7);
			cell7.setGrayFill(shading);
			cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);
			
			final PdfPCell cell8 = new PdfPCell(p8);
			cell8.setGrayFill(shading);
			cell8.setHorizontalAlignment(Element.ALIGN_RIGHT);	
			
			final PdfPCell cell9 = new PdfPCell(p9);
			cell9.setGrayFill(shading);
			cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
			
			final PdfPCell cell10 = new PdfPCell(p10);
			cell10.setGrayFill(shading);
			cell10.setHorizontalAlignment(Element.ALIGN_RIGHT);
			
			final PdfPCell cell11 = new PdfPCell(p11);
			cell11.setGrayFill(shading);
			
			table.addCell(cell0);
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			table.addCell(cell7);
			table.addCell(cell8);
			table.addCell(cell9);
			table.addCell(cell10);
			table.addCell(cell11);
		}
	}
}