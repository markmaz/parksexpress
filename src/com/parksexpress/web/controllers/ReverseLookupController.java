package com.parksexpress.web.controllers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.as400.util.DateChanger;
import com.parksexpress.domain.Chain;
import com.parksexpress.domain.PriceBookClass;
import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.Store;
import com.parksexpress.domain.User;
import com.parksexpress.domain.Vendor;
import com.parksexpress.domain.item.Item;
import com.parksexpress.domain.item.ReverseLookupItem;
import com.parksexpress.exceptions.InvalidStoreException;
import com.parksexpress.services.ChainService;
import com.parksexpress.services.ItemService;
import com.parksexpress.services.PriceBookService;
import com.parksexpress.services.ReverseLookupService;
import com.parksexpress.services.VendorService;
import com.parksexpress.views.pdf.AbstractParksexpressPrintView;
import com.parksexpress.views.pdf.ReverseLookupPrintSummaryView;
import com.parksexpress.views.pdf.ReverseLookupPrintView;
import com.parksexpress.views.xls.AbstractExcelView;
import com.parksexpress.views.xls.ReverseDetailExcelView;
import com.parksexpress.views.xls.ReverseExcelView;

public class ReverseLookupController extends ParksController {
	private VendorService vendorService;
	private PriceBookService priceBookService;
	private ReverseLookupService reverseLookupService;
	private ItemService itemService;
	private ChainService chainService;
	
	private Logger log = Logger.getLogger(ReverseLookupController.class);
	
	public ReverseLookupController(){}
	
	public void setVendorService(VendorService vendorService){
		this.vendorService = vendorService;
	}
	
	public void setPriceBookService(PriceBookService priceBookService){
		this.priceBookService = priceBookService;
	}
	
	public void setReverseLookupService(ReverseLookupService reverseLookupService) {
		this.reverseLookupService = reverseLookupService;
	}
	
	public void setItemService(ItemService itemService){
		this.itemService = itemService;
	}
	
	public void setChainService(ChainService chainService){
		this.chainService = chainService;
	}
	
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.log.debug("Entering: show");

		final List<Vendor> vendors = this.vendorService.getAllVendors();
		this.log.debug("Vendor list size: " + vendors.size());
	
		final List<PriceBookHeader> headers = this.priceBookService.getPriceBookHeaders();
		this.log.debug("Header size: " + headers.size());
		
		final List<PriceBookClass> classes = this.priceBookService.getPriceBookClasses();
		this.log.debug("Class list size: " + classes.size());
		
		final List<PriceBookFamily> families = this.priceBookService.getPriceBookFamilies();
		this.log.debug("Familiy size: " + families.size());
		
		final List<String> brands = this.itemService.getAllBrands();
		this.log.debug("Brand size: " + brands.size());
		
		final ModelAndView view = new ModelAndView("reverseLookupDisplay");
		view.addObject("headers", headers);
		view.addObject("vendors", vendors);
		view.addObject("families", families);
		view.addObject("classes", classes);
		view.addObject("brands", brands);
		
		final User user = this.getUser(request);
		final List<Store> stores = user.getStoresWithoutZones();
		view.addObject("stores", stores);
		view.addObject("chain", this.chainService.getChain(stores.get(0).getChainCode()));
		
