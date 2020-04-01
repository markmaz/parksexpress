package com.parksexpress.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.User;
import com.parksexpress.domain.item.FuturePriceChangeItem;
import com.parksexpress.services.ItemService;
import com.parksexpress.services.PriceBookService;
import com.parksexpress.views.csv.AbstractCommaSeparatedView;
import com.parksexpress.views.pdf.AbstractParksexpressPrintView;
import com.parksexpress.views.pdf.FuturePriceChangePrintView;
import com.parksexpress.views.xls.FuturePriceChangeExcelView;

public class FuturePriceChangeController extends ParksController {
	private ItemService itemService;
	private PriceBookService priceBookService;

	public FuturePriceChangeController() {
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public void setPriceBookService(PriceBookService priceBookService) {
		this.priceBookService = priceBookService;
	}

	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("futurePriceChange");
	}

	public ModelAndView report(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		final Map<PriceBookHeader, List<FuturePriceChangeItem>> itemMap = this.buildMap(user.getOrderGuideNumber());
		return new ModelAndView("futurePriceChangeData", "itemMap", itemMap);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView print(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		final Map<PriceBookHeader, List<FuturePriceChangeItem>> itemMap = this.buildMap(user.getOrderGuideNumber());
		final Map model = new HashMap();
		
		model.put(AbstractParksexpressPrintView.DATA, itemMap);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Future Price Change Report");
		model.put(AbstractParksexpressPrintView.CUSTOMER_INFO, user.getStores().get(0).getChain().getName());
		return new ModelAndView(new FuturePriceChangePrintView(), model);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView export(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		final Map<PriceBookHeader, List<FuturePriceChangeItem>> itemMap = this.buildMap(user.getOrderGuideNumber());
		
		final Map model = new HashMap();
		model.put(AbstractCommaSeparatedView.DATA, itemMap);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Future_Price_Change_Report");
		return new ModelAndView(new FuturePriceChangeExcelView(), model);
	}

	private Map<PriceBookHeader, List<FuturePriceChangeItem>> buildMap(final String orderGuide) {
		final Map<PriceBookHeader, List<FuturePriceChangeItem>> itemMap = 
			new HashMap<PriceBookHeader, List<FuturePriceChangeItem>>();
		final List<FuturePriceChangeItem> items = this.itemService.getFuturePriceChangeReport(orderGuide);
		final Set<PriceBookHeader> headerSet = new TreeSet<PriceBookHeader>();
		
		for(FuturePriceChangeItem item : items){
			final PriceBookHeader header = this.priceBookService.getPriceBookHeader(item.getPriceBookHeaderCode().trim());
			headerSet.add(header);
		}
		
		final Iterator<PriceBookHeader> iter = headerSet.iterator();
		while(iter.hasNext()){
			List<FuturePriceChangeItem> changeItems = new ArrayList<FuturePriceChangeItem>();
			PriceBookHeader header = iter.next();
			
			for(FuturePriceChangeItem i : items){
				if(i.getPriceBookHeaderCode().trim().equalsIgnoreCase(header.getHeaderCode().trim())){
					changeItems.add(i);
				}
			}
			
//			Collections.sort(changeItems);
			itemMap.put(header, changeItems);
		}

		return itemMap;
	}
}
