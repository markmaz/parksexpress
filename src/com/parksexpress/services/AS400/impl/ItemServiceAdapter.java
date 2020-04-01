package com.parksexpress.services.AS400.impl;

import java.util.List;

import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.Pricing;
import com.parksexpress.domain.item.AllowanceItem;
import com.parksexpress.domain.item.ApprovedDistributionsItem;
import com.parksexpress.domain.item.FuturePriceChangeItem;
import com.parksexpress.domain.item.Item;
import com.parksexpress.domain.item.PriceChangeItem;
import com.parksexpress.services.ItemService;

public class ItemServiceAdapter implements ItemService {

	public ItemServiceAdapter(){}
	
	public Item getItem(final String itemNumber) {
		return null;
	}

	public Item getItem(final String itemNumber, final String chainCode) {
		return null;
	}

	public List<Item> getItemsByClass(final String itemClass) {
		return null;
	}

	public List<Item> getItemsByFamily(final String family) {
		return null;
	}

	public List<Item> getItemsByHeader(final String header) {
		return null;
	}

	public List<Item> getItemsWithOrderGuide(final String chainCode,
			PriceBookFamily family) {
		return null;
	}

	public Pricing getPriceingForItem(final String itemNumber) {
		return null;
	}

	public boolean orderGuideItemAdd(final String chainCode, final Item item) {
		return false;
	}

	public boolean orderGuideItemRemove(final String chainCode, final Item item) {
		return false;
	}

	public List<Item> search(final String criteria) {
		return null;
	}

	public List<AllowanceItem> getAllowances(final String startDate, final String endDate,
			final String number, final String type) {
		return null;
	}

	public List<Item> getNewItems(final String startDate, final String endDate,
			final String number, final String type) {
		return null;
	}

	public List<Item> getDiscontinuedItems(final String startDate, final String endDate,
			final String number, final String type, final String orderGuide) {
		return null;
	}

	public List<Item> getSuspendedItems(final String orderGuideNumber) {
		return null;
	}

	public List<PriceChangeItem> getPriceChangeReport(final String startDate,
			final String endDate, final String storeNumber, final String code, final String codeType, String orderGuideNumber) {
		return null;
	}

	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String itemNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String itemNumber, String startDate, 
			String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FuturePriceChangeItem> getFuturePriceChangeReport(String orderGuideNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllBrands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getDiscontinuedAndSuspendedItemsByFamily(String familyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> find(String criteria) {
		// TODO Auto-generated method stub
		return null;
	}
}