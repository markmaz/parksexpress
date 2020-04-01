package com.parksexpress.web.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.Chain;
import com.parksexpress.domain.User;
import com.parksexpress.domain.item.AllowanceItem;
import com.parksexpress.domain.item.Item;
import com.parksexpress.services.ChainService;
import com.parksexpress.services.ItemService;
import com.parksexpress.views.pdf.AbstractParksexpressPrintView;
import com.parksexpress.views.pdf.DefaultHotSheetPrintView;
import com.parksexpress.views.pdf.ItemAllowancePrintView;
import com.parksexpress.views.xls.AbstractExcelView;
import com.parksexpress.views.xls.DefaultHotSheetExcelView;
import com.parksexpress.views.xls.ItemAllowanceExcelView;

public class HotSheetController extends ParksController {
	private ItemService itemService;
	private ChainService chainService;
	
	public HotSheetController(){}
	
	public void setItemService(final ItemService itemService){
		this.itemService = itemService;
	}
	
	public void setChainService(final ChainService chainService){
		this.chainService = chainService;
	}
	
	public ModelAndView hotSheet(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		return new ModelAndView("hotSheet");
	}
	
	public ModelAndView itemAllowances(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		final String currDate = sdf.format(Calendar.getInstance().getTime());
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		final String startDate = sdf.format(cal.getTime());
		
		final ModelAndView view = new ModelAndView("itemAllowances");
		view.addObject("startDate", startDate);
		view.addObject("endDate", currDate);
		return view;
	}
	
	public ModelAndView allowanceData(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String endDate = request.getParameter("endDate");
		final String startDate = request.getParameter("startDate");
		final String number = request.getParameter("number");
		final String type = request.getParameter("type");
		
		final List<AllowanceItem> list = this.itemService.getAllowances(startDate, endDate, number, type);
		final ModelAndView view = new ModelAndView("itemAllowanceData");
		view.addObject("itemAllowances", list);
		view.addObject("size", list.size());
		return view;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView printItemAllowance(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String endDate = request.getParameter("endDate");
		final String startDate = request.getParameter("startDate");
		final String number = request.getParameter("number");
		final String type = request.getParameter("type");

		final Map model = new HashMap();
		final List<AllowanceItem> list = this.itemService.getAllowances(startDate, endDate, number, type);
		model.put(AbstractParksexpressPrintView.DATA, list);
		model.put(AbstractParksexpressPrintView.END_DATE, endDate);
		model.put(AbstractParksexpressPrintView.START_DATE, startDate);
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		final Chain chain = this.chainService.getChain(user.getStores().get(0).getChainCode());
		model.put(AbstractParksexpressPrintView.CUSTOMER_INFO, chain.getName());
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Item Allowances");
		final ModelAndView view = new ModelAndView(new ItemAllowancePrintView(), model);
		return view;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView exportItemAllowance(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String endDate = request.getParameter("endDate");
		final String startDate = request.getParameter("startDate");
		final String number = request.getParameter("number");
		final String type = request.getParameter("type");

		final Map model = new HashMap();
		final List<AllowanceItem> list = this.itemService.getAllowances(startDate, endDate, number, type);
		model.put(AbstractExcelView.DATA, list);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Item_Allowance");
		final ModelAndView view = new ModelAndView(new ItemAllowanceExcelView(), model);
		return view;
	}	
	
	public ModelAndView newItems(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		final String currDate = sdf.format(Calendar.getInstance().getTime());
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -60);
		final String startDate = sdf.format(cal.getTime());
		
		final ModelAndView view = new ModelAndView("newItems");
		view.addObject("startDate", startDate);
		view.addObject("endDate", currDate);
		return view;
	}
	
	public ModelAndView newItemData(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String endDate = request.getParameter("endDate");
		final String startDate = request.getParameter("startDate");
		final String number = request.getParameter("number");
		final String type = request.getParameter("type");
		
		final List<Item> list = this.itemService.getNewItems(startDate, endDate, number, type);
		final ModelAndView view = new ModelAndView("newItemData");
		view.addObject("newItems", list);
		view.addObject("size", list.size());
		return view;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView exportNewItems(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String endDate = request.getParameter("endDate");
		final String startDate = request.getParameter("startDate");
		final String number = request.getParameter("number");
		final String type = request.getParameter("type");
		
		final List<Item> list = this.itemService.getNewItems(startDate, endDate, number, type);
		
		final Map model = new HashMap();
		model.put(AbstractExcelView.DATA, list);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "New_Items");
		final ModelAndView view = new ModelAndView(new DefaultHotSheetExcelView(), model);
		return view;		
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView printNewItems(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String endDate = request.getParameter("endDate");
		final String startDate = request.getParameter("startDate");
		final String number = request.getParameter("number");
		final String type = request.getParameter("type");
		
		final List<Item> list = this.itemService.getNewItems(startDate, endDate, number, type);
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		final Chain chain = this.chainService.getChain(user.getStores().get(0).getChainCode());
		
		final Map model = new HashMap();
		model.put(AbstractParksexpressPrintView.START_DATE, startDate);
		model.put(AbstractParksexpressPrintView.END_DATE, endDate);
		model.put(AbstractParksexpressPrintView.CUSTOMER_INFO, chain.getName());
		model.put(AbstractParksexpressPrintView.DATA, list);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "New Items");
		final ModelAndView view = new ModelAndView(new DefaultHotSheetPrintView(), model);
		return view;
	}	
	
	public ModelAndView discontinuedItems(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		final String currDate = sdf.format(Calendar.getInstance().getTime());
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -60);
		final String startDate = sdf.format(cal.getTime());
		
		final ModelAndView view = new ModelAndView("discontinuedItems");
		view.addObject("startDate", startDate);
		view.addObject("endDate", currDate);
		return view;
	}
	
