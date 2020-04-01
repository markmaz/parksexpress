package com.parksexpress.dao;

import com.parksexpress.domain.EffectiveDate;

public interface SrpDAO {
	EffectiveDate getEffectiveDateInFamily(String book, String familyNumber);
	EffectiveDate getEffectiveDateInItem(String book, String itemNumber);
}
