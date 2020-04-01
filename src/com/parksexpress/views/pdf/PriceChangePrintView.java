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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.item.PriceChangeItem;

public class PriceChangePrintView extends AbstractParksexpressPrintView {
	public PriceChangePrintView(){}

	private PdfPTable createTable(List<String> columns,
			List<PriceChangeItem> data, String title) {
		final float[] widths = { .75f, .85f, 3.5f, 1.25f, 1.25f, 1f, 1f, 1f, 1f, 1f };
		final PdfPTable table = new PdfPTable(widths);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		final int width = 100;
		table.setWidthPercentage(width);
		table.setHeaderRows(1);

		final float padding = 5f;
		for (int i = 0; i < columns.size(); i++) {
			final PdfPCell cell = new PdfPCell();
			cell.setPaddingBottom(padding);
			cell.setPaddingLeft(padding);

			final Paragraph p = new Paragraph((String) columns.get(i), FontFactory
					.getFont(BaseFont.HELVETICA_BOLD, 6));
			cell.addElement(p);
			table.addCell(cell);
		}

		this.generateTableCells(data, table);

		return table;
	}

	private void generateTableCells(final List<PriceChangeItem> data, final PdfPTable table) {
		for (int i = 0; i < data.size(); i++) {
			final PriceChangeItem item = data.get(i);

			float shading = 0;
			final float shaded = .9f;
			if (i % 2 == 0) {
				shading = shaded;
			} else {
				shading = 0;
			}
			
			final DecimalFormat format = new DecimalFormat("#######.##");

			final int fontSize = 6;
			final Font baseFont = FontFactory.getFont(BaseFont.HELVETICA, fontSize);
			final Paragraph p0 = new Paragraph(item.getPriceBookFamilyCode(),
					baseFont);
			final Paragraph p1 = new Paragraph(item.getCheckDigitItemNumber(), baseFont);
			final Paragraph p2 = new Paragraph(item.getDescription(), baseFont);
			final Paragraph p3 = new Paragraph("$" + format.format(item.getOldPrice()), baseFont);
			final Paragraph p4 = new Paragraph("$" + format.format(item.getNewPrice()), baseFont);
			final Paragraph p5 = new Paragraph("$" + format.format(item.getDifference()),
					baseFont);
			final Paragraph p6 = new Paragraph("$" + 
					format.format(item.getOldSrp()), baseFont);
			final Paragraph p7 = new Paragraph("$" + format.format(item.getNewSrp()),
					baseFont);
			final Paragraph p8 = new Paragraph(item.getEffectiveDate(), baseFont);
			final Paragraph p9 = new Paragraph(item.getFixedOrPercent(), baseFont);

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
			cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);

			final PdfPCell cell7 = new PdfPCell(p7);
			cell7.setGrayFill(shading);
			cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);

			final PdfPCell cell8 = new PdfPCell(p8);
			cell8.setGrayFill(shading);
			cell8.setHorizontalAlignment(Element.ALIGN_CENTER);

			final PdfPCell cell9 = new PdfPCell(p9);
			cell9.setGrayFill(shading);
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);

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
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void renderData(Document document, Map map) throws Exception {
		final List<String> columns = new ArrayList<String>();
		columns.add("Family");
		columns.add("Item #");
		columns.add("Description");
		columns.add("Old Price");
		columns.add("New Price");
		columns.add("Diff");
		columns.add("Old SRP");
		columns.add("New SRP");
		columns.add("Effective Date");
		columns.add("F/%");

		final Map<PriceBookHeader, List<PriceChangeItem>> convertedMap = 
			(Map<PriceBookHeader, List<PriceChangeItem>>)map.get(AbstractParksexpressPrintView.DATA);
		for(Map.Entry<PriceBookHeader, List<PriceChangeItem>> entry : convertedMap.entrySet()){
			final Paragraph para = new Paragraph(entry.getKey().getHeaderCode() + " - " + entry.getKey().getDescription());
			para.setSpacingAfter(5f);
			document.add(para);
			document.add(this.createTable(columns, entry.getValue(), "Price Change Report"));

		}
		
	}
}
