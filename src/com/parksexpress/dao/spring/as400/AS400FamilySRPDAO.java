package com.parksexpress.dao.spring.as400;

import java.math.BigDecimal;
import java.text.ParseException;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.as400.util.DateChanger;
import com.parksexpress.as400.util.Spacing;
import com.parksexpress.domain.PriceBookFamily;

public class AS400FamilySRPDAO extends AS400PriceBookAdapter {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private Logger logger = Logger.getRootLogger();
	private String warehouse;
	private String company;
	private String division;
	private String department;
	private String fixed = "D";
	private String percent = "S";
	
	public AS400FamilySRPDAO(){}

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

	public void setDataSource(final DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	@Override
	public boolean updateFutureFamilyPricing(final PriceBookFamily family, final String srpBook){
			// Need to figure out if we need to add or update.
			final String sql = "SELECT COUNT(*) AS CNT FROM SSCSRPCP WHERE SSCSRPN = ? AND SSCTTNP=? AND SSCSCDT=?";
			
			try {
				final String date = DateChanger.convertDateToAS400(family.getPricing().getEffectiveDate());
				final int count = this.simpleJdbcTemplate.queryForInt(sql, new Object[] {
						srpBook,
						Spacing.setCorrectSpacing(family.getFamilyCode(),
								Spacing.ITEM), date, });
				
				if (count > 0) {
					this.logger.debug("Updating Future Family Pricing");
					this.updateFutureFamilyPricing(family, srpBook, date);
				} else {
					this.logger.debug("Inserting into Future Family");
					this.insertFutureFamilyPricing(family, srpBook, date);
				}
				
				return true;
			} catch (ParseException e) {
				return false;
			}
	}
	
	private void updateFutureFamilyPricing(PriceBookFamily family,
			String srpBook, String date) {
		
		String code = this.percent;
		BigDecimal price = family.getPricing().getPercent();
		
		if (family.getPricing().isFixed()) {
			code = this.fixed;
			price = family.getPricing().getPrice();
		}
		
		String sql = "UPDATE SSCSRPCP SET SSCSRPP = " + price.toString() + ", SSCMCDE = ? WHERE SSCSRPN =? AND SSCTTNP=? WITH NC";
		
		this.simpleJdbcTemplate.update(sql, new Object[]{code, srpBook, 
				Spacing.setCorrectSpacing(family.getFamilyCode(), Spacing.ITEM),});
		
		sql = "UPDATE SSBSRPDP SET SSBDTEF = ? WHERE SSBSRPN=? WITH NC";
		this.simpleJdbcTemplate.update(sql, new Object[]{date, srpBook});
		
	}

	private void insertFutureFamilyPricing(PriceBookFamily family,
			String srpBook, String date) {

		//Need to get a code from the AS400 first.
		final String codeSQL = "SELECT SSQRUCD FROM SSQSRPHP WHERE SSQSRPN=?";
		final String rucd = this.simpleJdbcTemplate.queryForObject(codeSQL, String.class, new Object[]{srpBook});
		String code = this.percent;
		BigDecimal price = family.getPricing().getPercent();

		if (family.getPricing().isFixed()) {
			code = this.fixed;
			price = family.getPricing().getPrice();
		}
		
		final String sql = "INSERT INTO SSCSRPCP (SSCCMPN, SSCDIVN, SSCDPTN, SSCSRPN, SSCTYTC, "
					 + "SSCTTNP, SSCSRPP, SSCMCDE, SSCMUPT, SSCRUCD, SSCWHSN, SSCSCDT) "
						+ "VALUES(" + StringUtils.replace(this.company, "\"", "'")  + ", " 
						+ StringUtils.replace(this.division, "\"", "'") + ", " 
						+ StringUtils.replace(this.department, "\"", "'") + ", '"
						+ srpBook + "', 'F', '" + Spacing.setCorrectSpacing(family.getFamilyCode(), Spacing.ITEM) 
						+ "', " + price.toString() + ", '" + code + "', 'P', '" + rucd + "', "
						+ StringUtils.replace(this.warehouse, "\"", "'") + ",'" + date +  "') WITH NC";
		
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
	}

	@Override
	public boolean addFamilyPricing(final PriceBookFamily family, final String srpBook) {
		// Need to figure out if we need to add or update.
		final String sql = "SELECT COUNT(*) AS CNT FROM SSSSRPCP WHERE SSSSRPN = ? AND SSSTTNP=?";

		final int count = this.simpleJdbcTemplate.queryForInt(sql, new Object[] {
				srpBook,
				Spacing.setCorrectSpacing(family.getFamilyCode(), Spacing.ITEM), });

		if (count > 0) {
			this.logger.debug("Updating family");
			this.updateFamilyPricing(family, srpBook);
		} else {
			this.logger.debug("Inserting into family");
			this.insertFamilyPricing(family, srpBook);
		}

		return true;
	}

	private void insertFamilyPricing(final PriceBookFamily family, final String srpBook) {
		//Need to get a code from the AS400 first.
		final String codeSQL = "SELECT SSQRUCD FROM SSQSRPHP WHERE SSQSRPN=?";
		final String rucd = this.simpleJdbcTemplate.queryForObject(codeSQL, String.class, new Object[]{srpBook});
		
		String code = "S";
		BigDecimal price = family.getPricing().getPercent();
		
		if (family.getPricing().isFixed()) {
			code = "D";
			price = family.getPricing().getPrice();
		}

		//or whatever reason cannot use parameters. Doesn't work. Throws truncation error
		final String sql = "INSERT INTO SSSSRPCP (SSSCMPN, SSSDIVN, SSSDPTN, SSSSRPN, SSSTYTC, "
				+ "SSSTTNP, SSSSRPP, SSSMCDE, SSSMUPT, SSSRUCD, SSSWHSN) " 
				+ "VALUES(" + StringUtils.replace(this.company, "\"", "'")  + ", " 
				+ StringUtils.replace(this.division, "\"", "'") + ", " 
				+ StringUtils.replace(this.department, "\"", "'") + ", '"
				+ srpBook + "', 'F', '" + Spacing.setCorrectSpacing(family.getFamilyCode(), Spacing.ITEM) 
				+ "', " + price.toString() + ", '" + code + "', 'P', '" + rucd + "', "
				+ StringUtils.replace(this.warehouse, "\"", "'") + ") WITH NC";

		this.logger.warn(sql);
		this.simpleJdbcTemplate.getJdbcOperations().execute(sql);
	}

	private void updateFamilyPricing(final PriceBookFamily family, final String srpBook) {
		String code = this.percent;
		BigDecimal price = family.getPricing().getPercent();
		
		if (family.getPricing().isFixed()) {
			code = this.fixed;
			price = family.getPricing().getPrice();
		}

		final String sql = "UPDATE SSSSRPCP SET SSSSRPP =" + price.toString() + ", SSSMCDE = ? "
				+ "WHERE SSSSRPN = ? AND SSSTTNP=? WITH NC";


		this.simpleJdbcTemplate.update(sql, new Object[] {
				code,
				srpBook,
				Spacing.setCorrectSpacing(family.getFamilyCode(), Spacing.ITEM), });
	}

	@Override
	public boolean deleteClassFamilyPricing(final String family, final String srpBook) {
		this.logger.debug("DELETING family pricing for family: " + family + " with book: " + srpBook);
		
		final String currentSQL = "DELETE FROM SSSSRPCP WHERE SSSTTNP = ? AND  SSSSRPN=? WITH NC";
		final String futureSQL = "DELETE FROM SSCSRPCP WHERE SSCTTNP =? AND SSCSRPN =? WITH NC";
		
		this.simpleJdbcTemplate.update(futureSQL, new Object[]{Spacing.setCorrectSpacing(family, Spacing.ITEM), srpBook,});
		this.simpleJdbcTemplate.update(currentSQL, new Object[]{Spacing.setCorrectSpacing(family, Spacing.ITEM), srpBook,});
		return true;
	}
}
