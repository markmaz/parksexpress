package com.parksexpress.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.Chain;
import com.parksexpress.domain.Store;
import com.parksexpress.domain.User;
import com.parksexpress.domain.item.ApprovedDistributionsItem;
import com.parksexpress.services.ChainService;
import com.parksexpress.services.StoreService;
import com.parksexpress.services.AS400.impl.ApprovedDistributionsService;
import com.parksexpress.views.csv.AbstractCommaSeparatedView;
import com.parksexpress.views.pdf.AbstractParksexpressPrintView;
import com.parksexpress.views.pdf.ApprovedDistributionsPrintView;
import com.parksexpress.views.xls.ApprovedDistributionsExcelView;

public class ApprovedDistributionController extends ParksController {
	private ApprovedDistributionsService approvedDistributionsService;
	private ChainService chainService;
	private StoreService storeService;
	
	public ApprovedDistributionController() {
	}

	public void setApprovedDistributionsService(ApprovedDistributionsService approvedDistributionsService) {
		this.approvedDistributionsService = approvedDistributionsService;
	}

	public void setChainService(ChainService chainService) {
		this.chainService = chainService;
	}

	public void setStoreService(StoreService storeService){
		this.storeService = storeService;
	}
	
	public ModelAndView show(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final User user = this.getUser(request);
		final String chainCode = user.getStores().get(0).getChainCode();
		final Chain chain = this.chainService.getChain(chainCode);

		final ModelAndView view = new ModelAndView("approvedDistributions", "stores", user.getStoresWithoutZones());
		view.addObject("chain", chain);

		return view;
	}

	public ModelAndView data(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String storeNumber = request.getParameter("store");
		final String start = request.getParameter("start");
		final String end = request.getParameter("end");
		String item = request.getParameter("item");
		
		if(StringUtils.isNotBlank(item)){
			String[] items = StringUtils.split(item, ":");
			item = items[1];
		}
		
		final User user = this.getUser(request);
		final boolean isChain = this.isChain(storeNumber);
		
		final List<ApprovedDistributionsItem> report = this.buildReport(storeNumber, start, end, item, user, isChain);
		
		if(isChain){
			return new ModelAndView("approvedDistributionsForChain", "data", report);
		}else{
			return new ModelAndView("approvedDistributionsData", "data", report);
		}
	}

	@SuppressWarnings("unchecked")
	public ModelAndView print(final HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String storeNumber = request.getParameter("store");
		final String start = request.getParameter("start");
		final String end = request.getParameter("end");
		final String item = request.getParameter("item");
		final User user = this.getUser(request);
		final boolean isChain = this.isChain(storeNumber);
		
		final Map model = new HashMap();
		model.put(AbstractParksexpressPrintView.DATA, this.buildReport(storeNumber, start, end, item, user, isChain));
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Approved Distribution Report");

		if(isChain){
			final Chain chain = chainService.getChain(storeNumber);
			model.put(AbstractParksexpressPrintView.CUSTOMER_INFO, chain.getName());
		}else{
			model.put(AbstractParksexpressPrintView.CUSTOMER_INFO, this.getStore(storeNumber, user).getName());
		}
		
		if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
			model.put(AbstractParksexpressPrintView.START_DATE, start);
			model.put(AbstractParksexpressPrintView.END_DATE, end);
		}

		return new ModelAndView(new ApprovedDistributionsPrintView(isChain), model);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView export(final HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String storeNumber = request.getParameter("store");
		final String start = request.getParameter("start");
		final String end = request.getParameter("end");
		final String item = request.getParameter("item");
		final User user = this.getUser(request);
		final boolean isChain = this.isChain(storeNumber);
		final Map model = new HashMap();
		model.put(AbstractCommaSeparatedView.DATA, this.buildReport(storeNumber, start, end, item, user, isChain));
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Approved_Distributions");
		return new ModelAndView(new ApprovedDistributionsExcelView(isChain), model);

	}

	private List<ApprovedDistributionsItem> buildReport(final String storeNumber, final String start, 
			final String end, final String item,
			final User user, final boolean isChain) {

		List<ApprovedDistributionsItem> report = new ArrayList<ApprovedDistributionsItem>();

		// If the item number is empty then we can only call the method with dates.
		if (StringUtils.isBlank(item)) {
			if (StringUtils.isNotBlank(start) || StringUtils.isNotBlank(end)) {
				if(isChain){
					final Chain chain = this.chainService.getChain(storeNumber);
					chain.setStores(this.storeService.getStoresInChain(chain.getNumber(), true));
					report = this.approvedDistributionsService.getApprovedDistributions(chain, start, end);
				}else{
					report = this.approvedDistributionsService.getApprovedDistributions(storeNumber, start, end);
				}
			}
			// Decide whether the dates are present or not.
		} else {
			if (StringUtils.isBlank(start) || StringUtils.isBlank(end)) {
				if(isChain){
					final Chain chain = this.chainService.getChain(storeNumber);
					chain.setStores(this.storeService.getStoresInChain(chain.getNumber(), true));
					report = this.approvedDistributionsService.getApprovedDistributions(chain, item);
				}else{
					report = this.approvedDistributionsService.getApprovedDistributions(storeNumber, item);
				}
			} else {
				if(isChain){
					final Chain chain = this.chainService.getChain(storeNumber);
					chain.setStores(this.storeService.getStoresInChain(chain.getNumber(), true));
					report = this.approvedDistributionsService.getApprovedDistributions(chain, item, start, end);
				}else{
					report = this.approvedDistributionsService.getApprovedDistributions(storeNumber, item, start, end);
				}
			}
		}

		return report;
	}

	private Store getStore(String number, User user) {
		for (Store store : user.getStores()) {
			if (store.getNumber().equalsIgnoreCase(number)) {
				return store;
			}
		}

		return null;
	}

	private boolean isChain(final String storeNumber) {
		return this.chainService.isChain(storeNumber);
	}
}
