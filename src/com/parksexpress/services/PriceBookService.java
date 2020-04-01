package com.parksexpress.services;

import java.util.List;
import java.util.Map;

import com.parksexpress.domain.FamilyPricingException;
import com.parksexpress.domain.ItemPricingException;
import com.parksexpress.domain.PriceBook;
import com.parksexpress.domain.PriceBookClass;
import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.Pricing;
import com.parksexpress.domain.item.Item;

public interface PriceBookService {
	String PRICEBOOK = "pricebook";
	String PRICING = "pricing";

	boolean addClassSRP(PriceBookClass priceBookClass, String srpBook);

	boolean addFamilySRP(PriceBookFamily family, String srpBook);

	void addFutureFamilySRP(PriceBookFamily family, String book,
			String effectiveDate);

	boolean deleteClassFamilySRP(String family, String srpBook);

	boolean deletePricingForItem(Item item, String priceBook);

	boolean deletePricingForItems(List<Item> items, String priceBook);

	List<FamilyPricingException> getDetailPricingExceptions(String chainCode, String familyCode,
			int priority);

	String getItemLevelSRPBookName(String srpBookFamilyLevel);

	List<ItemPricingException> getItemPriceBookExceptions(String chainCode,
			String familyCode);

	PriceBook getPriceBookByZone(String zone);

	@SuppressWarnings("unchecked")
	List getPriceBookExceptions(String chainCode, String familyCode,
			int priority);

	PriceBookFamily getPriceBookFamily(String code);

	PriceBookHeader getPriceBookHeader(String code, int type);

	PriceBookHeader getPriceBookHeader(String code);
	
	PriceBookClass getPriceBookClass(String code);
	
	Map<String, List<PriceBook>> getPriceBooksByChain(String chainCode);

	List<PriceBookClass> searchClasses(String criteria);

	List<PriceBookFamily> searchFamilies(String criteria);

	List<PriceBookHeader> searchHeaders(String criteria);

	boolean updateFuturePricingForItem(Item item, String priceBook,
			String effectiveDate, float srp, String srpCode);
	
	boolean updatePricingForItem(Item item, String priceBook);

	boolean updatePricingForItems(List<Item> items, Pricing pricing,
			String priceBook);
	
	List<PriceBookHeader> getPriceBookHeaders();
	List<PriceBookClass> getPriceBookClasses();
	List<PriceBookFamily> getPriceBookFamilies();
}