		this.log.debug("Exiting: show");
		return view;
	}
	
	public ModelAndView lookUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String startDate = request.getParameter("startDate");
		this.log.debug("Start Date: " + startDate);
		
		final String endDate = request.getParameter("endDate");
		this.log.debug("End Date: " + endDate);
		
		if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)){
			this.log.fatal("Start Date or End Date is null or empty");
			return new ModelAndView("error", "message", "The start date or end date is empty or null.");
		}
		
		try{
			final List<ReverseLookupItem> list = this.determineReportTypeAndRun(request);
			final String store = request.getParameter("store");
			final User user = this.getUser(request);
			
			final BigDecimal totalRetail = getTotalRetail(list);
			final BigDecimal totalCost = getTotalCost(list);
			final String totalUnits = getTotalUnits(list);
			
			if(store.equalsIgnoreCase(user.getStoresWithoutZones().get(0).getChainCode())){
				ModelAndView view = new ModelAndView("reverseLookupChainData", "data", list);
				view.addObject("totalCost", totalCost);
				view.addObject("totalRetail", totalRetail);
				view.addObject("totalUnits", totalUnits);
				return view;
			}else{
				ModelAndView view = new ModelAndView("reverseLookupData", "data", list);;
				view.addObject("totalCost", totalCost);
				view.addObject("totalRetail", totalRetail);
				view.addObject("totalUnits", totalUnits);
				return view;
			}

		}catch(InvalidStoreException e){
			return new ModelAndView("error", "message", e.getMessage());
		}
	}
	
	private List<ReverseLookupItem> determineReportTypeAndRun(HttpServletRequest request) throws ParseException, InvalidStoreException{
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String sortOrder = request.getParameter("sort");
		
		startDate = DateChanger.convertDateToAS400(startDate);
		endDate = DateChanger.convertDateToAS400(endDate);
		
		final String store = request.getParameter("store");
		final String[] stores;
		
		final User user = this.getUser(request);
		boolean isSummary = false;
		
		if(store.equalsIgnoreCase(user.getStoresWithoutZones().get(0).getChainCode())){
			//stores = this.getStoreNumbers(user.getStoresWithoutZones());
			stores = new String[]{store};
			isSummary = true;
		}else{
			stores = new String[]{store};
		}
		
		return this.getData(request, startDate, endDate, stores, isSummary, Integer.parseInt(sortOrder));
	}
	
	private List<ReverseLookupItem> getData(HttpServletRequest request, String startDate, String endDate, 
			final String[] stores, boolean isSummary, int sortOrder) {
		final String itemNumber= request.getParameter("item");
		this.log.debug("Item: " + itemNumber);
		
		final String priceBookHeader = request.getParameter("header");
		this.log.debug("Price Book Header: " + priceBookHeader);
		
		final String priceBookClass = request.getParameter("class");
		this.log.debug("Price Book Class: " + priceBookClass);
		
		final String priceBookFamily = request.getParameter("family");
		this.log.debug("Price Book family: " + priceBookFamily);
		
		final String vendorCode = request.getParameter("vendor");
		this.log.debug("Vendor: " + vendorCode);
		
		final String brand = request.getParameter("brand");
		this.log.debug("Brand: " + brand);
		
		if(StringUtils.isNotEmpty(itemNumber)){
			List<Item> itemList = new ArrayList<Item>();
			String[] items = StringUtils.split(itemNumber, ",");
			
			for(int i=0; i < items.length; i++){
				final Item item = this.itemService.getItem(items[i].trim());
				itemList.add(item);
			}
			
			return this.reverseLookupService.lookUp(startDate, endDate, stores, itemList, isSummary, sortOrder);
		}
		
		if(!priceBookHeader.equalsIgnoreCase("-1")){
			final PriceBookHeader header = this.priceBookService.getPriceBookHeader(priceBookHeader);
			return this.reverseLookupService.lookUp(startDate, endDate, stores, header, isSummary, sortOrder);
		}
		
		if(!priceBookClass.equalsIgnoreCase("-1")){
			final PriceBookClass clazz = this.priceBookService.getPriceBookClass(priceBookClass);
			return this.reverseLookupService.lookUp(startDate, endDate, stores, clazz, isSummary, sortOrder);
		}
		
		if(!priceBookFamily.equalsIgnoreCase("-1")){
			final PriceBookFamily family = this.priceBookService.getPriceBookFamily(priceBookFamily);
			return this.reverseLookupService.lookUp(startDate, endDate, stores, family, isSummary, sortOrder);
		}
		
		if(StringUtils.isNotEmpty(vendorCode)){
			String[] vendorInfo = StringUtils.split(vendorCode, "-");
			final Vendor vendor = this.vendorService.getVendorByNumber(vendorInfo[0].trim());
			return this.reverseLookupService.lookUp(startDate, endDate, stores, vendor, isSummary, sortOrder);
		}
		
		if(!brand.equalsIgnoreCase("-1")){
			return this.reverseLookupService.lookUp(startDate, endDate, stores, brand, isSummary, sortOrder);
		}
		
		return this.reverseLookupService.lookUp(startDate, endDate, stores, isSummary, sortOrder);
	}

	private String[] getStoreNumbers(List<Store> stores) {
		final List<String> storeNumbers = new ArrayList<String>();
		
		for(Store store : stores){
			storeNumbers.add(store.getNumber());
		}
		return storeNumbers.toArray(new String[storeNumbers.size()]);
	}

	private String getReportCriteria(HttpServletRequest request){
		final String itemNumber= request.getParameter("item");
		final String priceBookHeader = request.getParameter("header");
		final String priceBookClass = request.getParameter("class");
		final String priceBookFamily = request.getParameter("family");
		final String vendorCode = request.getParameter("vendor");
		final String brand = request.getParameter("brand");
		
		if(StringUtils.isNotEmpty(itemNumber)){
			return "Item Number: " + itemNumber;
		}
		
		if(!priceBookHeader.equalsIgnoreCase("-1")){
			final PriceBookHeader header = this.priceBookService.getPriceBookHeader(priceBookHeader);
			return "Price Book Header: " + header.getDescription();
		}
		
		if(!priceBookClass.equalsIgnoreCase("-1")){
			final PriceBookClass clazz = this.priceBookService.getPriceBookClass(priceBookClass);
			return "Price Book Class: " + clazz.getDescription();
		}
		
		if(!priceBookFamily.equalsIgnoreCase("-1")){
			final PriceBookFamily family = this.priceBookService.getPriceBookFamily(priceBookFamily);
			return "Price Book Family: " + family.getDescription();
		}
		
		if(!vendorCode.equalsIgnoreCase("-1")){
			final Vendor vendor = this.vendorService.getVendorByNumber(vendorCode);
			return "Vendor: " + vendor.getName();
		}
		
		if(!brand.equalsIgnoreCase("-1")){
			return "Brand: " ;
		}
		
		return "";
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView print(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String startDate = request.getParameter("startDate");
		this.log.debug("Start Date: " + startDate);
		
		final String endDate = request.getParameter("endDate");
		this.log.debug("End Date: " + endDate);
		
		final String sortOrder = request.getParameter("sort");
		
		if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate) || StringUtils.isEmpty(sortOrder)){
			this.log.fatal("Start Date or End Date is null or empty");
			return new ModelAndView("error", "message", "The start date or end date is empty or null.");
		}
		
		final List<ReverseLookupItem> list = this.determineReportTypeAndRun(request);
		final Map model = new HashMap();
		final User user = this.getUser(request);
		final String store = request.getParameter("store");
		String customerName = "";
		String title = "";
		
		if(store.equalsIgnoreCase(user.getStoresWithoutZones().get(0).getChainCode())){
			final Chain chain = this.chainService.getChain(user.getStores().get(0).getChainCode());
			customerName = chain.getName();
			title = "Reverse Lookup - Summary";
		}else{
			final Store validStore = user.getStore(store);
			
			if(validStore != null){
				customerName = validStore.getName();
			}else{
				customerName = "Unknown Store";
			}
			
			title = "Reverse Lookup";
		}
		
		
		model.put(AbstractParksexpressPrintView.DATA, list);
		model.put(AbstractParksexpressPrintView.START_DATE, startDate);
		model.put(AbstractParksexpressPrintView.END_DATE, endDate);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, title);
		model.put(AbstractParksexpressPrintView.CUSTOMER_INFO, customerName);
		model.put(AbstractParksexpressPrintView.ADDITIONAL_CRITERIA, this.getReportCriteria(request));
		model.put(ReverseLookupPrintView.TOTAL_COST, getTotalCost(list));
		model.put(ReverseLookupPrintView.TOTAL_UNITS, getTotalUnits(list));
		model.put(ReverseLookupPrintView.TOTAL_RETAIL, getTotalRetail(list));
		
		return new ModelAndView(new ReverseLookupPrintView(), model);
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView printSummary(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		startDate = DateChanger.convertDateToAS400(startDate);
		endDate = DateChanger.convertDateToAS400(endDate);
		
		final String sortOrder = request.getParameter("sort");
		
		if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate) || StringUtils.isEmpty(sortOrder)){
			this.log.fatal("Start Date or End Date is null or empty");
			return new ModelAndView("error", "message", "The start date or end date is empty or null.");
		}

		final User user = this.getUser(request);
		final Chain chain = this.chainService.getChain(user.getStores().get(0).getChainCode());
		
		final String[] stores = this.getStoreNumbers(user.getStoresWithoutZones());
		
		final List<ReverseLookupItem> list = this.getData(request, startDate, endDate, stores, false, Integer.parseInt(sortOrder));
		final Map model = new HashMap();
		
		model.put(AbstractParksexpressPrintView.DATA, this.formatData(list));
		model.put(AbstractParksexpressPrintView.START_DATE, startDate);
		model.put(AbstractParksexpressPrintView.END_DATE, endDate);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Reverse Lookup - All Stores");
		model.put(AbstractParksexpressPrintView.CUSTOMER_INFO, chain.getName());
		model.put(AbstractParksexpressPrintView.ADDITIONAL_CRITERIA, this.getReportCriteria(request));
		
		return new ModelAndView(new ReverseLookupPrintSummaryView(), model);
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView export(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String startDate = request.getParameter("startDate");
		this.log.debug("Start Date: " + startDate);
		
		final String endDate = request.getParameter("endDate");
		this.log.debug("End Date: " + endDate);
		
		if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)){
			this.log.fatal("Start Date or End Date is null or empty");
			return new ModelAndView("error", "message", "The start date or end date is empty or null.");
		}
		
		final List<ReverseLookupItem> list = this.determineReportTypeAndRun(request);
		final Map model = new HashMap();
		model.put(AbstractExcelView.DATA, list);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Reverse_Lookup");
		model.put(ReverseExcelView.TOTAL_COST, getTotalCost(list));
		model.put(ReverseExcelView.TOTAL_RETAIL, getTotalRetail(list));
		model.put(ReverseExcelView.TOTAL_UNITS, getTotalUnits(list));
		return new ModelAndView(new ReverseExcelView(), model);
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView exportDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String startDate = request.getParameter("startDate");
		this.log.debug("Start Date: " + startDate);
		
		String endDate = request.getParameter("endDate");
		this.log.debug("End Date: " + endDate);
		
		startDate = DateChanger.convertDateToAS400(startDate);
		endDate = DateChanger.convertDateToAS400(endDate);
		final String sortOrder = request.getParameter("sort");
		
		if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)){
			this.log.fatal("Start Date or End Date is null or empty");
			return new ModelAndView("error", "message", "The start date or end date is empty or null.");
		}
		
		final User user = this.getUser(request);
		final Chain chain = this.chainService.getChain(user.getStores().get(0).getChainCode());
		
		final String[] stores = this.getStoreNumbers(user.getStoresWithoutZones());
		
		final List<ReverseLookupItem> list = this.getData(request, startDate, endDate, stores, false, Integer.parseInt(sortOrder));
		
		//final List<ReverseLookupItem> list = this.determineReportTypeAndRun(request);
		final Map model = new HashMap();
		model.put(AbstractExcelView.DATA, list);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Reverse_Lookup");
		return new ModelAndView(new ReverseDetailExcelView(), model);
	}
	
	private Map<String, List<ReverseLookupItem>> formatData(List<ReverseLookupItem> items){
		final Map<String, List<ReverseLookupItem>> map = new HashMap<String, List<ReverseLookupItem>>();
		String oldStore = "";
		List<ReverseLookupItem> list = new ArrayList<ReverseLookupItem>();
		
		for(ReverseLookupItem item : items){
			
			if(item.getCustomerName().trim().equalsIgnoreCase(oldStore)){
				list.add(item);
			}else{
				if(StringUtils.isBlank(oldStore)){
					oldStore = item.getCustomerName().trim();
					list.add(item);
				}else{
    					map.put(oldStore, list);
    					oldStore = item.getCustomerName().trim();
    					list = new ArrayList<ReverseLookupItem>();
    					list.add(item);
				}
			}
		}
		
		map.put(oldStore, list);
		return map;
	}
	
	private String getTotalUnits(List<ReverseLookupItem> items){
		long total = 0;
		
		for(ReverseLookupItem item : items){
			total += item.getQuantity();
		}
		
		return total + "";
	}
	
	private BigDecimal getTotalCost(List<ReverseLookupItem> items){
		BigDecimal amount = new BigDecimal(0);
		
		for(ReverseLookupItem item : items){
			amount = amount.add(item.getCostAmount());
		}
		
		return amount;
	}
	
	private BigDecimal getTotalRetail(List<ReverseLookupItem> items){
		BigDecimal amount = new BigDecimal(0);
		
		for(ReverseLookupItem item : items){
			amount = amount.add(item.getSrpAmount());
		}
		
		return amount;
	}
}
