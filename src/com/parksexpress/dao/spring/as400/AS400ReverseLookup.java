package com.parksexpress.dao.spring.as400;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.as400.util.Spacing;
import com.parksexpress.dao.ReverseLookupDAO;
import com.parksexpress.domain.PriceBookClass;
import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.Vendor;
import com.parksexpress.domain.item.Item;
import com.parksexpress.domain.item.ReverseLookupItem;
import com.parksexpress.web.controllers.ReverseLookupController;

public class AS400ReverseLookup extends AS400BaseDAO implements ReverseLookupDAO {
	private String[] sortOrder = {"HHIITMN", "HHIVNDN", "FFIPCKI", "FFISZEI", "FFIIDE1", "FFIUPCN", "FFIUPCP", "TOTAL", "EXT_COST_AMT", "EXT_SRP", "PROFIT_PER"};
	private static final String MAIN_SQL = "SELECT SUM(HHIQYSA) AS TOTAL, HHICUSN AS CUSTOMER_NUMBER, HHIITMN AS ITEM_NUMBER, " +
			"FFISZEI AS SIZE, FFIPCKI AS PACK, FFIIDE1 AS DESCRIPTION, FFIDGTC AS CHECK_DIGIT, FFDCNMB AS CUSTOMER_NAME, " +
			"FFIUPCN AS CARTON_UPC, FFIUPCP AS RETAIL_UPC, FFVVNMV AS VENDOR_NAME, HHIVNDN AS VENDOR_NUMBER, " +
			"FFIBNCD AS BRAND, SUM(HHIEXSN) AS EXT_COST_AMT, SUM(HHIEXSR) AS EXT_SRP, (SUM(HHIEXSN) / SUM(HHIEXSR) * 100) as PROFIT_PER FROM HHIORDDC0, FFIITMAC0, " +
			"FFVVNDAP, FFDCSTBC0 WHERE HHIDTEI >=? AND HHIDTEI <= ? AND HHICUSN in   ";

	private static final String END_SQL = " AND HHICUSN = FFDCUSN " + 
			"AND FFIITMN = HHIITMN AND HHIVNDN = FFVVNDN GROUP BY HHICUSN, HHIITMN, FFISZEI, FFIPCKI, FFIIDE1, FFIDGTC, "
			+ "FFDCNMB, FFIUPCN, FFIUPCP, HHIVNDN, FFVVNMV, FFIBNCD ORDER BY FFDCNMB,  ";
	
	private static final String MAIN_CHAIN_SQL = "SELECT SUM(HHIQYSA) AS TOTAL, HHIITMN AS ITEM_NUMBER, " +
	"FFISZEI AS SIZE, FFIPCKI AS PACK, FFIIDE1 AS DESCRIPTION, FFIDGTC AS CHECK_DIGIT, " +
	"FFIUPCN AS CARTON_UPC, FFIUPCP AS RETAIL_UPC, FFVVNMV AS VENDOR_NAME, HHIVNDN AS VENDOR_NUMBER, " +
	"FFIBNCD AS BRAND, SUM(HHIEXSN) AS EXT_COST_AMT, SUM(HHIEXSR) AS EXT_SRP, (SUM(HHIEXSN) / SUM(HHIEXSR) * 100) as PROFIT_PER FROM HHIORDDC0, FFIITMAC0, " +
	"FFVVNDAP, FFDCSTBC0 WHERE HHIDTEI >=? AND HHIDTEI <= ? AND HHICUSN IN (SELECT FFDCUSN FROM FFDCSTBC0 WHERE FFDCSCD = ";

	private static final String END_CHAIN_SQL = " AND HHICUSN = FFDCUSN " + 
	"AND FFIITMN = HHIITMN AND HHIVNDN = FFVVNDN GROUP BY HHIITMN, FFISZEI, FFIPCKI, FFIIDE1, FFIDGTC, "
	+ "FFIUPCN, FFIUPCP, HHIVNDN, FFVVNMV, FFIBNCD ORDER BY  ";

	private SimpleJdbcTemplate simpleJdbcTemplate;

	private Logger log = Logger.getLogger(ReverseLookupController.class);
	
	public AS400ReverseLookup() {
	}

