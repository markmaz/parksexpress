 package com.parksexpress.web.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.User;
import com.parksexpress.domain.Zone;
import com.parksexpress.domain.item.PriceChangeItem;
import com.parksexpress.services.ItemService;
import com.parksexpress.services.PriceBookService;
import com.parksexpress.views.pdf.AbstractParksexpressPrintView;
import com.parksexpress.views.pdf.PriceChangePrintView;
import com.parksexpress.views.xls.AbstractExcelView;
import com.parksexpress.views.xls.PriceChangeExcelView;

public class PriceChangeController extends ParksController {
	private ItemService itemService;
	private PriceBookService priceBookService;
	
	public PriceChangeController(){}
	
	public void setItemService(final ItemService itemService){
		this.itemService = itemService;
	}
	
	public void setPriceBookService(final PriceBookService priceBookService){
		this.priceBookService = priceBookService;
	}
	
	public ModelAndView showPriceChangeReport(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		final String currDate = sdf.format(Calendar.getInstance().getTime());
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		final String startDate = sdf.format(cal.getTime());
		
		final User user = this.getUser(request);
		final List<Zone> zones = user.getZones();
		
		final ModelAndView view = new ModelAndView("priceChange");
		view.addObject("startDate", startDate);
		view.addObject("endDate", currDate);
		view.addObject("zone", zones.get(0).getNumber());
		view.addObject("zones", zones);
		return view;
	}
	
	public ModelAndView getPriceChangeReport(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String code = request.getParameter("code");
		final String codeType = request.getParameter("codeType");
		final String startDate = request.getParameter("startDate");
		final String endDate = request.getParameter("endDate");
		final String storeNumber = request.getParameter("store");
		
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		final Map<PriceBookHeader, List<PriceChangeItem>> itemMap = this.buildMap(startDate, endDate, 
				storeNumber, code, codeType, user);
		return new ModelAndView("priceChangeData", "itemMap", itemMap);
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView printPriceChangeReport(final HttpServletRequest request, 
			final HttpServletResponse response) throws Exception {	
		final String code = request.getParameter("code");
		final String codeType = request.getParameter("codeType");
		final String startDate = request.getParameter("startDate");
		final String endDate = request.getParameter("endDate");
		final String storeNumber = request.getParameter("store");
		
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		final Map<PriceBookHeader, List<PriceChangeItem>> itemMap = this.buildMap(startDate, endDate, 
				storeNumber, code, codeType, user);
		
		final Map model = new HashMap();

		model.put(AbstractParksexpressPrintView.CUSTOMER_INFO, storeNumber);
		model.put(AbstractParksexpressPrintView.DATA, itemMap);
		model.put(AbstractParksexpressPrintView.START_DATE, startDate);
		model.put(AbstractParksexpressPrintView.END_DATE, endDate);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Price Change Report");
		
		return new ModelAndView(new PriceChangePrintView(), model);
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView exportPriceChangeReport(final HttpServletRequest request, 
			final HttpServletResponse response) throws Exception {	
		final String code = request.getParameter("code");
		final String codeType = request.getParameter("codeType");
		final String startDate = request.getParameter("startDate");
		final String endDate = request.getParameter("endDate");
		final String storeNumber = request.getParameter("store");
		
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		final Map<PriceBookHeader, List<PriceChangeItem>> itemMap = this.buildMap(startDate, endDate, 
				storeNumber, code, codeType, user);
		
		final Map model = new HashMap();
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Price_Change_Report");
		model.put(AbstractExcelView.DATA, itemMap);
		return new ModelAndView(new PriceChangeExcelView(), model);
	}
	
	private Map <PriceBookHeader, List<PriceChangeItem>> buildMap(final String startDate, final String endDate, 
			final String storeNumber, final String code, final String codeType, final User user){
		
		final List<Zone> zones = user.getZones();
		final Map<PriceBookHeader, List<PriceChangeItem>> itemMap = new TreeMap<PriceBookHeader, List<PriceChangeItem>>();
		//Check to make sure user has access to this zone.
		for(Zone zone : zones){
			if(zone.getNumber().equals(storeNumber)){				
				final List<PriceChangeItem>items = this.itemService.getPriceChangeReport(startDate, endDate, 
						storeNumber, code, codeType, user.getOrderGuideNumber());
				final Set<PriceBookHeader> headerSet = new TreeSet<PriceBookHeader>();
				
				
				for(PriceChangeItem item : items){
					final PriceBookHeader header = this.priceBookService.getPriceBookHeader(item.getPriceBookHeaderCode().trim());
					headerSet.add(header);
				}
				
				final Iterator<PriceBookHeader> iter = headerSet.iterator();
				while(iter.hasNext()){
					final List<PriceChangeItem> changeItems = new ArrayList<PriceChangeItem>();
					final PriceBookHeader header = iter.next();
					
					for(PriceChangeItem i : items){
						if(i.getPriceBookHeaderCode().trim().equalsIgnoreCase(header.getHeaderCode().trim())){
							changeItems.add(i);
						}
					}
					
					Collections.sort(changeItems);
					itemMap.put(header, changeItems);
				}
				break;
			}
		}
		
		
		return itemMap;
	}
}
