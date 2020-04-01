package com.parksexpress.dao;

import java.util.List;

import com.parksexpress.domain.Chain;
import com.parksexpress.domain.item.AllowanceItem;
import com.parksexpress.domain.item.ApprovedDistributionsItem;
import com.parksexpress.domain.item.FuturePriceChangeItem;
import com.parksexpress.domain.item.Item;
import com.parksexpress.domain.item.PriceChangeItem;

public interface ItemDAO {
	List<Item> search(String criteria);
	Item getItem(String itemNumber);
	List<Item> getItemsByFamily(String family);
	List<Item> getItemsByHeader(String header);
	List<Item> getItemsByClass(String itemClass);
	String getHeaderCodeForItem(String itemCode);
	List<Item> getItemsInOrderGuide(String chainCode, String familyCode);
	boolean isItemInOrderGuide(String chainCode, String itemNumber);
	int orderGuideItemAdd(String chainCode, Item item);
	int orderGuideItemRemove(String chainCode, Item item);
	List<AllowanceItem> getAllowances(String startDate, String endDate, String number, String type);
	List<Item> getNewItems(String startDate, String endDate, String number, String type);
	List<Item> getDiscontinuedItems(String startDate, String endDate, String number, String type, String orderGuide);
	List<Item> getSuspendedItems(String orderGuide);
	List<PriceChangeItem> getPriceChanges(String startDate, String endDate, String code, String codeType, String storeNumber, String orderGuideNumber);
	List<FuturePriceChangeItem> getFuturePriceChanges(String orderGuideNumber);
	List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String itemNumber);
	List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String startDate, String endDate);
	List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String itemNumber, String startDate, String endDate);
	List<String> getAllBrands();
	List<ApprovedDistributionsItem> getApprovedDistributions(Chain chain, String start, String end);
	List<ApprovedDistributionsItem> getApprovedDistributions(Chain chain, String item);
	List<ApprovedDistributionsItem> getApprovedDistributions(Chain chain, String item, String start, String end);
	List<Item> getDiscontinuedAndSuspendedItemsByFamily(final String family);
	List<Item> find(String criteria);
}
