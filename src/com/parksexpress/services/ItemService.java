package com.parksexpress.services;

import java.util.List;

import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.Pricing;
import com.parksexpress.domain.item.AllowanceItem;
import com.parksexpress.domain.item.ApprovedDistributionsItem;
import com.parksexpress.domain.item.FuturePriceChangeItem;
import com.parksexpress.domain.item.Item;
import com.parksexpress.domain.item.PriceChangeItem;

public interface ItemService {
	List<Item> search(String criteria);
	Item getItem(String itemNumber);
	Item getItem(String itemNumber, String chainCode);
	List<Item> getItemsByFamily(String family);
	List<Item> getItemsByHeader(String header);
	List<Item> getItemsByClass(String itemClass);
	List<Item> getItemsWithOrderGuide(String chainCode, PriceBookFamily family);
	Pricing getPriceingForItem(String itemNumber);
	boolean orderGuideItemAdd(String chainCode, Item item);
	boolean orderGuideItemRemove(String chainCode, Item item);
	List<AllowanceItem> getAllowances(String startDate, String endDate, String number, String type);
	List<Item> getNewItems(String startDate, String endDate, String number, String type);
	List<Item> getDiscontinuedItems(String startDate, String endDate, String number, String type, String orderGuide);
	List<Item> getSuspendedItems(String orderGuideNumber);
	List<PriceChangeItem> getPriceChangeReport(String startDate, String endDate, String storeNumber, String code, String codeType, String orderGuideNumber);
	List<FuturePriceChangeItem> getFuturePriceChangeReport(String orderGuideNumber);
	List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String itemNumber);
	List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String startDate, String endDate);
	List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String itemNumber, String startDate, String endDate);
	List<String> getAllBrands();
	List<Item> getDiscontinuedAndSuspendedItemsByFamily(String familyCode);
	List<Item> find(String criteria);
}
