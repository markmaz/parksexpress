package com.parksexpress.dao;

import com.parksexpress.domain.Pricing;

public interface PricingDAO {
	public String getEffectivePricingForItem(String item, String priceBook);
	public String getEffectivePricingForFamily(String familyCode, String priceBook);
	public Pricing getFuturePricingForItem(String item, String book);
	public Pricing getFuturePricingForFamily(String family, String book);
}
