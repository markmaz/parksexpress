package com.parksexpress.web.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.Chain;
import com.parksexpress.domain.Pricing;
import com.parksexpress.domain.RoundingCode;
import com.parksexpress.domain.ShelfTag;
import com.parksexpress.domain.Store;
import com.parksexpress.domain.User;
import com.parksexpress.domain.item.Item;
import com.parksexpress.services.ChainService;
import com.parksexpress.services.ItemService;
import com.parksexpress.services.PricingService;
import com.parksexpress.services.ShelfTagService;
import com.parksexpress.services.StoreService;
import com.parksexpress.services.TransactionHistoryService;
import com.parksexpress.util.DateUtil;

public class ShelfTagController extends ParksController {
	private ShelfTagService shelfTagService;
	private ItemService itemService;
	private ChainService chainService;
	private PricingService pricingService;
	private TransactionHistoryService historyService;
	private StoreService storeService;
	
	public ShelfTagController() {
	}

	public ModelAndView show(final HttpServletRequest request, final HttpServletResponse reponse) throws Exception {
		final User user = this.getUser(request);
		final Chain chain = this.chainService.getChain(user.getStores().get(0).getChainCode());
		
		final ModelAndView view = new ModelAndView("shelfTagRequest", "stores", user.getStoresWithoutZones());
		view.addObject("chain", chain);
		return view;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView requestShelfTag(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String itemNumber = request.getParameter("item");
		final String storeOrChain = request.getParameter("store");
		final User user = this.getUser(request);

		List<ShelfTag> shelfTagList = (List<ShelfTag>) request.getSession().getAttribute("shelfTagList");

		final ShelfTag tag = this.createShelfTag(user, itemNumber, storeOrChain);

		if (shelfTagList == null) {
			shelfTagList = new ArrayList<ShelfTag>();
		}

		if (!shelfTagList.contains(tag)) {
			shelfTagList.add(tag);
		}
		request.getSession().setAttribute("shelfTagList", shelfTagList);

		return new ModelAndView("shelfTagData");
	}

	@SuppressWarnings("unchecked")
	public ModelAndView removeShelfTag(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final String itemNumber = request.getParameter("item");

		final List<ShelfTag> shelfTagList = (List<ShelfTag>) request.getSession().getAttribute("shelfTagList");

		if (shelfTagList == null) {
			return new ModelAndView("shelfTagData");
		}
		
		for(ShelfTag tag : shelfTagList){
			if(tag.getItemNumber().equals(itemNumber)){
				shelfTagList.remove(tag);
				break;
			}
		}
		
		request.getSession().setAttribute("shelfTagList", shelfTagList);
		return new ModelAndView("shelfTagData");
	}
	
	@SuppressWarnings("unchecked")
	public ModelAndView submit(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final List<ShelfTag> shelfTagList = (List<ShelfTag>) request.getSession().getAttribute("shelfTagList");
		String message = "";
		final ModelAndView view = new ModelAndView("shelfTagSubmitted");

		if (shelfTagList == null) {
			message = "No shelf tags have been submitted - cause there weren't any.";
			view.addObject("message", message);
			return view;
		}
		
		final User user = this.getUser(request);
		final String chainCode = user.getStores().get(0).getChainCode();
		final Long userID = user.getId();
		
		for (ShelfTag tag : shelfTagList) {
			this.shelfTagService.requestShelfTag(tag);
			this.historyService.addTransaction(DateUtil.convertToMySQLDate(DateUtil.today()), chainCode, 
					this.getDetails(tag), userID);
		}

		if (shelfTagList.size() > 1) {
			message = shelfTagList.size() + " shelf labels have been submitted.";
		} else {
			message = "Your shelf label has been submitted.";
		}

		view.addObject("message", message);
		return view;
	}

	private String getDetails(final ShelfTag tag){
		final StringBuffer text = new StringBuffer("Shelf tag requested for ");
		
		if(tag.getCustomerType().equalsIgnoreCase("C")){
			text.append("All Stores");
		}else{
			final Store store = this.storeService.getStore(tag.getStoreOrChainNumber());
			text.append(store.getName());
		}
		
		text.append(" for item - " + tag.getItemNumber() + ".");
		
		return text.toString();
	}
	
	private ShelfTag createShelfTag(User user, String itemNumber, String chainOrStore) throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		final String today = sdf.format(Calendar.getInstance().getTime());
		final Item item = this.itemService.getItem(itemNumber);

		final ShelfTag tag = new ShelfTag();
		tag.setEffectiveDate(today);
		tag.setEndDate("20991231");

		final String chainCode = user.getStores().get(0).getChainCode();
		if (chainCode.equalsIgnoreCase(chainOrStore)) {
			tag.setCustomerType("C");
			tag.setStoreOrChainName(this.chainService.getChain(chainCode).getName());
		} else {
			tag.setCustomerType("S");
			tag.setStoreOrChainName(this.getStoreName(user.getStores(), chainOrStore));
		}
		tag.setStoreOrChainNumber(chainOrStore);

		final RoundingCode roundingCode = this.chainService.getRoundingCode(chainCode);
		tag.setItemType("I");
		tag.setItemNumber(itemNumber);
		tag.setItem(item);
		tag.setPack(item.getPack());
		tag.setRoundingCode(roundingCode.getCode());
		tag.setUser(user.getUsername());

		final Pricing pricing = this.pricingService.getPricing(itemNumber, chainOrStore);
		tag.setPrice(pricing.getCost().toPlainString());
		tag.setPercent(pricing.getPrice().toPlainString());
		return tag;
	}

	private String getStoreName(List<Store> stores, String storeNumber) {
		for (Store store : stores) {
			if (store.getNumber().equalsIgnoreCase(storeNumber)) {
				return store.getName();
			}
		}

		return null;
	}

	public void setShelfTagService(final ShelfTagService shelfTagService) {
		this.shelfTagService = shelfTagService;
	}

	public void setItemService(final ItemService itemService) {
		this.itemService = itemService;
	}

	public void setChainService(final ChainService chainService) {
		this.chainService = chainService;
	}

	public void setPricingService(final PricingService pricingService) {
		this.pricingService = pricingService;
	}
	
	public void setTransactionHistoryService(final TransactionHistoryService historyService){
		this.historyService = historyService;
	}
	
	public void setStoreService(final StoreService storeService){
		this.storeService = storeService;
	}
}