	public void setDataSource(DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] chainCode, boolean isSummary, int sortOrderIndex) {
		final String sql = AS400ReverseLookup.MAIN_SQL + this.getStoreSQL(chainCode) + AS400ReverseLookup.END_SQL + sortOrder[sortOrderIndex];
		this.log.debug(sql);
		this.log.debug("Arguments: " + startDate + ", " + endDate + ", " + chainCode);
		
		return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate },
				new ReverseLookupItemMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] chainCode, Vendor vendor, boolean isSummary, int sortOrderIndex) {
		String sql = "";
		final String vendorNumber = Spacing.setCorrectSpacing(vendor.getNumber(), Spacing.VENDOR);
		this.log.debug("Arguments: " + startDate + ", " + endDate + ", " + chainCode + ", " + vendor); 

		if(isSummary){
			sql = AS400ReverseLookup.MAIN_CHAIN_SQL + "'" + Spacing.setCorrectSpacing(chainCode[0], Spacing.CHAIN_CODE) 
			+ "') AND HHIVNDN = ?" + AS400ReverseLookup.END_CHAIN_SQL + sortOrder[sortOrderIndex];
			
			return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate, vendorNumber },
					new ReverseLookupItemMapperForSummary());
		}else{
			sql = AS400ReverseLookup.MAIN_SQL + this.getStoreSQL(chainCode) 
			+  " AND HHIVNDN = ?" + AS400ReverseLookup.END_SQL + sortOrder[sortOrderIndex];

			return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate, vendorNumber },
					new ReverseLookupItemMapper());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] chainCode, PriceBookClass clazz, boolean isSummary, int sortOrderIndex) {
		String sql = "";
		final String classCode = Spacing.setCorrectSpacing(clazz.getClassCode(), Spacing.CLASS);
		this.log.debug("Arguments: " + startDate + ", " + endDate + ", " + chainCode + ", " + clazz);
		
		if(isSummary){
			sql = AS400ReverseLookup.MAIN_CHAIN_SQL + "'" + Spacing.setCorrectSpacing(chainCode[0], Spacing.CHAIN_CODE) 
			+ "') AND HHICLSN = ?" + AS400ReverseLookup.END_CHAIN_SQL + sortOrder[sortOrderIndex];
			
			return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate, classCode },
					new ReverseLookupItemMapperForSummary());
		}else{			
			sql = AS400ReverseLookup.MAIN_SQL + this.getStoreSQL(chainCode) 
			+ " AND HHICLSN = ?" + AS400ReverseLookup.END_SQL + sortOrder[sortOrderIndex];
			
			return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate, classCode },
					new ReverseLookupItemMapper());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] chainCode, PriceBookHeader header, boolean isSummary, int sortOrderIndex) {
		String sql = "";
		final String headerCode = Spacing.setCorrectSpacing(header.getHeaderCode(), Spacing.HEADER);
		this.log.debug("Arguments: " + startDate + ", " + endDate + ", " + chainCode + ", " + header);
		
		if(isSummary){
			sql = AS400ReverseLookup.MAIN_CHAIN_SQL + "'" + Spacing.setCorrectSpacing(chainCode[0], Spacing.CHAIN_CODE) 
			+ "') AND HHIPBHN = ?" + AS400ReverseLookup.END_CHAIN_SQL + sortOrder[sortOrderIndex];		
			
			log.debug("SQL: " + sql);
			return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate, headerCode },
					new ReverseLookupItemMapperForSummary());
		}else{			
			sql = AS400ReverseLookup.MAIN_SQL + this.getStoreSQL(chainCode) 
			+ " AND HHIPBHN = ?" + AS400ReverseLookup.END_SQL + sortOrder[sortOrderIndex];
			log.debug("SQL: " + sql);
			
			return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate, headerCode },
					new ReverseLookupItemMapper());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] chainCode, PriceBookFamily family, boolean isSummary, int sortOrderIndex) {
		String sql = "";
		final String familyCode = Spacing.setCorrectSpacing(family.getFamilyCode(), Spacing.FAMILY_IN_HISTORY);
		this.log.debug("Arguments: " + startDate + ", " + endDate + ", " + chainCode + ", " + familyCode);
		
		if(isSummary){
			sql = AS400ReverseLookup.MAIN_CHAIN_SQL + "'" + Spacing.setCorrectSpacing(chainCode[0], Spacing.CHAIN_CODE) 
			+ "') AND HHIPBHF = ? " + AS400ReverseLookup.END_CHAIN_SQL + sortOrder[sortOrderIndex];
			
			return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate, familyCode },
					new ReverseLookupItemMapperForSummary());
		}else{			
			sql = AS400ReverseLookup.MAIN_SQL + this.getStoreSQL(chainCode) 
			+ " AND HHIPBHF = ? " + AS400ReverseLookup.END_SQL + sortOrder[sortOrderIndex];
			
			return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate, familyCode },
					new ReverseLookupItemMapper());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] chainCode, Item item, boolean isSummary, int sortOrderIndex) {
		String sql = "";
		this.log.debug(sql);
		final String itemNumber = Spacing.setCorrectSpacing(item.getNumber(), Spacing.ITEM);
		this.log.debug("Arguments: " + startDate + ", " + endDate + ", " + chainCode + ", " + item);
		
		if(isSummary){
			sql = AS400ReverseLookup.MAIN_CHAIN_SQL + "'" + Spacing.setCorrectSpacing(chainCode[0], Spacing.CHAIN_CODE) 
			+ "') AND HHIITMN = ?" + AS400ReverseLookup.END_CHAIN_SQL + sortOrder[sortOrderIndex];		
			
			return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate, itemNumber },
					new ReverseLookupItemMapperForSummary());
		}else{			
			sql = AS400ReverseLookup.MAIN_SQL + this.getStoreSQL(chainCode) 
			+ " AND HHIITMN = ?" + AS400ReverseLookup.END_SQL + sortOrder[sortOrderIndex];
			
			return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate, itemNumber },
					new ReverseLookupItemMapper());
		}

	}

	@Override
	public List<ReverseLookupItem> lookUp(String startDate, String endDate, String[] chainCode, String brand, boolean isSummary, int sortOrderIndex) {
		return null;
	}

	private static final class ReverseLookupItemMapperForSummary implements RowMapper {
		public ReverseLookupItemMapperForSummary(){}
		
		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final ReverseLookupItem item = new ReverseLookupItem();
			item.setBrand(rs.getString("BRAND"));
			item.setVendorName(rs.getString("VENDOR_NAME"));
			item.setVendorNumber(rs.getString("VENDOR_NUMBER"));
			item.setNumber(rs.getString("ITEM_NUMBER").trim());
			item.setCheckDigit(rs.getString("CHECK_DIGIT"));
			item.setPack(rs.getString("PACK"));
			item.setDescription(rs.getString("DESCRIPTION"));
			item.setSize(rs.getString("SIZE"));
			item.setCartonUPCNumber(rs.getString("CARTON_UPC"));
			item.setRetailUPCNumber(rs.getString("RETAIL_UPC"));
			item.setQuantity(rs.getLong("TOTAL"));
			item.setCostAmount(rs.getBigDecimal("EXT_COST_AMT"));
			item.setSrpAmount(rs.getBigDecimal("EXT_SRP"));
			item.setProfitPercent(this.calculateProfit(item.getSrpAmount().floatValue(), item.getCostAmount().floatValue()));
			return item;
		}
		
		private float calculateProfit(float srp, float cost){
			final float profitDollars = srp - cost;
			final float percent = 100f;
			
			float profitPercent = 0;
            
			if(srp != 0){
				profitPercent = (profitDollars / srp) * percent;
			}
			
			return profitPercent;
		}
		
	}
	
	private static final class ReverseLookupItemMapper implements RowMapper {
		public ReverseLookupItemMapper(){}
		
		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final ReverseLookupItem item = new ReverseLookupItem();
			item.setBrand(rs.getString("BRAND"));
			item.setCustomerNumber(rs.getString("CUSTOMER_NUMBER"));
			item.setCustomerName(rs.getString("CUSTOMER_NAME"));
			item.setVendorName(rs.getString("VENDOR_NAME"));
			item.setVendorNumber(rs.getString("VENDOR_NUMBER"));
			item.setNumber(rs.getString("ITEM_NUMBER").trim());
			item.setCheckDigit(rs.getString("CHECK_DIGIT"));
			item.setPack(rs.getString("PACK"));
			item.setDescription(rs.getString("DESCRIPTION"));
			item.setSize(rs.getString("SIZE"));
			item.setCartonUPCNumber(rs.getString("CARTON_UPC"));
			item.setRetailUPCNumber(rs.getString("RETAIL_UPC"));
			item.setQuantity(rs.getLong("TOTAL"));
			item.setCostAmount(rs.getBigDecimal("EXT_COST_AMT"));
			item.setSrpAmount(rs.getBigDecimal("EXT_SRP"));
			item.setProfitPercent(this.calculateProfit(item.getSrpAmount().floatValue(), item.getCostAmount().floatValue()));
			//item.setProfitPercent(rs.getFloat("PROFIT_PER"));
			return item;
		}
		
		private float calculateProfit(float srp, float cost){
			final float profitDollars = srp - cost;
			final float percent = 100f;
			
			float profitPercent = 0;
            
			if(srp != 0){
				profitPercent = (profitDollars / srp) * percent;
			}
			
			return profitPercent;
		}
		
	}

	@Override
	public List<ReverseLookupItem> lookUp(String startDate, String endDate,
			String[] chainCode, List<Item> items, boolean isSummary, int sortOrderIndex) {
		String sql = "";
		String itemNumbers = "";
			
		for(int i = 0; i < items.size(); i++){
			
			if(i == 0){
				itemNumbers = itemNumbers + "'" + Spacing.setCorrectSpacing(items.get(i).getNumber(), Spacing.ITEM) + "'";
			}else{
				itemNumbers = itemNumbers + ",'" + Spacing.setCorrectSpacing(items.get(i).getNumber(), Spacing.ITEM) + "'";
			}
		}

		this.log.debug("Arguments: " + startDate + ", " + endDate + ", " + chainCode + ", " + itemNumbers);
		
		if(isSummary){
			sql = AS400ReverseLookup.MAIN_CHAIN_SQL + "'" + Spacing.setCorrectSpacing(chainCode[0], Spacing.CHAIN_CODE) 
			+ "') AND HHIITMN in (" + itemNumbers + ") " + AS400ReverseLookup.END_CHAIN_SQL + sortOrder[sortOrderIndex];		
			
			return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate}, new ReverseLookupItemMapperForSummary());
		}else{			
			sql = AS400ReverseLookup.MAIN_SQL + this.getStoreSQL(chainCode) 
			+ " AND HHIITMN in (" + itemNumbers + ") "+ AS400ReverseLookup.END_SQL + sortOrder[sortOrderIndex];
			
			return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate},
					new ReverseLookupItemMapper());
		}
	}
}
