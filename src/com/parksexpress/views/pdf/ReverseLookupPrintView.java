package com.parksexpress.views.pdf;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.parksexpress.domain.item.ReverseLookupItem;

public class ReverseLookupPrintView extends AbstractParksexpressPrintView {
	public static final String TOTAL_COST = "TOTAL_COST";
	public static final String TOTAL_UNITS = "TOTAL_UNITS";
	public static final String TOTAL_RETAIL = "TOTAL_RETAIL";

	public ReverseLookupPrintView(){}

	@SuppressWarnings("unchecked")
	@Override
	public void renderData(Document document, Map map) throws Exception {
	
		final List<String> columns = new ArrayList<String>();
		columns.add("Item #");
		columns.add("Vendor");
		columns.add("Pack");
		columns.add("Size");
		columns.add("Description");
		columns.add("Carton UPC");
		columns.add("Retail UPC");
		columns.add("Qty");
		columns.add("Cost");
		columns.add("Retail");
		columns.add("PFT %");

		final PdfPTable table = this.createTable(columns, (List<ReverseLookupItem>) map
				.get("data"), "Reverse Lookup", map);
		document.add(table);
	}
	
	@SuppressWarnings("unchecked")
	public PdfPTable createTable(List columns, List data, String title, Map map){
		final PdfPTable table = new PdfPTable(new float[]{.75f, 2.5f, .75f, .75f, 2.75f, 1.25f, 1.25f, .95f, 1.1f, 1.1f, 1.1f});
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
		this.generateTotals(table, map);
		return table;
	}
	
	private void generateTotals(PdfPTable table, Map map) {
		final DecimalFormat format = new DecimalFormat("#,###,###.##");

		final int fontSize = 6;
		final Font baseFont = FontFactory.getFont(BaseFont.HELVETICA, fontSize);

		for(int i = 0; i < 11; i++){
			table.addCell(new PdfPCell());
		}
		
		final Paragraph p0 = new Paragraph("", baseFont);
		final Paragraph p1 = new Paragraph("", baseFont);
		final Paragraph p2 = new Paragraph("", baseFont);
		final Paragraph p3 = new Paragraph("", baseFont);
		final Paragraph p4 = new Paragraph("", baseFont);
		final Paragraph p5 = new Paragraph("", baseFont);
		final Paragraph p6 = new Paragraph("", baseFont);
		final Paragraph p7 = new Paragraph(map.get(ReverseLookupPrintView.TOTAL_UNITS) + "", baseFont);
		final Paragraph p8 = new Paragraph("$" + map.get(ReverseLookupPrintView.TOTAL_COST), baseFont);
		final Paragraph p9 = new Paragraph("$" + map.get(ReverseLookupPrintView.TOTAL_RETAIL),
				baseFont);
		final Paragraph p10 = new Paragraph("", baseFont);

		final PdfPCell cell0 = new PdfPCell(p0);
		final PdfPCell cell1 = new PdfPCell(p1);
		final PdfPCell cell2 = new PdfPCell(p2);
		final PdfPCell cell3 = new PdfPCell(p3);
		final PdfPCell cell4 = new PdfPCell(p4);
		final PdfPCell cell5 = new PdfPCell(p5);
		final PdfPCell cell6 = new PdfPCell(p6);
		
		final PdfPCell cell7 = new PdfPCell(p7);
		cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);

		final PdfPCell cell8 = new PdfPCell(p8);
		cell8.setHorizontalAlignment(Element.ALIGN_RIGHT);

		final PdfPCell cell9 = new PdfPCell(p9);
		cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);

		final PdfPCell cell10 = new PdfPCell(p10);
		cell10.setHorizontalAlignment(Element.ALIGN_RIGHT);
		
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
	}

	private void generateTableCells(final List<ReverseLookupItem> data, final PdfPTable table) {
		for (int i = 0; i < data.size(); i++) {
			final ReverseLookupItem item = data.get(i);

			float shading = 0;
			final float shaded = .9f;
			if (i % 2 == 0) {
				shading = shaded;
			} else {
				shading = 0;
			}
			
			final DecimalFormat format = new DecimalFormat("#,###,###.##");

			final int fontSize = 6;
			final Font baseFont = FontFactory.getFont(BaseFont.HELVETICA, fontSize);

			final Paragraph p0 = new Paragraph(item.getCheckDigitItemNumber(), baseFont);
			final Paragraph p1 = new Paragraph(item.getVendorName(), baseFont);
			final Paragraph p2 = new Paragraph(item.getPack(), baseFont);
			final Paragraph p3 = new Paragraph(item.getSize(), baseFont);
			final Paragraph p4 = new Paragraph(item.getDescription(), baseFont);
			final Paragraph p5 = new Paragraph(item.getRetailUPCNumber(), baseFont);
			final Paragraph p6 = new Paragraph(item.getCartonUPCNumber(), baseFont);
			final Paragraph p7 = new Paragraph(item.getQuantity() + "", baseFont);
			final Paragraph p8 = new Paragraph("$" + format.format(item.getCostAmount()), baseFont);
			final Paragraph p9 = new Paragraph("$" + format.format(item.getSrpAmount()),baseFont);
			final Paragraph p10 = new Paragraph("$" + format.format(item.getProfitPercent()), baseFont);

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
		}
	}
}
