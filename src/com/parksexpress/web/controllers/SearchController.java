package com.parksexpress.web.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.parksexpress.domain.Chain;
import com.parksexpress.domain.SearchResult;
import com.parksexpress.domain.Store;
import com.parksexpress.domain.item.Item;
import com.parksexpress.services.ChainService;
import com.parksexpress.services.ItemService;
import com.parksexpress.services.StoreService;

public class SearchController extends MultiActionController {
	private ChainService chainService;
	private StoreService storeService;
	private ItemService itemService;
	
	public SearchController(){}
	
	public void setChainService(final ChainService chainService){
		this.chainService = chainService;
	}
	
	public void setStoreService(final StoreService storeService){
		this.storeService = storeService;
	}
	
	public void setItemService(final ItemService itemService){
		this.itemService = itemService;
	}
	
	public ModelAndView findItem(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		String searchCriteria = request.getParameter("criteria").toUpperCase();
		
		if(StringUtils.isEmpty(searchCriteria) || searchCriteria.contains("DELETE") || searchCriteria.contains("UPDATE")){
			return null;
		}
		
		try{
			Integer.parseInt(searchCriteria);
			
			if(searchCriteria.length() == Item.ITEM_NUMBER_LENGTH + 1){
				searchCriteria = Item.getItemNumberFromCheckDigit(searchCriteria);
			}
		}catch(Exception e){
		}

		String s = searchCriteria.replace("*", "%");
		List<Item> list = itemService.find(s);
		
		ModelAndView view = new ModelAndView("findSearchItems");
		view.addObject("items", list);
		return view;
	}
	
	public ModelAndView search(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		final String criteria = request.getParameter("criteria");
		
		final List<SearchResult> list = new ArrayList<SearchResult>();
		final List<Chain> chains = this.chainService.search(criteria);
		final List<Store> stores = this.storeService.search(criteria);
		
		for(Chain chain : chains){
			list.add(new SearchResult(chain.getName(), chain.getNumber(), "chain"));
		}
		
		for(Store store : stores){
			list.add(new SearchResult(store.getName(), store.getNumber(), "store"));
		}
		
		return new ModelAndView("chainStoreResults", "results", list);
	}
}
