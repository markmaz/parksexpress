/**
 * 
 */
package com.parksexpress.events;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author markmaz
 * 
 */
public class DefaultPageEvent extends PdfPageEventHelper {
	public static final int FONT_SIZE = 6;
	protected Phrase header;
	protected Log logger = LogFactory.getLog(this.getClass());

	public DefaultPageEvent(String reportName) {
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		final String date = sdf.format(Calendar.getInstance().getTime());
		final Font baseFont = FontFactory.getFont(BaseFont.HELVETICA,
				DefaultPageEvent.FONT_SIZE);
		
		final Chunk report = new Chunk(reportName, baseFont);
		final Chunk dateRange = new Chunk("             Printed on: " + date, baseFont);
		this.header = new Phrase();
		this.header.setLeading(10f);
		this.header.add(report);
		this.header.add(dateRange);
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		final PdfContentByte cb = writer.getDirectContent();
		final float bottom = 25f;
		final int offset = 15 / 2;
		
		ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, this.header, document
				.leftMargin(), document.bottom(), 0);

		ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Phrase("Page "
				+ document.getPageNumber(), FontFactory.getFont(
				BaseFont.HELVETICA, DefaultPageEvent.FONT_SIZE)), document.right()
				- document.left() + offset, document.bottom() , 0);
	}
}
