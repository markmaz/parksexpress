package com.parksexpress.services.impl;

import java.util.List;

import com.parksexpress.dao.ItemDAO;
import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.Pricing;
import com.parksexpress.domain.item.Item;
import com.parksexpress.jms.sender.OrderGuideSender;

public class ItemServiceImpl extends ItemServiceAdapter {
	private ItemDAO itemDAO;
	private OrderGuideSender orderGuideMessenger;
	
	public ItemServiceImpl(){}
	
	public void setOrderGuide(final OrderGuideSender orderGuideMessenger) {
		this.orderGuideMessenger = orderGuideMessenger;
	}

	public void setItemDAO(final ItemDAO itemDAO){
		this.itemDAO = itemDAO;
	}
	
	public Item getItem(final String itemNumber) {
		return this.itemDAO.getItem(itemNumber);
	}

	public List<Item> getItemsByClass(final String itemClass) {
		return this.itemDAO.getItemsByClass(itemClass);
	}

	public List<Item> getItemsByFamily(final String family) {
		return this.itemDAO.getItemsByFamily(family);
	}

	public List<Item> getItemsByHeader(final String header) {
		return this.itemDAO.getItemsByHeader(header);
	}

	public List<Item> search(final String criteria) {
		return this.itemDAO.search(criteria);
	}

	public List<Item> getItemsInOrderGuide(final String chainCode, final String familyCode) {
		return this.itemDAO.getItemsInOrderGuide(chainCode, familyCode);
	}

	public List<Item> getItemsWithOrderGuide(final String chainCode, final PriceBookFamily family){
		final List<Item> orderGuide = this.itemDAO.getItemsInOrderGuide(chainCode, family.getFamilyCode());
		
		for(Item item : family.getItems()){
			item.setInOrderGuide(orderGuide.contains(item));
		}

		return family.getItems();
	}
	/*
	 * (non-Javadoc)
	 * @see com.parksexpress.services.ItemService#getPriceingForItem(java.lang.String)
	 * 
	 * The idea is that we'll get the pricing for the item. This will include the srp 
	 * and everything else.
	 */
	public Pricing getPriceingForItem(final String itemNumber) {
		return null;
	}

	public Item getItem(final String itemNumber, final String chainCode) {
		final Item item = this.getItem(itemNumber);
		item.setInOrderGuide(this.itemDAO.isItemInOrderGuide(chainCode, itemNumber));
		return item;
	}

	public boolean orderGuideItemAdd(final String chainCode, final Item item) {
		final int i = this.itemDAO.orderGuideItemAdd(chainCode, item);
		
		if (i > 0){
			this.orderGuideMessenger.sendOrderGuideMessage(chainCode, item, OrderGuideSender.ADD);
			return true;
		}else{
			return false;
		}
	}

	public boolean orderGuideItemRemove(final String chainCode, final Item item) {
		final int i = this.itemDAO.orderGuideItemRemove(chainCode, item);
		
		if (i > 0){
			this.orderGuideMessenger.sendOrderGuideMessage(chainCode, item, OrderGuideSender.DELETE);
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public List<Item> getDiscontinuedAndSuspendedItemsByFamily(String familyCode) {
		return this.itemDAO.getDiscontinuedAndSuspendedItemsByFamily(familyCode);
	}

	@Override
	public List<Item> find(String criteria) {
		return this.itemDAO.find(criteria);
	}
}
