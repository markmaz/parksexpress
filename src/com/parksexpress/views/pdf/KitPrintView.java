package com.parksexpress.views.pdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.parksexpress.domain.Kit;
import com.parksexpress.domain.item.KitItem;

public class KitPrintView extends AbstractParksexpressPrintView {
	public KitPrintView(){}
	
	private PdfPTable createTable(final List<String> columns,
			final Kit data, final String title) {
		final PdfPTable table = new PdfPTable(new float[]{1f, 3.5f, 1f, 1f, 1.25f, .75f, 3.25f, .75f});
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

	private void generateTableCells(final Kit kit, final PdfPTable table) {
		this.printLine(kit, table);
		
		for (int i = 0; i < kit.getComponents().size(); i++) {
			final KitItem item = kit.getComponents().get(i);

			float shading = 0;
			final float darkerShading = .9f;
			
			if (i % 2 == 0) {
				shading = darkerShading;
			} else {
				shading = 0;
			}

			final Paragraph p0 = new Paragraph("");
			final Paragraph p1 = new Paragraph("");
			final Paragraph p2 = new Paragraph(item.getCheckDigitItemNumber(), KitPrintView.DATA_FONT);
			final Paragraph p3 = new Paragraph(item.getPack(), KitPrintView.DATA_FONT);
			final Paragraph p4 = new Paragraph(item.getSize(), KitPrintView.DATA_FONT);
			final Paragraph p5 = new Paragraph(item.getQuantity() + "", KitPrintView.DATA_FONT);
			final Paragraph p6 = new Paragraph(item.getDescription(), KitPrintView.DATA_FONT);
			final Paragraph p7 = new Paragraph("$" + item.getFormattedPrice(), KitPrintView.DATA_FONT);
			


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
			cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
			
			final PdfPCell cell6 = new PdfPCell(p6);
			cell6.setGrayFill(shading);
			
			final PdfPCell cell7 = new PdfPCell(p7);
			cell7.setGrayFill(shading);
			cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);
			

			table.addCell(cell0);
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell6);
			table.addCell(cell7);
		}
	}
	
	public void printLine(final Kit kit, final PdfPTable table){
		final Paragraph p0 = new Paragraph(kit.getCheckDigitItemNumber(),
				KitPrintView.DATA_FONT);
		final Paragraph p1 = new Paragraph(kit.getDescription(), KitPrintView.DATA_FONT);
		final Paragraph p2 = new Paragraph("");
		final Paragraph p3 = new Paragraph("");
		final Paragraph p4 = new Paragraph("");
		final Paragraph p5 = new Paragraph("");
		final Paragraph p6 = new Paragraph("");
		final Paragraph p7 = new Paragraph("");

		final PdfPCell cell0 = new PdfPCell(p0);
		final PdfPCell cell1 = new PdfPCell(p1);
		final PdfPCell cell2 = new PdfPCell(p2);
		final PdfPCell cell3 = new PdfPCell(p3);
		final PdfPCell cell4 = new PdfPCell(p4);
		final PdfPCell cell5 = new PdfPCell(p5);
		final PdfPCell cell6 = new PdfPCell(p6);
		final PdfPCell cell7 = new PdfPCell(p7);
		
		table.addCell(cell0);
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		table.addCell(cell6);
		table.addCell(cell7);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void renderData(final Document document, final Map map) throws Exception {
		final List<String> columns = new ArrayList<String>();
		columns.add("Kit Number");
		columns.add("Kit Description");
		columns.add("Component Number");
		columns.add("Pack");
		columns.add("Size");
		columns.add("Quantity"); 
		columns.add("Description");
		columns.add("Price"); 
		
		final PdfPTable table = this.createTable(columns, (Kit) map
				.get(DATA), (String)map.get(REPORT_TITLE));
		document.add(table);
		
	}

}
