package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.FamilyPricingException;
import com.parksexpress.domain.ItemPricingException;
import com.parksexpress.domain.PriceBook;
import com.parksexpress.domain.PriceBookClass;
import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.Pricing;
import com.parksexpress.domain.item.Item;

public interface PriceBookDAO {
	int CLASS = 0;
	int FAMILY = 2;
	int HEADER = 1;
	int ITEM = 3;

	boolean addClassPricing(PriceBookClass priceBookClass, String srpBook);

	boolean addFamilyPricing(PriceBookFamily family, String srpBook);

	boolean addItemPricing(Item item, String srpBook);

	boolean deleteClassFamilyPricing(String family, String srpBook);

	boolean deletePricingForItem(Item item, String priceBook);

	PriceBookClass getClass(String criteria);

	PriceBookFamily getFamily(String criteria);

	List<FamilyPricingException> getFamilyExceptionPricing(
			PriceBookFamily family, String chainCode, int priority);

	PriceBookHeader getHeader(String criteria);

	List<ItemPricingException> getItemLevelPriceBookExceptionsForFamilyForAllZones(
			String chainCode, String familyCode);

	String getItemLevelSRPBookName(String srpBookFamilyLevel);

	PriceBook getPriceBookByZone(String zone);

	List<PriceBook> getPriceBooksByPriority(int priority,
			List<PriceBook> parentBooks);

	List<PriceBook> getPriceBooksByPriority(int priority, String chainCode);

	List<Pricing> getPricingForFamily(String chainCode, String familyCode,
			int priority);

	List<Pricing> getPricingForFamily(String chainCode, String priceBook, String familyCode);

	Pricing getPricingForItem(String itemNumber, String priceBook);

	PriceBookHeader getSkeleton(String criteria, int type);

	List<PriceBook> getZones(int priority, String chainCode);

	List<PriceBookClass> searchClasses(String criteria);
	
	List<PriceBookFamily> searchFamilies(String criteria);

	List<PriceBookHeader> searchHeaders(String criteria);

	boolean updateFutureFamilyPricing(PriceBookFamily family, String srpBook);

	boolean updateFuturePricingForItem(Item item, String priceBook,
			String effectiveDate, float srp, String isFixed);

	boolean updatePricingForItem(Item item, String priceBook);
	
	List<PriceBookClass> getAllPriceBookClasses();
	List<PriceBookHeader> getAllPriceBookHeaders();
	List<PriceBookFamily> getAllPriceBookFamilies();
}