	public ModelAndView discontinuedItemData(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String endDate = request.getParameter("endDate");
		final String startDate = request.getParameter("startDate");
		final String number = request.getParameter("number");
		final String type = request.getParameter("type");
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		
		final List<Item> list = this.itemService.getDiscontinuedItems(startDate, endDate, number, type, user.getOrderGuideNumber());
		final ModelAndView view = new ModelAndView("discontinuedItemData");
		
		view.addObject("discontinuedItems", list);
		view.addObject("size", list.size());
		return view;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView printDiscontinuedItems(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String endDate = request.getParameter("endDate");
		final String startDate = request.getParameter("startDate");
		final String number = request.getParameter("number");
		final String type = request.getParameter("type");
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		
		final List<Item> list = this.itemService.getDiscontinuedItems(startDate, endDate, number, type, user.getOrderGuideNumber());
		final Chain chain = this.chainService.getChain(user.getStores().get(0).getChainCode());
		
		final Map model = new HashMap();
		model.put(AbstractParksexpressPrintView.START_DATE, startDate);
		model.put(AbstractParksexpressPrintView.END_DATE, endDate);
		model.put(AbstractParksexpressPrintView.CUSTOMER_INFO, chain.getName());
		model.put(AbstractParksexpressPrintView.DATA, list);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Discontinued Items");
		
		final ModelAndView view = new ModelAndView(new DefaultHotSheetPrintView(), model);
		return view;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView exportDiscontinuedItems(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String endDate = request.getParameter("endDate");
		final String startDate = request.getParameter("startDate");
		final String number = request.getParameter("number");
		final String type = request.getParameter("type");
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		final List<Item> list = this.itemService.getDiscontinuedItems(startDate, endDate, number, type, user.getOrderGuideNumber());
		final Map model = new HashMap();
		model.put(AbstractParksexpressPrintView.DATA, list);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Discontinued_Items");
		final ModelAndView view = new ModelAndView(new DefaultHotSheetExcelView(), model);
		return view;
	}
	
	public ModelAndView suspendedItems(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final ModelAndView view = new ModelAndView("suspendedItems");
		return view;
	}
	
	public ModelAndView suspendedItemData(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		
		final List<Item> list = this.itemService.getSuspendedItems(user.getOrderGuideNumber());
		final ModelAndView view = new ModelAndView("suspendedItemData");
		view.addObject("suspendedItems", list);
		view.addObject("size", list.size());
		return view;
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView printSuspendedItems(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		
		final List<Item> list = this.itemService.getSuspendedItems(user.getOrderGuideNumber());
		final Chain chain = this.chainService.getChain(user.getStores().get(0).getChainCode());
		
		final Map model = new HashMap();
		model.put(AbstractParksexpressPrintView.CUSTOMER_INFO, chain.getName());
		model.put(AbstractParksexpressPrintView.DATA, list);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Suspended Items");
		
		final ModelAndView view = new ModelAndView(new DefaultHotSheetPrintView(), model);
		return view;
	}	
	
	@SuppressWarnings("unchecked")
	public ModelAndView exportSuspendedItems(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final User user = (User)request.getSession().getAttribute("authenticatedUser");
		
		final List<Item> list = this.itemService.getSuspendedItems(user.getOrderGuideNumber());
		final Map model = new HashMap();
		model.put(AbstractExcelView.DATA, list);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Suspended_Items");
		final ModelAndView view = new ModelAndView(new DefaultHotSheetExcelView(), model);
		return view;
	}		
}
