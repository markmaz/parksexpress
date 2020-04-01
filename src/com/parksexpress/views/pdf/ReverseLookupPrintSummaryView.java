package com.parksexpress.views.pdf;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.parksexpress.domain.item.ReverseLookupItem;

public class ReverseLookupPrintSummaryView extends AbstractParksexpressPrintView {
	public ReverseLookupPrintSummaryView(){}

	@SuppressWarnings("unchecked")
	@Override
	public void renderData(Document document, Map map) throws Exception {
		final List<String> columns = new ArrayList<String>();
		columns.add("Store");
		columns.add("Item #");
		columns.add("Pack");
		columns.add("Size");
		columns.add("Description");
		columns.add("Qty");
		columns.add("Cost");
		columns.add("Retail");
		columns.add("PFT %");

		final PdfPTable table = this.createTable(columns, (Map<String, List<ReverseLookupItem>>) map
				.get("data"), "Reverse Lookup");
		document.add(table);
	}
	
	@SuppressWarnings("unchecked")
	public PdfPTable createTable(List columns, Map<String, List<ReverseLookupItem>> data, String title){
		final PdfPTable table = new PdfPTable(new float[]{2.5f, .75f, .75f, .75f, 2.75f, .85f, .85f, .75f, .75f});
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
	
	private void generateTableCells(final Map<String, List<ReverseLookupItem>> data, final PdfPTable table) {
		final SortedSet<String> sortedSet = new TreeSet<String>(data.keySet());
		final Iterator<String> iter = sortedSet.iterator();

		while(iter.hasNext()){
			final String store = iter.next();
			this.printLine(table, store, 0f);
			final List<ReverseLookupItem> list = data.get(store);
			
			long totalQty = 0;
			BigDecimal totalCost = new BigDecimal(0);
			BigDecimal totalRetail = new BigDecimal(0);
			float totalSrp = 0f;
			int count = 0;
			
    		for (int i = 0; i < list.size(); i++) {
    			final ReverseLookupItem item = list.get(i);
    
    			float shading = 0;
    			final float shaded = .9f;
    			if (i % 2 == 0) {
    				shading = shaded;
    			} else {
    				shading = 0;
    			}
    			
    			this.printLine(table, item, shading);
    			totalQty += item.getQuantity();
    			totalCost = totalCost.add(item.getCostAmount());
    			totalRetail = totalRetail.add(item.getSrpAmount());
    			totalSrp += item.getProfitPercent();
    			count++;
    		}
    		
    		float avg = totalSrp/count;
    		this.printLine(table, totalQty, totalCost, totalRetail, avg, 0f);
    		this.printLine(table, " ", 0f);
		}
	}

	private void printLine(PdfPTable table, long totalQty, BigDecimal totalCost, BigDecimal totalRetail, float profitPercent, float shading) {
		final int fontSize = 6;
		final Font baseFont = FontFactory.getFont(BaseFont.HELVETICA, fontSize);
		final Font boldFont = FontFactory.getFont(BaseFont.HELVETICA_BOLD, fontSize);
		final DecimalFormat format = new DecimalFormat("#,###,###.##");
		
		final Paragraph p0 = new Paragraph(" ", baseFont);
		final Paragraph p1 = new Paragraph(" ", baseFont);
		final Paragraph p2 = new Paragraph(" ", baseFont);
		final Paragraph p3 = new Paragraph(" ", baseFont);
		final Paragraph p4 = new Paragraph("Total", boldFont);
		final Paragraph p5 = new Paragraph(totalQty + "", baseFont);
		final Paragraph p6 = new Paragraph("$" + format.format(totalCost), baseFont);
		final Paragraph p7 = new Paragraph("$" + format.format(totalRetail), baseFont);
		final Paragraph p8 = new Paragraph(format.format(profitPercent) + "%", baseFont);

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
		cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);

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
		cell8.setHorizontalAlignment(Element.ALIGN_RIGHT);


		table.addCell(cell0);
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		table.addCell(cell6);
		table.addCell(cell7);
		table.addCell(cell8);	
	}

	private void printLine(final PdfPTable table, final ReverseLookupItem item, float shading) {
		final DecimalFormat format = new DecimalFormat("#,###,###.##");

		final int fontSize = 6;
		final Font baseFont = FontFactory.getFont(BaseFont.HELVETICA, fontSize);
		
		final Paragraph p0 = new Paragraph("", baseFont);
		final Paragraph p1 = new Paragraph(item.getCheckDigitItemNumber(), baseFont);
		final Paragraph p2 = new Paragraph(item.getPack(), baseFont);
		final Paragraph p3 = new Paragraph(item.getSize(), baseFont);
		final Paragraph p4 = new Paragraph(item.getDescription(), baseFont);
		final Paragraph p5 = new Paragraph(item.getQuantity() + "", baseFont);
		final Paragraph p6 = new Paragraph("$" + format.format(item.getCostAmount()), baseFont);
		final Paragraph p7 = new Paragraph("$" + format.format(item.getSrpAmount()),
				baseFont);
		final Paragraph p8 = new Paragraph(format.format(item.getProfitPercent()) + "%", baseFont);

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
		cell8.setHorizontalAlignment(Element.ALIGN_RIGHT);


		table.addCell(cell0);
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		table.addCell(cell6);
		table.addCell(cell7);
		table.addCell(cell8);
	}
	private void printLine(final PdfPTable table, String store, float shading) {
		final int fontSize = 6;
		final Font baseFont = FontFactory.getFont(BaseFont.HELVETICA, fontSize);
		
		final Paragraph p0 = new Paragraph(store, baseFont);
		final Paragraph p1 = new Paragraph("", baseFont);
		final Paragraph p2 = new Paragraph("", baseFont);
		final Paragraph p3 = new Paragraph("", baseFont);
		final Paragraph p4 = new Paragraph("", baseFont);
		final Paragraph p5 = new Paragraph("", baseFont);
		final Paragraph p6 = new Paragraph("", baseFont);
		final Paragraph p7 = new Paragraph("", baseFont);
		final Paragraph p8 = new Paragraph("", baseFont);

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
		cell8.setHorizontalAlignment(Element.ALIGN_RIGHT);


		table.addCell(cell0);
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		table.addCell(cell6);
		table.addCell(cell7);
		table.addCell(cell8);
	}
}
