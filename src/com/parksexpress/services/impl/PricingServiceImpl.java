package com.parksexpress.services.impl;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.parksexpress.as400.util.RpgSrp;
import com.parksexpress.dao.PriceBookDAO;
import com.parksexpress.dao.PricingDAO;
import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.Pricing;
import com.parksexpress.domain.item.Item;
import com.parksexpress.services.PricingService;

public class PricingServiceImpl implements PricingService {
	private static Logger log = Logger.getRootLogger();
	private RpgSrp rpg;
	private PriceBookDAO priceBookDAO;
	private PricingDAO pricingDAO;
	
	public PricingServiceImpl(){}
	
	public void setPriceBookDAO(final PriceBookDAO priceBookDAO) {
		this.priceBookDAO = priceBookDAO;
	}

	public void setPricingDAO(final PricingDAO pricingDAO){
		this.pricingDAO = pricingDAO;
	}
	
	public void setRpg(final RpgSrp rpg) {
		this.rpg = rpg;
	}

	@SuppressWarnings("unchecked")
	public Pricing getPricing(final String customerNumber, final String itemNumber,
			final String family, final String priceBook, final String chainCode) throws Exception {
//		Pricing pricing = this.getPricingForItem(itemNumber, priceBook);
// 
//		if (pricing == null) {
//			pricing = this.getFamilyPricing(chainCode, family, priceBook);
//		}

		Pricing pricing = new Pricing();
		
		try {
			final Map map = this.rpg.getSrpData(customerNumber, itemNumber);

			pricing.setCost(new BigDecimal(map.get("price").toString()));
			pricing.setPrice(new BigDecimal(map.get("srp").toString()));
			pricing.setEffectiveDate(this.pricingDAO.getEffectivePricingForItem(itemNumber, priceBook));
			
			if(pricing.getCost().floatValue() == 0){
				pricing.setFixed(false);
			}else{
				pricing.setFixed(true);
			}
		} catch (ConnectException e) {
			PricingServiceImpl.log.fatal("Connection to AS400 timed out.");
			pricing.setCost(new BigDecimal("0.00"));
			pricing.setPrice(new BigDecimal("0.00"));
		}

		return pricing;
	}

	public Pricing getPricingForItem(final String itemNumber, final String priceBook) {
		return this.priceBookDAO.getPricingForItem(itemNumber, priceBook);
	}

	public List<Item> getPricingForItems(final String book, final PriceBookFamily family,
			final String customerNumber) throws Exception {
		final List<Item> list = new ArrayList<Item>();

		for (Item item : family.getItems()) {
			//final Pricing pricing = this.getPricingForItem(item.getNumber(), book);
			final Map map = this.rpg.getSrpData(customerNumber, item.getNumber());
			Pricing pricing = new Pricing();
			pricing.setCost(new BigDecimal(map.get("price").toString()));
			pricing.setPrice(new BigDecimal(map.get("srp").toString()));
			//pricing.setEffectiveDate(pricingDAO.getEffectivePricingForItem(item.getNumber(), book));
			
			if (pricing != null) {
				item.setPricing(pricing);
				item.setMarketCost(pricing.getCost().floatValue());
			} else {
				item.setPricing(family.getPricing());
			}

			item.setFuturePricing(pricingDAO.getFuturePricingForItem(item.getNumber(), book));
			
			list.add(item);
		}

		return list;
	}

	public Pricing getFamilyPricing(final String chainCode, final String familyCode,
			final String book) {
		List<Pricing> list = this.priceBookDAO.getPricingForFamily(chainCode, book, familyCode);

		if (list.size() == 0) {
			final int priority = 100;
			list = this.priceBookDAO.getPricingForFamily(chainCode, familyCode, priority);
		}

		Pricing pricing = new Pricing();
		for (Pricing p : list) {
			if (p.getPricingLevel().equalsIgnoreCase(Pricing.HEADER)
					&& StringUtils.isEmpty(pricing.getPricingLevel())) {
				pricing = p;
			}

			if (p.getPricingLevel().equalsIgnoreCase(Pricing.CLASS)) {
				if (pricing.getPricingLevel().equalsIgnoreCase(Pricing.HEADER)
						|| StringUtils.isEmpty(pricing.getPricingLevel())) {
					pricing = p;
				}
			}

			if (p.getPricingLevel().equalsIgnoreCase(Pricing.FAMILY)) {
				if (pricing.getPricingLevel().equalsIgnoreCase(Pricing.HEADER)
						|| pricing.getPricingLevel().equalsIgnoreCase(
								Pricing.CLASS)
						|| StringUtils.isEmpty(pricing.getPricingLevel())) {
					pricing = p;
				}
			}
		}

		pricing.setEffectiveDate(this.pricingDAO.getEffectivePricingForFamily(familyCode, book));
		return pricing;
	}

	@Override
	public Pricing getPricing(final String itemNumber, final String customer) {
		final Pricing pricing = new Pricing();
		
		try {
			final Map map = this.rpg.getSrpData(customer, itemNumber);

			pricing.setCost(new BigDecimal(map.get("price").toString()));
			pricing.setPrice(new BigDecimal(map.get("srp").toString()));
		} catch (ConnectException e) {
			PricingServiceImpl.log.fatal("Connection to AS400 timed out.");
			pricing.setCost(new BigDecimal("0.00"));
			pricing.setPrice(new BigDecimal("0.00"));
		} catch (Exception e) {
			PricingServiceImpl.log.fatal("Connection to AS400 timed out.");
			pricing.setCost(new BigDecimal("0.00"));
			pricing.setPrice(new BigDecimal("0.00"));
		}
	
		return pricing;
	}

	@Override
	public Pricing getFuturePricingForItem(String itemNumber, String book) {
		return this.pricingDAO.getFuturePricingForItem(itemNumber, book);
	}

	@Override
	public Pricing getFuturePricingForFamily(String family, String book) {
		return this.pricingDAO.getFuturePricingForFamily(family, book);
	}
}
