package com.parksexpress.views.pdf;

import java.math.BigDecimal;
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
import com.parksexpress.domain.Invoice;

public class InvoiceReportPrintView extends AbstractParksexpressPrintView {
	public InvoiceReportPrintView() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void renderData(final Document document, final Map map) throws Exception {
		final List<String> columns = new ArrayList<String>();
		columns.add("Date");
		columns.add("Invoice Number");
		columns.add("Type");
		columns.add("Amount Due");
		columns.add("Invoice Amount");
		columns.add("Payment Amount");
		columns.add("Adjustment Amount");
		columns.add("Balance");

		final PdfPTable table = this.createTable(columns, (List<Invoice>) map.get("data"), "AP Detail");
		document.add(table);
	}

	@SuppressWarnings("unchecked")
	public PdfPTable createTable(List columns, List data, String title) {
		final PdfPTable table = new PdfPTable(new float[] { 1f, 1f, .75f, .75f, .75f, .75f, .75f, .75f });
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

	private void generateTableCells(final List<Invoice> data, final PdfPTable table) {
		BigDecimal totalInvoiceAmt = new BigDecimal(0);
		BigDecimal totalPaymentAmt = new BigDecimal(0);
		BigDecimal totalAdjustmentAmt = new BigDecimal(0);
		BigDecimal totalBalanceAmt = new BigDecimal(0);

		for (int i = 0; i < data.size(); i++) {
			final Invoice invoice = data.get(i);

			totalInvoiceAmt = totalInvoiceAmt.add(invoice.getInvoiceAmount());
			totalPaymentAmt = totalPaymentAmt.add(invoice.getPaymentAmount());
			totalAdjustmentAmt = totalAdjustmentAmt.add(invoice.getAdjustmentAmount());
			totalBalanceAmt = totalBalanceAmt.add(invoice.getBalance());

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

			final Paragraph p0 = new Paragraph(invoice.getDate(), baseFont);
			final Paragraph p1 = new Paragraph(invoice.getNumber(), baseFont);
			final Paragraph p2 = new Paragraph(invoice.getType(), baseFont);
			final Paragraph p3 = new Paragraph(format.format(0D), baseFont);
			final Paragraph p4 = new Paragraph("$" + format.format(invoice.getInvoiceAmount()), baseFont);
			final Paragraph p5 = new Paragraph("$" + format.format(invoice.getPaymentAmount()), baseFont);
			final Paragraph p6 = new Paragraph("$" + format.format(invoice.getAdjustmentAmount()), baseFont);
			final Paragraph p7 = new Paragraph("$" + format.format(invoice.getBalance()), baseFont);

			final PdfPCell cell0 = new PdfPCell(p0);
			cell0.setGrayFill(shading);

			final PdfPCell cell1 = new PdfPCell(p1);
			cell1.setGrayFill(shading);

			final PdfPCell cell2 = new PdfPCell(p2);
			cell2.setGrayFill(shading);

			final PdfPCell cell3 = new PdfPCell(p3);
			cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell3.setGrayFill(shading);

			final PdfPCell cell4 = new PdfPCell(p4);
			cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell4.setGrayFill(shading);

			final PdfPCell cell5 = new PdfPCell(p5);
			cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell5.setGrayFill(shading);

			final PdfPCell cell6 = new PdfPCell(p6);
			cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
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
		this.generateBlankLine(table);
		this.generateTotalLine(table, totalInvoiceAmt, totalPaymentAmt, totalAdjustmentAmt, totalBalanceAmt);
	}

	private void generateBlankLine(PdfPTable table) {
		final PdfPCell cell0 = new PdfPCell(new Paragraph());
		final PdfPCell cell1 = new PdfPCell(new Paragraph());
		final PdfPCell cell2 = new PdfPCell(new Paragraph());
		final PdfPCell cell3 = new PdfPCell(new Paragraph());
		final PdfPCell cell4 = new PdfPCell(new Paragraph());
		final PdfPCell cell5 = new PdfPCell(new Paragraph());
		final PdfPCell cell6 = new PdfPCell(new Paragraph());
		final PdfPCell cell7 = new PdfPCell(new Paragraph());

		table.addCell(cell0);
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		table.addCell(cell6);
		table.addCell(cell7);
	}

	private void generateTotalLine(PdfPTable table, BigDecimal totalInvoiceAmt, BigDecimal totalPaymentAmt, 
			BigDecimal totalAdjustmentAmt, BigDecimal totalBalanceAmt) {
		final DecimalFormat format = new DecimalFormat("#,###,###.##");

		final int fontSize = 6;
		final Font baseFont = FontFactory.getFont(BaseFont.HELVETICA, fontSize);

		final Paragraph p0 = new Paragraph(" ", baseFont);
		final Paragraph p1 = new Paragraph(" ", baseFont);
		final Paragraph p2 = new Paragraph("Total", baseFont);
		final Paragraph p3 = new Paragraph(" ", baseFont);
		final Paragraph p4 = new Paragraph("$" + format.format(totalInvoiceAmt), baseFont);
		final Paragraph p5 = new Paragraph("$" + format.format(totalPaymentAmt), baseFont);
		final Paragraph p6 = new Paragraph("$" + format.format(totalAdjustmentAmt), baseFont);
		final Paragraph p7 = new Paragraph("$" + format.format(totalBalanceAmt), baseFont);

		final PdfPCell cell0 = new PdfPCell(p0);
		final PdfPCell cell1 = new PdfPCell(p1);
		final PdfPCell cell2 = new PdfPCell(p2);
		final PdfPCell cell3 = new PdfPCell(p3);
		final PdfPCell cell4 = new PdfPCell(p4);
		cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
		final PdfPCell cell5 = new PdfPCell(p5);
		cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
		final PdfPCell cell6 = new PdfPCell(p6);
		cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
		final PdfPCell cell7 = new PdfPCell(p7);
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
