package com.parksexpress.dao.spring.as400;

import com.parksexpress.as400.util.Spacing;

public class AS400BaseDAO {
	String getStoreSQL(String[] chainCode) {
		final StringBuffer stores = new StringBuffer("(");

		for (int i = 0; i < chainCode.length; i++) {
			if (i == chainCode.length - 1) {
				stores.append("'" + Spacing.setCorrectSpacing(chainCode[i], Spacing.CUSTOMER) + "'");
			} else {
				stores.append("'" + Spacing.setCorrectSpacing(chainCode[i], Spacing.CUSTOMER) + "', ");
			}
		}

		stores.append(")");
		return stores.toString();
	}
}
