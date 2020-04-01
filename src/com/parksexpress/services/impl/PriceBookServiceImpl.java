package com.parksexpress.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.parksexpress.dao.PriceBookDAO;
import com.parksexpress.domain.FamilyPricingException;
import com.parksexpress.domain.ItemPricingException;
import com.parksexpress.domain.PriceBook;
import com.parksexpress.domain.PriceBookClass;
import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.Pricing;
import com.parksexpress.domain.item.Item;
import com.parksexpress.jms.sender.FamilyPricingSender;
import com.parksexpress.jms.sender.ItemPricingSender;
import com.parksexpress.services.PriceBookService;

public class PriceBookServiceImpl implements PriceBookService {
	private FamilyPricingSender familyPricingSender;
	private ItemPricingSender itemPricingSender;
	private PriceBookDAO priceBookDAO;
	private Logger log = Logger.getLogger(PriceBookServiceImpl.class);
	private PriceBookDAO as400PriceBookDAO;
	
	public PriceBookServiceImpl() {
	}

	public boolean addClassSRP(final PriceBookClass priceBookClass, final String srpBook) {
		this.log.debug("Entering: addClassSRP");
		this.log.debug("Price Book Class: " + priceBookClass + " SRP Book: " + srpBook);
		this.log.debug("Exiting: addClassSRP");
		return this.priceBookDAO.addClassPricing(priceBookClass, srpBook);
	}

	public boolean addFamilySRP(final PriceBookFamily family, final String srpBook) {
		this.log.debug("Entering: addFamilySRP");
		this.log.debug("Price Book Family: " + family + " SRP Book: " + srpBook);
		this.log.debug("Sending family pricing message: ADD");
		this.familyPricingSender.sendFamilyPricingMessage(family, srpBook, FamilyPricingSender.ADD);
		this.log.debug("Exiting: addFamilySRP");
		return this.priceBookDAO.addFamilyPricing(family, srpBook);
	}

	public void addFutureFamilySRP(final PriceBookFamily family, final String book, final String effectiveDate) {
		this.log.debug("Entering: addFutureFamilySRP");
		this.log.debug("Family: " + family + " Book: " + book + " effectiveDate: " + effectiveDate);
		this.priceBookDAO.updateFutureFamilyPricing(family, book);
		this.as400PriceBookDAO.updateFutureFamilyPricing(family, book);
	}

	public boolean deleteClassFamilySRP(final String family, final String srpBook) {
		this.log.debug("Entering deleteClassFamilySRP");
		this.log.debug("Family: " + family + " srpBook:" + srpBook);
		this.log.debug("Get priceBookFamily object");
		final PriceBookFamily priceBookFamily = this.priceBookDAO.getFamily(family);
		this.log.debug("Send family pricing message: DELETE");
		this.familyPricingSender.sendFamilyPricingMessage(priceBookFamily, srpBook, FamilyPricingSender.DELETE);
		this.log.debug("Exiting: deleteClassFamilySRP");
		return this.priceBookDAO.deleteClassFamilyPricing(family, srpBook);
	}

	public boolean deletePricingForItem(final Item item, final String priceBook) {
		this.log.debug("Entering deletePricingForItem");
		this.log.debug("Item: " + item + " priceBook: " + priceBook);
		this.log.debug("Send Item Pricing message: DELETE");
		this.itemPricingSender.sendItemPricingMessage(item, priceBook, ItemPricingSender.DELETE);
		this.log.debug("Exiting: deletePricingForItem");
		return this.priceBookDAO.deletePricingForItem(item, priceBook);
	}

	public boolean deletePricingForItems(final List<Item> items, final String priceBook) {
		this.log.debug("Entering: deltePricingForItems");
		this.log.debug("Items (size): " + items.size() + " priceBook:" + priceBook);
		for (Item item : items) {
			this.log.debug("Sending Item Pricing Message: DELETE for Item: " + item.getNumber());
			this.itemPricingSender.sendItemPricingMessage(item, priceBook, ItemPricingSender.DELETE);
			this.log.debug("Deleting pricing for local object: " + item.getNumber());
			this.priceBookDAO.deletePricingForItem(item, priceBook);
		}

		this.log.debug("Exiting deletePricingForItems");
		return true;
	}

