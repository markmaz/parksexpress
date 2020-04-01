package com.parksexpress.views.csv;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import com.parksexpress.views.pdf.AbstractParksexpressPrintView;


public abstract class AbstractCommaSeparatedView implements View {
	public AbstractCommaSeparatedView(){}
	public static final String DATA = "data";
	public static final String COLUMNS = "columns";
	 
	@Override
	public String getContentType() {
		return "text/csv";
		//return "application/vnd.ms-excel";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void render(final Map model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"" + model.get(AbstractParksexpressPrintView.REPORT_TITLE) +  ".parks\"");
		final PrintWriter writer = response.getWriter();
		this.renderData(model, writer);
		writer.flush();
		writer.close();
	}
	
	@SuppressWarnings("unchecked")
	public abstract void renderData(Map model, PrintWriter writer);

}
