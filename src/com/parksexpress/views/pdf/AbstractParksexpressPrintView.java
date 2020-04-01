package com.parksexpress.views.pdf;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.parksexpress.events.DefaultPageEvent;

public abstract class AbstractParksexpressPrintView implements View {
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	public static final String REPORT_TITLE = "reportTitle";
	public static final String CUSTOMER_INFO = "customerInfo";
	public static final String DATA = "data";
	public static final String COLUMNS = "columns";
	public static final String ADDITIONAL_CRITERIA = "criteria";

	protected static final Font REPORT_TITLE_FONT = FontFactory.getFont(BaseFont.HELVETICA_BOLD, 16);
	protected static final Font SMALL_FONT = FontFactory.getFont(BaseFont.HELVETICA, 4);
	protected static final Font DATA_FONT = FontFactory.getFont(BaseFont.HELVETICA, 6);

	public String getContentType() {
		return "application/pdf";
	}

	protected PdfPCell createBorderlessCell() {
		final PdfPCell cell = new PdfPCell();
		cell.disableBorderSide(PdfPCell.LEFT);
		cell.disableBorderSide(PdfPCell.RIGHT);
		cell.disableBorderSide(PdfPCell.BOTTOM);
		cell.disableBorderSide(PdfPCell.TOP);

		return cell;
	}

	protected PdfPCell createBorderlessCell(Image image) {
		final PdfPCell cell = new PdfPCell(image);
		cell.disableBorderSide(PdfPCell.LEFT);
		cell.disableBorderSide(PdfPCell.RIGHT);
		cell.disableBorderSide(PdfPCell.BOTTOM);
		cell.disableBorderSide(PdfPCell.TOP);

		return cell;
	}

	@SuppressWarnings("unchecked")
	protected void createTitleArea(Document document, Map map) throws Exception {
		final PdfPTable title = this.createPdfPTable(new float[] { 1, 1 });
		final int titleSpacing = 10;
		title.setSpacingAfter(titleSpacing);

		final Image image = Image.getInstance(ViewConstants.LOGO_URL);
		final float scalePercent = 50f;
		image.scalePercent(scalePercent);

		final PdfPCell imageCell = this.createBorderlessCell(image);
		imageCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		imageCell.setVerticalAlignment(PdfPCell.ALIGN_TOP);

		final PdfPCell reportTitle = this.createBorderlessCell();
		reportTitle.setHorizontalAlignment(PdfPCell.LEFT);
		reportTitle.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

		final Chunk reportName = new Chunk((String) map.get(AbstractParksexpressPrintView.REPORT_TITLE) + "\n",
				AbstractParksexpressPrintView.REPORT_TITLE_FONT);
		final Chunk storeNumber = new Chunk((String) map.get(AbstractParksexpressPrintView.CUSTOMER_INFO), FontFactory.getFont(
				BaseFont.HELVETICA, 8));

		final Phrase reportPhrase = new Phrase();
		final float leading = 12f;
		reportPhrase.setLeading(leading);
		reportPhrase.add(reportName);
		reportPhrase.add(storeNumber);

		final Paragraph rp = new Paragraph(reportPhrase);
		reportTitle.addElement(rp);

		title.addCell(reportTitle);
		title.addCell(imageCell);
		document.add(title);
	}

	@SuppressWarnings("unchecked")
	public void render(final Map map, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final Document document = new Document();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PdfWriter writer = PdfWriter.getInstance(document, baos);

		if (map.containsKey(AbstractParksexpressPrintView.START_DATE) && map.containsKey(AbstractParksexpressPrintView.END_DATE)) {
			writer.setPageEvent(new DefaultPageEvent(map.get(AbstractParksexpressPrintView.REPORT_TITLE) + " ("
					+ (String) map.get(AbstractParksexpressPrintView.START_DATE) + " - "
					+ (String) map.get(AbstractParksexpressPrintView.END_DATE) + ")"));
		} else {
			writer.setPageEvent(new DefaultPageEvent((String) map.get(AbstractParksexpressPrintView.REPORT_TITLE)));
		}

		document.open();

		this.createTitleArea(document, map);
		this.createHeaderInfo(document, map);
		this.renderData(document, map);
		document.close();

		response.setContentType("application/pdf");
		response.setContentLength(baos.size());
		response.addHeader("Expires", "0");
		response.addHeader("Pragma", "public");
		response.addHeader("Content-Disposition", " inline; filename=itemAllowance.pdf");
		final ServletOutputStream out = response.getOutputStream();
		baos.writeTo(out);
		out.flush();
	}

