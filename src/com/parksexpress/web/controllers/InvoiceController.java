package com.parksexpress.web.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.Invoice;
import com.parksexpress.domain.Store;
import com.parksexpress.domain.User;
import com.parksexpress.exceptions.InvalidStoreException;
import com.parksexpress.services.InvoiceService;
import com.parksexpress.services.StoreService;
import com.parksexpress.views.csv.AbstractCommaSeparatedView;
import com.parksexpress.views.pdf.AbstractParksexpressPrintView;
import com.parksexpress.views.pdf.InvoiceReportPrintView;
import com.parksexpress.views.xls.InvoiceExcelView;

public class InvoiceController extends ParksController {
	private InvoiceService invoiceService;
	private StoreService storeService;

	public InvoiceController() {
	}

	public ModelAndView show(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final User user = this.getUser(request);
		return new ModelAndView("invoiceShow", "stores", user.getStoresWithoutZones());
	}

	public ModelAndView find(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String isChain = request.getParameter("chain");
		final String code = request.getParameter("code");

		try {
			final List<Invoice> invoices = this.getInvoiceList(isChain, code, this.getUser(request));

			BigDecimal totalInvoiceAmt = new BigDecimal(0);
			BigDecimal totalPaymentAmt = new BigDecimal(0);
			BigDecimal totalAdjustmentAmt = new BigDecimal(0);
			BigDecimal totalBalanceAmt = new BigDecimal(0);

			for (Invoice invoice : invoices) {
				totalInvoiceAmt = totalInvoiceAmt.add(invoice.getInvoiceAmount());
				totalPaymentAmt = totalPaymentAmt.add(invoice.getPaymentAmount());
				totalAdjustmentAmt = totalAdjustmentAmt.add(invoice.getAdjustmentAmount());
				totalBalanceAmt = totalBalanceAmt.add(invoice.getBalance());
			}

			final ModelAndView view = new ModelAndView("invoiceData", "invoices", invoices);
			view.addObject("totalInvoiceAmount", totalInvoiceAmt);
			view.addObject("totalPaymentAmount", totalPaymentAmt);
			view.addObject("totalAdjustmentAmount", totalAdjustmentAmt);
			view.addObject("totalBalanceAmount", totalBalanceAmt);

			return view;
		} catch (InvalidStoreException e) {
			return new ModelAndView("unauthorizedUserError");
		}
	}

	private List<Invoice> getInvoiceList(String isChain, final String code, final User user) throws Exception {
		List<Invoice> invoices = null;

		if (StringUtils.isBlank(isChain)) {
			isChain = "N";
		}

		if (this.isValidStoreNumber(user, code)) {
			if (isChain.equalsIgnoreCase("Y")) {
				invoices = this.invoiceService.getInvoicesByChain(code);
			} else {
				invoices = this.invoiceService.getInvoicesByStore(code);
			}

			return invoices;
		} else {
			throw new InvalidStoreException("User not permitted to view this store.");
		}
	}

	@SuppressWarnings("unchecked")
	public ModelAndView print(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final Map model = new HashMap();
		final String isChain = request.getParameter("chain");
		final String code = request.getParameter("code");

		try {
			final List<Invoice> invoices = this.getInvoiceList(isChain, code, this.getUser(request));

			final Store store = this.storeService.getStore(code);
			model.put(AbstractParksexpressPrintView.DATA, invoices);
			model.put(AbstractParksexpressPrintView.REPORT_TITLE, "AP Detail");
			model.put(AbstractParksexpressPrintView.CUSTOMER_INFO, store.getNumber() + " - " + store.getName());

			return new ModelAndView(new InvoiceReportPrintView(), model);
		} catch (InvalidStoreException e) {
			return new ModelAndView("unauthorizedUserError");
		}
	}

	@SuppressWarnings("unchecked")
	public ModelAndView export(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final Map model = new HashMap();
		final String isChain = request.getParameter("chain");
		final String code = request.getParameter("code");

		try {
			final List<Invoice> invoices = this.getInvoiceList(isChain, code, this.getUser(request));

			model.put(AbstractCommaSeparatedView.DATA, invoices);
			model.put(AbstractParksexpressPrintView.REPORT_TITLE, "AP_Detail");
			return new ModelAndView(new InvoiceExcelView(), model);
		} catch (InvalidStoreException e) {
			return new ModelAndView("unauthorizedUserError");
		}
	}

	public void setInvoiceService(final InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public void setStoreService(final StoreService storeService) {
		this.storeService = storeService;
	}
}
