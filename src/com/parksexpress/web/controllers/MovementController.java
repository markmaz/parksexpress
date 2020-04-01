package com.parksexpress.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.Store;
import com.parksexpress.domain.User;
import com.parksexpress.domain.item.MovementItem;
import com.parksexpress.exceptions.InvalidStoreException;
import com.parksexpress.services.MovementService;
import com.parksexpress.services.PriceBookService;
import com.parksexpress.services.StoreService;
import com.parksexpress.util.DateUtil;
import com.parksexpress.views.pdf.AbstractParksexpressPrintView;
import com.parksexpress.views.pdf.MovementReportPrintView;
import com.parksexpress.views.xls.AbstractExcelView;
import com.parksexpress.views.xls.MovementExcelView;

public class MovementController extends ParksController {
	private PriceBookService priceBookService;
	private MovementService movementService;
	private StoreService storeService;

	public MovementController() {
	}

	public ModelAndView show(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final User user = (User) request.getSession().getAttribute("authenticatedUser");
		final List<PriceBookHeader> headers = this.priceBookService.getPriceBookHeaders();

		final ModelAndView view = new ModelAndView("movementShow", "stores", user.getStoresWithoutZones());
		view.addObject("headers", headers);

		return view;
	}

	public ModelAndView data(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		try {
			final List<MovementItem> data = this.generateMovementReport(request);
			return new ModelAndView("movementData", "data", data);
		} catch (InvalidStoreException e) {
			return new ModelAndView("unauthorizedUserError");
		}
	}

	@SuppressWarnings("unchecked")
	public ModelAndView print(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		try {
			final Map model = new HashMap();
			final Store store = this.storeService.getStore(request.getParameter("store"));
			final List<MovementItem> data = this.generateMovementReport(request);
			model.put(AbstractParksexpressPrintView.DATA, data);
			model.put(AbstractParksexpressPrintView.CUSTOMER_INFO, store.getNumber() + " - " + store.getName());
			model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Movement Report");

			return new ModelAndView(new MovementReportPrintView(), model);
		} catch (InvalidStoreException e) {
			return new ModelAndView("unauthorizedUserError");
		}
	}

	@SuppressWarnings("unchecked")
	public ModelAndView export(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		try {
			final Map model = new HashMap();
			model.put(AbstractExcelView.DATA, this.generateMovementReport(request));
			model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Movement_Report");
			return new ModelAndView(new MovementExcelView(), model);
		} catch (InvalidStoreException e) {
			return new ModelAndView("unauthorizedUserError");
		}
	}

	private List<MovementItem> generateMovementReport(final HttpServletRequest request) throws Exception {
		String startDate = request.getParameter("start");
		String endDate = request.getParameter("end");
		final String headerCode = request.getParameter("code");
		final String storeNumber = request.getParameter("store");
		final String dateValue = request.getParameter("time");

		if (this.isValidStoreNumber(this.getUser(request), storeNumber)) {
			if (StringUtils.isNotEmpty(dateValue)) {
				if (dateValue.equalsIgnoreCase("MTD")) {
					startDate = DateUtil.getMonthStart();
					endDate = DateUtil.today();
				}

				if (dateValue.equalsIgnoreCase("YTD")) {
					startDate = DateUtil.getYearStart();
					endDate = DateUtil.today();
				}
			}

			final List<MovementItem> data = this.movementService.getMovement(storeNumber, endDate, startDate, headerCode);
			return data;
		} else {
			throw new InvalidStoreException("Invalid user");
		}
	}

	public void setPriceBookService(PriceBookService priceBookService) {
		this.priceBookService = priceBookService;
	}

	public void setMovementService(MovementService movementService) {
		this.movementService = movementService;
	}

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}
}
