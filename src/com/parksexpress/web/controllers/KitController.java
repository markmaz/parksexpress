package com.parksexpress.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.Kit;
import com.parksexpress.domain.SearchResult;
import com.parksexpress.domain.User;
import com.parksexpress.services.KitService;
import com.parksexpress.views.csv.AbstractCommaSeparatedView;
import com.parksexpress.views.pdf.AbstractParksexpressPrintView;
import com.parksexpress.views.pdf.KitPrintView;
import com.parksexpress.views.xls.KitExcelView;

public class KitController extends ParksController {
	private static final int MIN_LENGTH = 3;
	private KitService kitService;
	
	public KitController(){}
	
	public void setKitService(KitService kitService){
		this.kitService = kitService;
	}
	
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("kitComponent");
	}
	
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String kitNumber = request.getParameter("kit");
		final Kit kit = this.kitService.getKit(kitNumber);
		
		return new ModelAndView("kitComponentData", "kit", kit);
	}
	
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String criteria = request.getParameter("criteria");
		List<Kit> kits = new ArrayList<Kit>();
		final List<SearchResult> items = new ArrayList<SearchResult>();
		
		if(criteria.length() > KitController.MIN_LENGTH){
			kits = this.kitService.search(criteria);
		}
		
		for (Kit kit : kits) {
			items.add(new SearchResult(kit.getDescription(), kit.getNumber(),
					"kit"));
		}

		return new ModelAndView("srpSearchResults", "results", items);
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView print(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String kitNumber = request.getParameter("kit");
		final Kit kit = this.kitService.getKit(kitNumber);
		final User user = this.getUser(request);
		final Map model = new HashMap();
		model.put(AbstractParksexpressPrintView.DATA, kit);
		model.put(AbstractParksexpressPrintView.CUSTOMER_INFO, user.getStores().get(0).getChain().getName());
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Kit Components");
		return new ModelAndView(new KitPrintView(), model);
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView export(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String kitNumber = request.getParameter("kit");
		final Kit kit = this.kitService.getKit(kitNumber);
		final Map model = new HashMap();
		model.put(AbstractCommaSeparatedView.DATA, kit);
		model.put(AbstractParksexpressPrintView.REPORT_TITLE, "Kit_Components");
		return new ModelAndView(new KitExcelView(), model);
	}
}