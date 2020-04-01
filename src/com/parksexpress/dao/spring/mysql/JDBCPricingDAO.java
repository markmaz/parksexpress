package com.parksexpress.dao.spring.mysql;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.dao.PricingDAO;
import com.parksexpress.domain.Pricing;
import com.parksexpress.util.DateUtil;

public class JDBCPricingDAO implements PricingDAO{
	private static final String ITEM_SQL = "SELECT effectiveDate FROM future_item_pricing WHERE itemNumber = ? AND priceBook = ? and str_to_date(effectiveDate, '%m/%d/%Y') > str_to_date(?, '%m/%d/%Y')";
	private static final String FAMILY_SQL = "SELECT effectiveDate FROM future_family_pricing WHERE levelID = ? AND priceBook = ? and str_to_date(effectiveDate, '%m/%d/%Y') > str_to_date(?, '%m/%d/%Y')";
	private SimpleJdbcTemplate simpleJdbcTemplate;
	
	public JDBCPricingDAO(DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}
	
	public JDBCPricingDAO(){
	}

	public void setDataSource(DataSource dataSource){
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	public String getEffectivePricingForItem(String item, String priceBook){
		RowMapper mapper = new RowMapper() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("effectiveDate");
			}
		};	
		
		List<String> list = simpleJdbcTemplate.getJdbcOperations().query(ITEM_SQL, new Object[]{item, priceBook, DateUtil.today()}, mapper);
		
		if(list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String getEffectivePricingForFamily(String familyCode, String priceBook){
		RowMapper mapper = new RowMapper() {
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString("effectiveDate");
			}
		};	
		
		List<String> list = simpleJdbcTemplate.getJdbcOperations().query(FAMILY_SQL, new Object[]{familyCode, priceBook, DateUtil.today()}, mapper);
		
		if(list.size() > 0){
			return list.get(0);
		}
		
		return null;
	}

	@Override
	public Pricing getFuturePricingForItem(String item, String book) {
		RowMapper mapper = new RowMapper(){
			public Pricing mapRow(ResultSet rs, int row) throws SQLException {
				Pricing pricing = new Pricing();
				pricing.setEffectiveDate(rs.getString("effectiveDate"));
				
				if(StringUtils.isNotEmpty(rs.getString("srpCode"))){
					if(rs.getString("srpCode").equalsIgnoreCase("F")){
						pricing.setFixed(true);
						pricing.setPrice(rs.getBigDecimal("srp"));
					}else{
						pricing.setFixed(false);
						pricing.setPercent(rs.getBigDecimal("srp"));
					}
				}
				return pricing;
			}
		};
		
		String sql = "SELECT srp, srpCode, effectiveDate FROM future_item_pricing WHERE priceBook=? and itemNumber=? and str_to_date(effectiveDate, '%m/%d/%Y') > str_to_date(?, '%m/%d/%Y')";
		List<Pricing> list = simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[]{book, item, DateUtil.today()}, mapper);
		
		if(list.size() == 0){
			return null;
		}
		
		return list.get(0);
	}

	@Override
	public Pricing getFuturePricingForFamily(String family, String book) {
		RowMapper mapper = new RowMapper(){
			public Pricing mapRow(ResultSet rs, int row) throws SQLException {
				Pricing pricing = new Pricing();
				pricing.setEffectiveDate(rs.getString("effectiveDate"));
				
				if(rs.getString("srpCode").equalsIgnoreCase("D")){
					pricing.setFixed(true);
					pricing.setPrice(rs.getBigDecimal("srp"));
				}else{
					pricing.setFixed(false);
					pricing.setPercent(rs.getBigDecimal("srp"));
				}
				
				return pricing;
			}
		};
		
		String sql = "SELECT srp, srpCode, effectiveDate FROM future_family_pricing WHERE priceBook=? and levelID=? and str_to_date(effectiveDate, '%m/%d/%Y') > str_to_date(?, '%m/%d/%Y')";
		List<Pricing> list = simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[]{book, family, DateUtil.today()}, mapper);
		
		if(list.size() == 0){
			return null;
		}
		
		return list.get(0);
	}
	

}
