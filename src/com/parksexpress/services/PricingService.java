package com.parksexpress.services;

import java.util.List;

import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.Pricing;
import com.parksexpress.domain.item.Item;

public interface PricingService {
	Pricing getPricing(String customerNumber, String itemNumber, String family,
			String priceBook, String chainCode) throws Exception;

	Pricing getPricingForItem(String itemNumber, String priceBook);

	List<Item> getPricingForItems(String book, PriceBookFamily family,
			String customerNumber) throws Exception;

	Pricing getFamilyPricing(String chainCode, String familyCode, String book);
	
	Pricing getPricing(String itemNumber, String customer);
	
	Pricing getFuturePricingForItem(String itemNumber, String book);
	
	Pricing getFuturePricingForFamily(String family, String book);
}
