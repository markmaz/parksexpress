package com.parksexpress.dao.spring.as400;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.as400.util.Spacing;
import com.parksexpress.domain.item.Item;

public class AS400OrderGuideDAO extends AS400ItemDAOAdapter {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private Logger logger = Logger.getRootLogger();
	private String warehouse;
	private String company;
	private String division;
	private String department;

	public AS400OrderGuideDAO(){
	}

	public String getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(final String warehouse) {
		this.warehouse = warehouse;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(final String company) {
		this.company = company;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(final String division) {
		this.division = division;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(final String department) {
		this.department = department;
	}

	public void setDataSource(final DataSource ds) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
	}

	@Override
	public int orderGuideItemAdd(final String chainCode, final Item item) {
		final String orderGuideNumber = this.getOrderGuideNumber(chainCode);
		final String sql = "INSERT INTO BBQORDGL0 (BBQCMPN, BBQDIVN, BBQDPTN, BBQWHSN, BBQORGN, "
				+ "BBQITMN, BBQCLSN, BBQPBHN, BBQPBHF) VALUES(" + StringUtils.replace(this.company, "\"", "'") 
				+ ", " + StringUtils.replace(this.division, "\"", "'") + ", "
				+ StringUtils.replace(this.department, "\"", "'") + ", " 
				+ StringUtils.replace(this.warehouse, "\"", "'") +", '" + Spacing.setCorrectSpacing(orderGuideNumber, Spacing.ORDER_GUIDE) + "', '" 
				+ Spacing.setCorrectSpacing(item.getNumber(), Spacing.ITEM) 
				+ "', '" + Spacing.setCorrectSpacing(item.getPriceBookClassCode(), Spacing.CLASS) + "', '"
				+ Spacing.setCorrectSpacing(item.getPriceBookHeaderCode(), Spacing.HEADER) + "', '"
				+ Spacing.setCorrectSpacing(item.getPriceBookFamilyCode(), Spacing.FAMILY) + "')";
		
		this.logger.debug(sql);
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
		return 0;
	}

	@Override
	public int orderGuideItemRemove(final String chainCode, final Item item) {
		final String orderGuideNumber = this.getOrderGuideNumber(chainCode);
		final String sql = "DELETE FROM BBQORDGL0 WHERE BBQITMN = '" 
				+ Spacing.setCorrectSpacing(item.getNumber(), Spacing.ITEM) + "' "
				+ "  AND BBQORGN = '" + Spacing.setCorrectSpacing(orderGuideNumber, Spacing.ORDER_GUIDE) + "'";
		 
		this.logger.debug(sql);		
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
		return 0;
	}

	private String getOrderGuideNumber(final String chainCode) {
		final String sql = "SELECT BBSORGN FROM BBSCHNHP WHERE BBSCSCD = ?";
		final String guide = this.simpleJdbcTemplate.queryForObject(sql, String.class,
				new Object[] { Spacing.setCorrectSpacing(chainCode, Spacing.CHAIN_CODE) });
		
		if(StringUtils.isEmpty(guide)){
			return "CHAS";
		}else{
			return guide.trim();
		}
	}
}
