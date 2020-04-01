package com.parksexpress.web.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.User;
import com.parksexpress.domain.item.HistoryItem;
import com.parksexpress.services.TransactionHistoryService;
import com.parksexpress.util.DateUtil;

public class HistoryController extends ParksController {
	private TransactionHistoryService historyService;
	
	public HistoryController(){}
		
	public void setTransactionHistoryService(TransactionHistoryService historyService){
		this.historyService = historyService;
	}
	
	public ModelAndView getSevenDayHistory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final User user = this.getUser(request);
		final String chain = user.getStores().get(0).getChainCode();
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		final String startDate = sdf.format(cal.getTime());
		
		final List<HistoryItem> list = this.historyService.getTransactionHistory(startDate, 15, chain, user.getId());
		return new ModelAndView("shortHistory", "items", list);
	}
	
	public ModelAndView history(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final User user = this.getUser(request);
		final String endDate = request.getParameter("endDate");
		final String startDate = request.getParameter("startDate");
		final String chain = user.getStores().get(0).getChainCode();
		final List<HistoryItem> list = this.historyService.getTransactionHistory(
				DateUtil.convertToMySQLDate(startDate), DateUtil.convertToMySQLDate(endDate), 
				chain, user.getId());
		
		return new ModelAndView("shortHistory", "items", list);
	}
	
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("history");
	}
	
	public ModelAndView showAdmin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("historyAdmin");
	}
	
	public ModelAndView adminHistory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final User user = this.getUser(request);
		final String endDate = request.getParameter("endDate");
		final String startDate = request.getParameter("startDate");
		final String chain = request.getParameter("chain");
		
		final List<HistoryItem> list = this.historyService.getTransactionHistory(
				DateUtil.convertToMySQLDate(startDate), DateUtil.convertToMySQLDate(endDate), 
				chain, user.getId());
		
		return new ModelAndView("shortHistory", "items", list);
	}
}
