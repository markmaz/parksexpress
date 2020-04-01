package com.parksexpress.dao.spring.as400;
 
import java.util.List;

import com.parksexpress.dao.ItemDAO;
import com.parksexpress.domain.Chain;
import com.parksexpress.domain.item.AllowanceItem;
import com.parksexpress.domain.item.ApprovedDistributionsItem;
import com.parksexpress.domain.item.FuturePriceChangeItem;
import com.parksexpress.domain.item.Item;
import com.parksexpress.domain.item.PriceChangeItem;

public class AS400ItemDAOAdapter extends AS400BaseDAO implements ItemDAO {
	public AS400ItemDAOAdapter(){
	}

	public String getHeaderCodeForItem(String itemCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public Item getItem(String itemNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Item> getItemsByClass(String itemClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Item> getItemsByFamily(String family) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Item> getItemsByHeader(String header) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Item> getItemsInOrderGuide(String chainCode, String familyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isItemInOrderGuide(String chainCode, String itemNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	public int orderGuideItemAdd(String chainCode, Item item) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int orderGuideItemRemove(String chainCode, Item item) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Item> search(String criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<AllowanceItem> getAllowances(String startDate, String endDate,
			String number, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Item> getNewItems(String startDate, String endDate,
			String number, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Item> getDiscontinuedItems(String startDate, String endDate,
			String number, String type, String orderGuide) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Item> getSuspendedItems(String orderGuide) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PriceChangeItem> getPriceChanges(String startDate, String endDate,
			String code, String codeType, String storeNumber, String orderGuideNumber) {
		// TODO Auto-generated method stub
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
	public List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String itemNumber, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FuturePriceChangeItem> getFuturePriceChanges(String orderGuideNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAllBrands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(Chain chain, String start, String end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(Chain chain, String item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(Chain chain, String item, String start, String end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getDiscontinuedAndSuspendedItemsByFamily(String family) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> find(String criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