	private PdfPCell createAddressCell() {
		final PdfPCell addressCell = this.createBorderlessCell();
		final Chunk line1 = new Chunk("500 Belvedere Dr.\n", AbstractParksexpressPrintView.SMALL_FONT);
		final Chunk line2 = new Chunk("P.O. Box 119\n", AbstractParksexpressPrintView.SMALL_FONT);
		final Chunk line3 = new Chunk("Gallatin, TN 37066", AbstractParksexpressPrintView.SMALL_FONT);

		final Phrase address = new Phrase();
		final float leading = 5f;
		address.setLeading(leading);
		address.add(line1);
		address.add(line2);
		address.add(line3);

		addressCell.addElement(address);
		addressCell.setHorizontalAlignment(PdfPCell.LEFT);
		return addressCell;
	}

	@SuppressWarnings("unchecked")
	private void createHeaderInfo(final Document document, final Map map) throws Exception {
		final PdfPTable headerTable = this.createPdfPTable(new float[] { 1, 1 });
		headerTable.addCell(this.createAddressCell());
		headerTable.addCell(this.createPhoneCell());

		if (map.containsKey(AbstractParksexpressPrintView.START_DATE) && map.containsKey(AbstractParksexpressPrintView.END_DATE)) {
			final PdfPCell dateRange = this.createBorderlessCell();
			final Paragraph date = new Paragraph((String) map.get(AbstractParksexpressPrintView.START_DATE) + " Through "
					+ (String) map.get(AbstractParksexpressPrintView.END_DATE), FontFactory.getFont(BaseFont.HELVETICA_BOLD, 6));
			date.setAlignment(Paragraph.ALIGN_LEFT);
			dateRange.addElement(date);
			headerTable.addCell(dateRange);

			if (map.containsKey(AbstractParksexpressPrintView.ADDITIONAL_CRITERIA)) {
				final PdfPCell criterion = this.createBorderlessCell();
				final Paragraph criteria = new Paragraph((String) map.get(AbstractParksexpressPrintView.ADDITIONAL_CRITERIA), 
						FontFactory.getFont(BaseFont.HELVETICA_BOLD, 6));
				criterion.addElement(criteria);
				headerTable.addCell(criterion);
			}

		} else {
			headerTable.addCell(this.createBorderlessCell());
		}

		headerTable.addCell(this.createNoticeCell());
		document.add(headerTable);
	}

	private PdfPCell createNoticeCell() {
		final PdfPCell noticeCell = this.createBorderlessCell();
		final Paragraph notice = new Paragraph("Please have all orders in by noon CST", FontFactory.getFont(BaseFont.HELVETICA_BOLD, 6));
		notice.setAlignment(Paragraph.ALIGN_RIGHT);
		final float spacing = 5f;
		notice.setSpacingAfter(spacing);
		noticeCell.addElement(notice);
		return noticeCell;
	}

	private PdfPCell createPhoneCell() {
		final PdfPCell phoneCell = this.createBorderlessCell();
		phoneCell.setHorizontalAlignment(PdfPCell.RIGHT);

		final Chunk phone1 = new Chunk("MSI Ordering: 1-800-225-3568\n", AbstractParksexpressPrintView.SMALL_FONT);
		final Chunk phone2 = new Chunk("1-800-843-6139\n", AbstractParksexpressPrintView.SMALL_FONT);
		final Chunk phone3 = new Chunk("Office: 1-800-873-2406", AbstractParksexpressPrintView.SMALL_FONT);

		final Phrase phone = new Phrase();
		final float spacing = 5f;
		phone.setLeading(spacing);
		phone.add(phone1);
		phone.add(phone2);
		phone.add(phone3);

		final Paragraph p = new Paragraph(phone);
		p.setAlignment(Element.ALIGN_RIGHT);
		phoneCell.addElement(p);
		return phoneCell;
	}

	private PdfPTable createPdfPTable(final float[] cellSize) {
		final PdfPTable headerTable = new PdfPTable(cellSize);
		headerTable.setHeaderRows(0);
		final int width = 100;
		headerTable.setWidthPercentage(width);
		headerTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		return headerTable;
	}

	@SuppressWarnings("unchecked")
	public abstract void renderData(final Document document, final Map map) throws Exception;
}