	@SuppressWarnings("unchecked")
	public List getDetailPricingExceptions(final String chainCode, final String familyCode, final int priority) {
		this.log.debug("Entering: getDetailPricingExceptions");
		this.log.debug("chainCode: " + chainCode + "familyCode: " + familyCode + " priority:" + priority);
		this.log.debug("Getting the family object");
		final PriceBookFamily family = this.priceBookDAO.getFamily(familyCode);
		this.log.debug("Getting the family exceptions");
		final List<FamilyPricingException> familyExceptions = 
			this.priceBookDAO.getFamilyExceptionPricing(family, chainCode, priority);

		this.log.debug("Looping through the family exceptions");
		for (int i = 0; i < familyExceptions.size(); i++) {
			final FamilyPricingException fpe = familyExceptions.get(i);
			this.log.debug("Family Exception: " + fpe.toString());
			this.log.debug("Getting the ItemPricingExceptions for the chain using family code from the FamilyPricingException");
			final List<ItemPricingException> items = 
				this.priceBookDAO.getItemLevelPriceBookExceptionsForFamilyForAllZones(chainCode, fpe
					.getFamily().getFamilyCode());
			this.log.debug("Found " + items.size() + " item exceptions");
			this.log.debug("Adding the itemException to the family exceptions");
			familyExceptions.get(0).setItemExceptions(items);
		}

		this.log.debug("Exiting getDetailPricingExceptions");
		return familyExceptions;
	}

	public String getItemLevelSRPBookName(final String srpBookFamilyLevel) {
		this.log.debug("Entering: getItemLevelSRPBookName");
		this.log.debug("srpBookFamilyLevel: " + srpBookFamilyLevel);
		return this.priceBookDAO.getItemLevelSRPBookName(srpBookFamilyLevel);
	}

	public List<ItemPricingException> getItemPriceBookExceptions(final String chainCode, final String familyCode) {
		this.log.debug("Entering: getItemPriceBookExceptions");
		this.log.debug("chainCode: " + chainCode + " familyCode: " + familyCode);
		this.log.debug("Getting item level exceptions for families in all zones");
		return this.priceBookDAO.getItemLevelPriceBookExceptionsForFamilyForAllZones(chainCode, familyCode);
	}

	public PriceBook getPriceBookByZone(final String zone) {
		this.log.debug("Entering: getPriceBookByZone");
		this.log.debug("Zone: " + zone);
		return this.priceBookDAO.getPriceBookByZone(zone);
	}

	@SuppressWarnings("unchecked")
	public List getPriceBookExceptions(final String chainCode, final String familyCode, final int priority) {
		this.log.debug("Entering getPriceBookExceptions");
		this.log.debug("chainCode: " + chainCode + " familyCode:" + familyCode + " priority: " + priority);
		this.log.debug("Getting the family object using the name");
		final PriceBookFamily family = this.priceBookDAO.getFamily(familyCode);
		this.log.debug("Getting the Family Exceptions");
		return this.priceBookDAO.getFamilyExceptionPricing(family, chainCode, priority);
	}

	public PriceBookFamily getPriceBookFamily(final String code) {
		this.log.debug("Entering: getPriceBookFamily");
		this.log.debug("Code: " + code);
		return this.priceBookDAO.getFamily(code);
	}

	public PriceBookHeader getPriceBookHeader(final String code, final int type) {
		this.log.debug("Entering: getPriceBookHeader");
		this.log.debug("code: " + code + " type: " + type);
		this.log.debug("Getting the header using the skeleton");
		return this.priceBookDAO.getSkeleton(code, type);
	}

	public Map<String, List<PriceBook>> getPriceBooksByChain(final String chainCode) {
		this.log.debug("Entering: getPriceBooksByChain");
		this.log.debug("chainCode:" + chainCode);
		final Map<String, List<PriceBook>> bookMap = new HashMap<String, List<PriceBook>>();
		this.log.debug("getting the chainFamily pricebook using chainCode:" + chainCode);
		final List<PriceBook> chainFamilyLevelPriceBook = this.priceBookDAO.getZones(PriceBook.CHAIN_FAMILY_PRIORITY, chainCode);
		bookMap.put(PriceBook.CHAIN_FAMILY_NAME, chainFamilyLevelPriceBook);
		this.log.debug("getting the itemlevel pricebook using chainCode:" + chainCode);
		final List<PriceBook> chainItemLevelPriceBook = this.priceBookDAO.getZones(PriceBook.CHAIN_ITEM_PRIORITY, chainCode);
		chainItemLevelPriceBook.get(0).setParent(chainFamilyLevelPriceBook.get(0));
		bookMap.put(PriceBook.CHAIN_ITEM_NAME, chainItemLevelPriceBook);
		this.log.debug("getting the zoneFamilyLevel pricebooks using chainCode:" + chainCode);
		final List<PriceBook> zoneFamilyLevelPriceBook = this.priceBookDAO.getZones(PriceBook.ZONE_FAMILY_PRIORITY, chainCode);

		this.log.debug("Set the chainItemLevelPriceBook as the parent for all the zone families");
		for (PriceBook book : zoneFamilyLevelPriceBook) {
			this.log.debug("Book Name: " + book.getDescription());
			book.setParent(chainItemLevelPriceBook.get(0));
		}
		
		bookMap.put(PriceBook.ZONE_FAMILY_NAME, zoneFamilyLevelPriceBook);
		this.log.debug("Exiting: getPriceBooksButChain");
		return bookMap;
	}

