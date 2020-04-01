package com.parksexpress.dao.spring.as400;

import java.util.List;

import com.parksexpress.dao.PriceBookDAO;
import com.parksexpress.domain.FamilyPricingException;
import com.parksexpress.domain.ItemPricingException;
import com.parksexpress.domain.PriceBook;
import com.parksexpress.domain.PriceBookClass;
import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.Pricing;
import com.parksexpress.domain.item.Item;

public class AS400PriceBookAdapter implements PriceBookDAO{
	public AS400PriceBookAdapter(){
	}
	
	public boolean addClassPricing(PriceBookClass priceBookClass, String srpBook) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addFamilyPricing(PriceBookFamily family, String srpBook) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addItemPricing(Item item, String srpBook) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteClassFamilyPricing(String family, String srpBook) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteItemPricing(String itemNumber, String srpBook) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deletePricingForItem(Item item, String priceBook) {
		// TODO Auto-generated method stub
		return false;
	}

	public PriceBookClass getClass(String criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public PriceBookFamily getFamily(String criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<FamilyPricingException> getFamilyExceptionPricing(
			PriceBookFamily family, String chainCode, int priority) {
		// TODO Auto-generated method stub
		return null;
	}

	public PriceBookHeader getHeader(String criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ItemPricingException> getItemLevelPriceBookExceptionsForFamilyForAllZones(
			String chainCode, String familyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getItemLevelSRPBookName(String srpBookFamilyLevel) {
		// TODO Auto-generated method stub
		return null;
	}

	public PriceBook getPriceBookByZone(String zone) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PriceBook> getPriceBooksByPriority(int priority,
			String chainCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PriceBook> getPriceBooksByPriority(int priority,
			List<PriceBook> parentBooks) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Pricing> getPricingForFamily(String chainCode,
			String familyCode, int priority) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Pricing> getPricingForFamily(String chainCode,
			String priceBook, String familyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public Pricing getPricingForItem(String itemNumber, String priceBook) {
		// TODO Auto-generated method stub
		return null;
	}

	public PriceBookHeader getSkeleton(String criteria, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PriceBook> getZones(int priority, String chainCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PriceBookClass> searchClasses(String criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PriceBookFamily> searchFamilies(String criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PriceBookHeader> searchHeaders(String criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean updateFuturePricingForItem(Item item, String priceBook,
			String effectiveDate, float srp, String srpCode) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updatePricingForItem(Item item, String priceBook) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateFutureFamilyPricing(PriceBookFamily family,
			String srpBook) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PriceBookClass> getAllPriceBookClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PriceBookFamily> getAllPriceBookFamilies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PriceBookHeader> getAllPriceBookHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

}
