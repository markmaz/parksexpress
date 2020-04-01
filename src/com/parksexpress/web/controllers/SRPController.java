package com.parksexpress.web.controllers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.parksexpress.domain.FamilyPricingException;
import com.parksexpress.domain.ItemPricingException;
import com.parksexpress.domain.PriceBook;
import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.Pricing;
import com.parksexpress.domain.SearchResult;
import com.parksexpress.domain.Store;
import com.parksexpress.domain.User;
import com.parksexpress.domain.Zone;
import com.parksexpress.domain.item.HistoryItem;
import com.parksexpress.domain.item.Item;
import com.parksexpress.services.ChainService;
import com.parksexpress.services.ItemService;
import com.parksexpress.services.PriceBookService;
import com.parksexpress.services.PricingService;
import com.parksexpress.services.StoreService;
import com.parksexpress.services.TransactionHistoryService;
import com.parksexpress.util.DateUtil;

public class SRPController extends ParksController {
	private ChainService chainService;
	private TransactionHistoryService historyService;
	private ItemService itemService;
	private Logger log = Logger.getRootLogger();
	private PriceBookService priceBookService;
	private PricingService pricingService;

	private StoreService storeService;

	public SRPController() {
	}

	@SuppressWarnings("unchecked")
	public ModelAndView suspendedItems(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final String code = request.getParameter("familyCode");
		String book = request.getParameter("book");
		String customerNumber = null;

		if (book.equalsIgnoreCase("undefined")) {
			book = ((PriceBook) ((List) request.getSession().getAttribute(
					"defaultZone")).get(0)).getNumber();
			customerNumber = ((PriceBook) ((List) request.getSession()
					.getAttribute("defaultZone")).get(0)).getCustomerNumber();
		} else {
			customerNumber = (this.priceBookService.getPriceBookByZone(book))
					.getCustomerNumber();
		}

		boolean isParksBook = false;
		if(book.equalsIgnoreCase("CP100") || book.equalsIgnoreCase("CP190")){
			isParksBook = true;
		}
		
		final PriceBookHeader pbh = this.priceBookService.getPriceBookHeader(
				code, 2);

		final User user = (User) request.getSession().getAttribute(
				"authenticatedUser");
		final String chainCode = user.getStores().get(0).getChainCode();

		PriceBookFamily family = null;

		// This defaults to only active items - need to get suspended and
		// discontinued.
		family = this.priceBookService.getPriceBookFamily(code);
		family.getItems().clear();
		family.setItems(this.itemService
				.getDiscontinuedAndSuspendedItemsByFamily(code));

		family.setPricing(this.pricingService.getFamilyPricing(chainCode,
				family.getFamilyCode(), book));
		family.setItems(this.pricingService.getPricingForItems(book, family,
				customerNumber));
		family.setItems(this.itemService.getItemsWithOrderGuide(chainCode,
				family));

		final ModelAndView view = new ModelAndView("srpSuspendedData",
				"priceBookHeader", pbh);
		view.addObject("family", family);
		view.addObject("isParksBook", isParksBook);
		return view;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView familyForm(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final String familyCode = request.getParameter("family");
		String book = request.getParameter("srpBook");
		final User user = (User) request.getSession().getAttribute(
				"authenticatedUser");
		final String chainCode = user.getStores().get(0).getChainCode();

		if (book.equalsIgnoreCase("undefined")) {
			book = ((PriceBook) ((List) request.getSession().getAttribute(
					"defaultZone")).get(0)).getNumber();
		}

		final PriceBook srpBook = this.priceBookService
				.getPriceBookByZone(book);
		final PriceBookFamily family = this.priceBookService
				.getPriceBookFamily(familyCode);
		family.setPricing(this.pricingService.getFamilyPricing(chainCode,
				familyCode, book));

		final List<FamilyPricingException> familyExceptions = this.priceBookService
				.getDetailPricingExceptions(chainCode, familyCode,
						srpBook.getPriority());
		final List<ItemPricingException> itemExceptions = this.priceBookService
				.getItemPriceBookExceptions(chainCode, familyCode);

		//final String today = DateUtil.tomorrow();
		final String today = DateUtil.today();
		
		final ModelAndView view = new ModelAndView("familySRPForm");
		view.addObject("family", family);
		view.addObject("familyExceptions", familyExceptions);
		view.addObject("itemExceptions", itemExceptions);
		view.addObject("today", today);
		return view;
	}

	public ChainService getChainService() {
		return this.chainService;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView getData(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final String type = request.getParameter("type");
		final String code = request.getParameter("id");
		String book = request.getParameter("book");
		String customerNumber = null;
		/*
		 * If the book comes in undefined - we get the default zone. We're
		 * getting the Zone Level from the page - i.e. RDR100 - we need to
		 * convert it to the customer number for the RPG program.
		 */
		if (book.equalsIgnoreCase("undefined")) {
			book = ((PriceBook) ((List) request.getSession().getAttribute(
					"defaultZone")).get(0)).getNumber();
			customerNumber = ((PriceBook) ((List) request.getSession()
					.getAttribute("defaultZone")).get(0)).getCustomerNumber();
		} else {
			customerNumber = (this.priceBookService.getPriceBookByZone(book))
					.getCustomerNumber();
		}

		boolean isParksBook = false;
		if(book.equalsIgnoreCase("CP100") || book.equalsIgnoreCase("CP190")){
			isParksBook = true;
		}
		
		int iType = this.processType(type);
		
		if(iType == -1){
			iType = 3;
		}
		//final PriceBookHeader pbh = this.priceBookService.getPriceBookHeader(code, iType);
		//final ModelAndView view = new ModelAndView("srpData","priceBookHeader", pbh);

		final ModelAndView view = new ModelAndView();
		final User user = (User) request.getSession().getAttribute(
				"authenticatedUser");
		final String chainCode = user.getStores().get(0).getChainCode();
		PriceBookFamily family = null;

		switch (iType) {
		case 0:
			view.setViewName("srpClass");
			break;
		case 1:
			view.setViewName("srpHeader");
			break;
		case 2:
			family = this.priceBookService.getPriceBookFamily(code);
			family.setPricing(this.pricingService.getFamilyPricing(chainCode,
					family.getFamilyCode(), book));
			family.setItems(this.pricingService.getPricingForItems(book,
					family, customerNumber));
			family.setItems(this.itemService.getItemsWithOrderGuide(chainCode,
					family));
			view.addObject("family", family);
			view.addObject("futureFamilyPricing", this.pricingService.getFuturePricingForFamily(family.getFamilyCode(), book));
			view.setViewName("srpFamily");
			break;
		case 3:
			final long time = System.currentTimeMillis();

			view.setViewName("srpItem");
			this.log.debug("getting item info");
			final Item item = this.itemService.getItem(code, chainCode);
//			this.log.debug("setting pricing info");
//			item.setPricing(this.pricingService.getPricing(customerNumber,
//					item.getNumber(), item.getPriceBookFamilyCode(), book,
//					chainCode));

			this.log.debug("starting family info");
			family = this.priceBookService.getPriceBookFamily(item
					.getPriceBookFamilyCode());
			family.setPricing(this.pricingService.getFamilyPricing(chainCode,
					family.getFamilyCode(), book));
			family.setItems(this.pricingService.getPricingForItems(book,
					family, customerNumber));
			family.setItems(this.itemService.getItemsWithOrderGuide(chainCode,
					family));

			this.log.debug("Time: " + (time - System.currentTimeMillis())
					/ 1000);
			
			for(Item famItem : family.getItems()){
				if(famItem.getNumber().equals(item.getNumber())){
					view.addObject("item", famItem);
				}
			}
			view.addObject("futureFamilyPricing", this.pricingService.getFuturePricingForFamily(family.getFamilyCode(), book));
			view.addObject("family", family);
			break;
		}

		view.addObject("isParksBook", isParksBook);
		return view;
	}

	public ModelAndView getStoresForZone(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final String zone = request.getParameter("zone");
		final List<Store> stores = this.storeService.getStoresForZone(zone);

		return new ModelAndView("zoneStores", "stores", stores);
	}

	public ModelAndView getTransactionHistory(final HttpServletRequest request,
			final HttpServletResponse response) {
		String date = request.getParameter("date");
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (StringUtils.isEmpty(date) || date.equalsIgnoreCase("undefined")) {
			date = sdf.format(Calendar.getInstance().getTime());
		} else {
			final SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
			try {
				date = sdf.format(sdf2.parse(date));
			} catch (ParseException e) {
				date = sdf.format(Calendar.getInstance().getTime());
			}
		}

		final User user = (User) request.getSession().getAttribute(
				"authenticatedUser");

		final List<HistoryItem> items = this.historyService
				.getTransactionHistory(date, user.getStores().get(0)
						.getChainCode(), user.getId());
		return new ModelAndView("transactionHistory", "items", items);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView getZones(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		Map<String, List<PriceBook>> priceBookMap = (Map<String, List<PriceBook>>) request
				.getSession().getAttribute("priceBookMap");
		final User user = (User) request.getSession().getAttribute(
				"authenticatedUser");

		if (user == null) {
			final ModelAndView view = new ModelAndView("logonForm");
			view.addObject("user", new User());
			view.addObject("errMsg",
					"Username or Password is incorrect. Please try again.");
			return view;
		}

		if (priceBookMap == null) {
			priceBookMap = this.priceBookService.getPriceBooksByChain(user
					.getStores().get(0).getChainCode());
			request.getSession().setAttribute("priceBookMap", priceBookMap);
		}

		final ModelAndView view = new ModelAndView("srpZones");
		view.addObject("zones", priceBookMap);
		request.getSession().setAttribute("defaultZone",
				priceBookMap.get("100"));

		return view;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView itemInfo(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final String code = request.getParameter("itemNumber");
		String srpBook = request.getParameter("srpBook");
		String customerNumber = "";

		final User user = (User) request.getSession().getAttribute(
				"authenticatedUser");
		final String chainCode = user.getStores().get(0).getChainCode();
		final Item item = this.itemService.getItem(code, chainCode);

		if (srpBook.equalsIgnoreCase("undefined")) {
			srpBook = ((PriceBook) ((List) request.getSession().getAttribute(
					"defaultZone")).get(0)).getNumber();
			customerNumber = ((PriceBook) ((List) request.getSession()
					.getAttribute("defaultZone")).get(0)).getCustomerNumber();
		} else {
			customerNumber = (this.priceBookService.getPriceBookByZone(srpBook))
					.getCustomerNumber();
		}

		item.setPricing(this.pricingService.getPricing(customerNumber,
				item.getNumber(), item.getPriceBookFamilyCode(), srpBook,
				chainCode));

		//final String today = DateUtil.tomorrow();
		final String today = DateUtil.today();;

		final ModelAndView view = new ModelAndView("itemDetails", "item", item);
		view.addObject("today", today);
		return view;
	}

	public ModelAndView itemInfoJSON(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final String code = request.getParameter("itemNumber");

		final User user = (User) request.getSession().getAttribute(
				"authenticatedUser");
		final String chainCode = user.getStores().get(0).getChainCode();
		final Item item = this.itemService.getItem(code, chainCode);

		return new ModelAndView("itemDetailsJSON", "item", item);
	}

	public ModelAndView orderGuideAdd(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final User user = (User) request.getSession().getAttribute(
				"authenticatedUser");
		final String itemNumber = request.getParameter("itemNumber");
		final Item item = this.itemService.getItem(itemNumber);
		this.itemService.orderGuideItemAdd(user.getStores().get(0)
				.getChainCode(), item);
		this.addHistoryItem(
				user,
				"Item - " + itemNumber + " added to order guide ("
						+ user.getOrderGuideNumber() + ").");
		return new ModelAndView("srpMessage", "msg", "Item has been added.");
	}

	public ModelAndView orderGuideDelete(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final User user = (User) request.getSession().getAttribute(
				"authenticatedUser");
		final String itemNumber = request.getParameter("itemNumber");
		final Item item = this.itemService.getItem(itemNumber);
		this.itemService.orderGuideItemRemove(user.getStores().get(0)
				.getChainCode(), item);
		this.addHistoryItem(
				user,
				"Item - " + itemNumber + " removed from order guide("
						+ user.getOrderGuideNumber() + ").");
		return new ModelAndView("srpMessage", "msg", "Item has been removed.");
	}

	@SuppressWarnings("unchecked")
	public ModelAndView removeItemPricing(final HttpServletRequest request,
			final HttpServletResponse response) {
		final String itemNumber = request.getParameter("itemNumber");
		String book = request.getParameter("priceBook");
		final String historyMessage = itemNumber + " special pricing has been removed for " + book + ".";

		if (book.equalsIgnoreCase("undefined")) {
			book = ((PriceBook) ((List) request.getSession().getAttribute(
					"defaultZone")).get(0)).getNumber();
		}

		final User user = (User) request.getSession().getAttribute(
				"authenticatedUser");
		final String chainCode = user.getStores().get(0).getChainCode();
		String customerNumber;

		if (book.equalsIgnoreCase("undefined")) {
			book = ((PriceBook) ((List) request.getSession().getAttribute(
					"defaultZone")).get(0)).getNumber();
			customerNumber = ((PriceBook) ((List) request.getSession()
					.getAttribute("defaultZone")).get(0)).getCustomerNumber();
		} else {
			customerNumber = (this.priceBookService.getPriceBookByZone(book))
					.getCustomerNumber();
		}

		final String itemLevelBook = this.priceBookService
				.getItemLevelSRPBookName(book);

		final Item item = this.itemService.getItem(itemNumber);
		try {
			item.setPricing(this.pricingService.getPricing(customerNumber,
					item.getNumber(), item.getPriceBookFamilyCode(),
					itemLevelBook, chainCode));
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.priceBookService.deletePricingForItem(item, itemLevelBook);
		this.addHistoryItem(
				(User) request.getSession().getAttribute("authenticatedUser"),
				historyMessage);
		return new ModelAndView("srpMessage", "msg",
				"Item pricing has been removed.");
	}

	@SuppressWarnings("unchecked")
	public ModelAndView removeFamilyPricing(final HttpServletRequest request,
			final HttpServletResponse response) {
		String book = request.getParameter("srpBook");
		final String familyCode = request.getParameter("family");
		final String historyMessage = "Family: " + familyCode
				+ " pricing has been removed for " + book + ".";

		if (book.equalsIgnoreCase("undefined")) {
			book = ((PriceBook) ((List) request.getSession().getAttribute(
					"defaultZone")).get(0)).getNumber();
		}

		this.priceBookService.deleteClassFamilySRP(familyCode, book);

		this.addHistoryItem(
				(User) request.getSession().getAttribute("authenticatedUser"),
				historyMessage);
		return new ModelAndView("srpMessage", "msg",
				"Family pricing has been removed.");
	}

	public ModelAndView searchAheadItemsOnly(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final String criteria = request.getParameter("criteria");
		final List<Item> items = this.itemService.search(criteria);
		final List<SearchResult> list = new ArrayList<SearchResult>();

		for (Item item : items) {
			list.add(new SearchResult(item.getDescription(), item.getNumber(),
					"item", item.getCartonUPCNumber(), item.getPack(), item
							.getSize()));
		}

		return new ModelAndView("srpSearchResults", "results", list);
	}

	public ModelAndView searchAhead(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final String criteria = request.getParameter("criteria");

		final List<Item> items = this.itemService.search(criteria);
		final List<PriceBookFamily> families = this.priceBookService
				.searchFamilies(criteria);

		final List<SearchResult> list = new ArrayList<SearchResult>();

		for (PriceBookFamily fam : families) {
			list.add(new SearchResult(fam.getDescription(),
					fam.getFamilyCode(), "family"));
		}

		for (Item item : items) {
			list.add(new SearchResult(item.getDescription(), item.getNumber(),
					"item", item.getCartonUPCNumber(), item.getPack(), item
							.getSize()));
		}

		return new ModelAndView("srpSearchResults", "results", list);
	}

	public void setChainService(final ChainService chainService) {
		this.chainService = chainService;
	}

	public void setHistoryService(final TransactionHistoryService historyService) {
		this.historyService = historyService;
	}

	public void setItemService(final ItemService itemService) {
		this.itemService = itemService;
	}

	public void setPriceBookService(final PriceBookService priceBookService) {
		this.priceBookService = priceBookService;
	}

	public void setPricingService(final PricingService pricingService) {
		this.pricingService = pricingService;
	}

	public void setStoreService(final StoreService storeService) {
		this.storeService = storeService;
	}

	public ModelAndView show(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		return new ModelAndView("srpSearch");
	}

	public ModelAndView showSRP(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		User user = this.getUser(request);
		Map<String, List<PriceBook>> priceBookMap = (Map<String, List<PriceBook>>) request
				.getSession().getAttribute("priceBookMap");

		if (user == null) {
			final ModelAndView view = new ModelAndView("logonForm");
			view.addObject("user", new User());
			view.addObject("errMsg",
					"Username or Password is incorrect. Please try again.");
			return view;
		}

		if (priceBookMap == null) {
			priceBookMap = this.priceBookService.getPriceBooksByChain(user
					.getStores().get(0).getChainCode());
			request.getSession().setAttribute("priceBookMap", priceBookMap);
		}

		final List<PriceBookFamily> families = this.priceBookService.getPriceBookFamilies();
		request.getSession().setAttribute("defaultZone",
				priceBookMap.get("100"));
		final ModelAndView view = new ModelAndView("srpScreen");
		view.addObject("families", families);
		view.addObject("defaultBook", priceBookMap.get("100"));
		return view;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView updateFamilyPricing(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception{
		String book = request.getParameter("srpBook");
		final String effectiveDate = request.getParameter("effectiveDate");
		final String srp = request.getParameter("srp");
		final String familyCode = request.getParameter("family");
		final String isFixed = request.getParameter("isFixed");
		String historyMessage = "Family: " + familyCode
				+ " updated new price is ";
		PriceBook pBook;

		if (book.equalsIgnoreCase("undefined")) {
			book = ((PriceBook) ((List) request.getSession().getAttribute(
					"defaultZone")).get(0)).getNumber();
			pBook = ((PriceBook) ((List) request.getSession().getAttribute(
					"defaultZone")).get(0));
		} else {
			pBook = this.priceBookService.getPriceBookByZone(book);
		}

		Pricing pricing = null;

		if (isFixed.equalsIgnoreCase("F")) {
			pricing = new Pricing(new BigDecimal(srp), new BigDecimal("0"),
					true, new BigDecimal("0"), effectiveDate, null);
			historyMessage += "$" + srp;
		} else {
			pricing = new Pricing(new BigDecimal("0"), new BigDecimal(srp),
					false, new BigDecimal("0"), effectiveDate, null);
			historyMessage += srp + "%";
		}

		final PriceBookFamily family = this.priceBookService
				.getPriceBookFamily(familyCode);
		family.setPricing(pricing);

		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		final String today = sdf.format(Calendar.getInstance().getTime());

		if (effectiveDate.equalsIgnoreCase(today)) {
			this.priceBookService.addFamilySRP(family, book);
		} else {
			this.priceBookService.addFutureFamilySRP(family, book,
					effectiveDate);
		}

		historyMessage += " effective " + effectiveDate + " for "
				+ pBook.getDescription();

		this.addHistoryItem(
				(User) request.getSession().getAttribute("authenticatedUser"),
				historyMessage);
		return this.familyForm(request, response);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView updateItemPricing(final HttpServletRequest request,
			final HttpServletResponse response) {
		final String itemNumber = request.getParameter("itemNumber");
		final String effectiveDate = request.getParameter("effDate");
		String priceBook = request.getParameter("priceBook");
		final String itemPricing = request.getParameter("itemPricing");
		final String isFixed = request.getParameter("isFixed");
		String historyMessage = "Item - " + itemNumber + " pricing updated to ";
		PriceBook pBook;

		if (priceBook.equalsIgnoreCase("undefined")) {
			priceBook = ((PriceBook) ((List) request.getSession().getAttribute(
					"defaultZone")).get(0)).getNumber();
			pBook = ((PriceBook) ((List) request.getSession().getAttribute(
					"defaultZone")).get(0));
		} else {
			pBook = this.priceBookService.getPriceBookByZone(priceBook);
		}

		final Pricing pricing = new Pricing();
		final Item item = this.itemService.getItem(itemNumber);

		if (isFixed.equalsIgnoreCase("F")) {
			pricing.setFixed(true);
			pricing.setPrice(new BigDecimal(itemPricing));
			pricing.setPercent(new BigDecimal(0));
			historyMessage += " a fixed price of " + itemPricing;
		} else {
			pricing.setFixed(false);
			pricing.setPercent(new BigDecimal(itemPricing));
			pricing.setPrice(new BigDecimal(0));
			historyMessage += " a markup of " + itemPricing + "%";
		}

		item.setPricing(pricing);
		historyMessage += " for " + pBook.getDescription();
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		final String today = sdf.format(Calendar.getInstance().getTime());

		if (today.equalsIgnoreCase(effectiveDate)) {
			this.priceBookService.updatePricingForItem(item, priceBook);
			this.addHistoryItem(
					(User) request.getSession().getAttribute(
							"authenticatedUser"), historyMessage);
		} else {
			if(pricing.isFixed()){
				this.priceBookService.updateFuturePricingForItem(item, priceBook,
					effectiveDate, pricing.getPrice().floatValue(), "F");
			}else{
				this.priceBookService.updateFuturePricingForItem(item, priceBook,
						effectiveDate, pricing.getPercent().floatValue(), "P");
			}
			historyMessage += " with an effective date of: " + effectiveDate;
			this.addHistoryItem(
					(User) request.getSession().getAttribute(
							"authenticatedUser"), historyMessage);
		}

		return new ModelAndView("srpMessage", "msg",
				"Item pricing has been updated.");
	}

	@SuppressWarnings("unchecked")
	public ModelAndView updateItems(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		final String[] itemNumbers = StringUtils.split(
				request.getParameter("items"), ",");
		String book = request.getParameter("book");
		String customerNumber = "";

		final User user = (User) request.getSession().getAttribute(
				"authenticatedUser");
		final String chainCode = user.getStores().get(0).getChainCode();

		if (book.equalsIgnoreCase("undefined")) {
			book = ((PriceBook) ((List) request.getSession().getAttribute(
					"defaultZone")).get(0)).getNumber();
			customerNumber = ((PriceBook) ((List) request.getSession()
					.getAttribute("defaultZone")).get(0)).getCustomerNumber();
		} else {
			customerNumber = (this.priceBookService.getPriceBookByZone(book))
					.getCustomerNumber();
		}

		final List<Item> items = new ArrayList<Item>();
		for (int i = 0; i < itemNumbers.length; i++) {
			Item item = this.itemService.getItem(itemNumbers[i],
					chainCode);
			item.setPricing(this.pricingService.getPricing(customerNumber,
					item.getNumber(), item.getPriceBookFamilyCode(), book,
					chainCode));
			items.add(item);
		}

		final PriceBookFamily family = this.priceBookService
				.getPriceBookFamily(items.get(0).getPriceBookFamilyCode());
		family.setPricing(this.pricingService.getFamilyPricing(chainCode,
				family.getFamilyCode(), book));

		//final String today = DateUtil.tomorrow();
		final String today = DateUtil.today();

		final ModelAndView view = new ModelAndView("itemListDetails");
		view.addObject("items", items);
		view.addObject("family", family);
		view.addObject("today", today);

		return view;
	}

	private void addHistoryItem(final User user, final String message) {
		String today = "";
		try {
			today = DateUtil.convertToMySQLDate(DateUtil.today());
		} catch (ParseException e) {
			today = "";
		}

		this.historyService.addTransaction(today, user.getStores().get(0)
				.getChainCode(), message, user.getId());
	}

	private int processType(final String type) {
		if (type.equalsIgnoreCase("header")) {
			return 1;
		}

		if (type.equalsIgnoreCase("class")) {
			return 0;
		}

		if (type.equalsIgnoreCase("family")) {
			return 2;
		}

		if (type.equalsIgnoreCase("item")) {
			return 3;
		}

		return -1;
	}
}