	public List<PriceBookClass> searchClasses(final String criteria) {
		this.log.debug("Entering: searchClasses(" + criteria + ")");
		return this.priceBookDAO.searchClasses(criteria);
	}

	public List<PriceBookFamily> searchFamilies(final String criteria) {
		this.log.debug("Entering: searchFamilies(" + criteria + ")");
		return this.priceBookDAO.searchFamilies(criteria);
	}

	public List<PriceBookHeader> searchHeaders(final String criteria) {
		this.log.debug("Entering: searchHeaders(" + criteria + ")");
		return this.priceBookDAO.searchHeaders(criteria);
	}

	public void setFamilyPricingSender(final FamilyPricingSender familyPricingSender) {
		this.familyPricingSender = familyPricingSender;
	}

	public void setItemPricingSender(final ItemPricingSender itemPricingSender) {
		this.itemPricingSender = itemPricingSender;
	}

	public void setPriceBookDAO(final PriceBookDAO priceBookDAO) {
		this.priceBookDAO = priceBookDAO;
	}
	
	public void setAS400PriceBookDAO(final PriceBookDAO priceBookDAO){
		this.as400PriceBookDAO = priceBookDAO;
	}

	public boolean updateFuturePricingForItem(final Item item, final String priceBook, final String effectiveDate, float srp, String srpCode) {
		this.log.debug("Entering updateFuturePricingForItem");
		this.log.debug("Item: " + item + " priceBook:" + priceBook + " effectiveDate:" + effectiveDate);
		item.getPricing().setEffectiveDate(effectiveDate);

		this.log.debug("getting the itemLevelPriceBook");
		final String itemLevelPriceBook = this.priceBookDAO.getItemLevelSRPBookName(priceBook);
		this.log.debug("calling sendItemPricingMessage: UPDATE");
		this.itemPricingSender.sendItemPricingMessage(item, itemLevelPriceBook, ItemPricingSender.UPDATE);
		this.log.debug("Updating the future pricing locally");
		return this.priceBookDAO.updateFuturePricingForItem(item, priceBook, effectiveDate, srp, srpCode);
	}
	

	public boolean updatePricingForItem(final Item item, final String priceBook) {
		this.log.debug("Entering updatePricingForItem");
		this.log.debug("Item: " + item + " priceBook:" + priceBook);
		this.log.debug("getting the itemlevelsrpBook");
		final String itemLevelPriceBook = this.priceBookDAO.getItemLevelSRPBookName(priceBook);
		this.log.debug("Sending item pricing message: ADD");
		this.itemPricingSender.sendItemPricingMessage(item, itemLevelPriceBook, ItemPricingSender.ADD);
		this.log.debug("Updating the pricing locally");
		return this.priceBookDAO.updatePricingForItem(item, priceBook);
	}

	public boolean updatePricingForItems(final List<Item> items, final Pricing pricing, final String priceBook) {
		this.log.debug("ENtering: updatePricingForItems");
		this.log.debug("Items (size): " + items.size() + " pricing:" + pricing + " priceBook:" + priceBook);
		this.log.debug("Looping through item list");
		for (Item item : items) {
			item.setPricing(pricing);
			this.log.debug("Sending pricing message: ADD for item:" + item.getNumber());
			this.itemPricingSender.sendItemPricingMessage(item, priceBook, ItemPricingSender.ADD);
			this.log.debug("Update the pricing locally for item: " + item.getNumber());
			this.priceBookDAO.updatePricingForItem(item, priceBook);
		}

		this.log.debug("Exiting: updatePricingForItem");
		return true;
	}

	@Override
	public PriceBookHeader getPriceBookHeader(final String code) {
		return this.priceBookDAO.getHeader(code);
	}

	@Override
	public List<PriceBookClass> getPriceBookClasses() {
		return this.priceBookDAO.getAllPriceBookClasses();
	}

	@Override
	public List<PriceBookFamily> getPriceBookFamilies() {
		return this.priceBookDAO.getAllPriceBookFamilies();
	}

	@Override
	public List<PriceBookHeader> getPriceBookHeaders() {
		return this.priceBookDAO.getAllPriceBookHeaders();
	}

	@Override
	public PriceBookClass getPriceBookClass(String code) {
		return this.priceBookDAO.getClass(code);
	}
}