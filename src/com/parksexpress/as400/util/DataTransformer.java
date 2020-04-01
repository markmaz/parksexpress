package com.parksexpress.as400.util;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.dao.ItemDAO;
import com.parksexpress.domain.item.AllowanceItem;

public class DataTransformer {
	private ItemDAO itemDAO;
	private SimpleJdbcTemplate simpleJdbcTemplate;

	public DataTransformer() {
	}

	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	public void setDataSource(DataSource ds) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
	}

	public void moveAllowanceData() {
		final List<AllowanceItem> items = this.itemDAO.getAllowances(null, null, null, null);

		final String sql = "INSERT INTO itemAllowances (itemNumber, unit, description, cartonUPCNumber, "
				+ " retailUPCNumber, marketCost, costAllowance, specialPrice, startDate, " 
				+ "expirationDate)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		for (AllowanceItem item : items) {
			this.simpleJdbcTemplate.update(sql, new Object[] {item.getCheckDigitItemNumber(), 
					item.getSize(), item.getDescription(), item.getCartonUPCNumber(), 
					item.getRetailUPCNumber(), item.getMarketCost(),
					item.getCostAllowance(), item.getSpecialPrice(), item.getStartDate(), 
					item.getExpirationDate(),});
		}
	}
}
