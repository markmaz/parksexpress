package com.parksexpress.dao.spring.as400;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.as400.util.DateChanger;
import com.parksexpress.as400.util.RpgSrp;
import com.parksexpress.as400.util.Spacing;
import com.parksexpress.domain.Chain;
import com.parksexpress.domain.Store;
import com.parksexpress.domain.item.ApprovedDistributionsItem;

public class AS400ApprovedDistributions extends AS400ItemDAOAdapter {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private RpgSrp rpg;
	private Logger log = Logger.getRootLogger();
	private static final String FORCE_OUT = "TTBITRSC0";
	private static final String TRADE_SHOW = "WEB_TRADE";
	private static final String ITEM_BY_NUMBER = "FFIITMAC0";
	private static final String FFJ_FILE = "FFJITMBC2";

	public AS400ApprovedDistributions() {
	}

	public void setDataSource(DataSource ds) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
	}

	public void setRpgSrp(RpgSrp rpg) {
		this.rpg = rpg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(String storeNumber, String itemNumber) {
		final String customer = Spacing.setCorrectSpacing(storeNumber, Spacing.CUSTOMER);
		final String item = Spacing.setCorrectSpacing(itemNumber, Spacing.ITEM);

		final StringBuffer sSQL = new StringBuffer("SELECT FFIDGTC as check_digit, TTBITMN as item_number, TTBQYOA as quantity, ");
		sSQL.append("TTBDTES as ship_date, TTBCUSN as customer, ");
		sSQL.append("FFIIDE1 as description, FFISZEI as size, FFIPCKI as pack, FFIUPCP as retail_upc, FFIUPCN as carton_upc, ");
		sSQL.append("FFJQOHH as inventory_level FROM ");
		sSQL.append(AS400ApprovedDistributions.ITEM_BY_NUMBER + " AS Item, ");
		sSQL.append(AS400ApprovedDistributions.FORCE_OUT + " AS Force, ");
		sSQL.append(AS400ApprovedDistributions.FFJ_FILE + " WHERE ");
		sSQL.append(" AND Item.FFIITMN = Force.TTBITMN AND Force.TTBCUSN = ? ");
		sSQL.append("AND FFIITMN = FFJITMN AND FFIITMN = ? ");
		sSQL.append("UNION ");
		sSQL.append("SELECT FFIDGTC, OOIITMN as item_number, OOIQYOA as quantity, OOIDTES as ship_date, OOICUSN as customer, ");
		sSQL.append("FFIIDE1 as description, FFISZEI as size, FFIPCKI as pack, FFIUPCP as retail_upc, FFIUPCN as carton_upc, ");
		sSQL.append("FFJQOHH as inventory_level FROM ");
		sSQL.append(AS400ApprovedDistributions.ITEM_BY_NUMBER + ", " + AS400ApprovedDistributions.TRADE_SHOW + ", ");
		sSQL.append(AS400ApprovedDistributions.FFJ_FILE + " ");
		sSQL.append(" WHERE FFIITMN = OOIITMN AND OOICUSN = ?");
		sSQL.append(" AND FFIITMN = FFJITMN AND FFIITMN = ? ORDER BY Ship_Date");

		this.log.debug(sSQL.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sSQL.toString(), new Object[] { customer, item, customer, item },
				new ApprovedDistributionRowMapper(rpg, customer));

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(Chain chain, String itemNumber) {
		final String item = Spacing.setCorrectSpacing(itemNumber, Spacing.ITEM);

		final StringBuffer sSQL = new StringBuffer("SELECT FFIDGTC as check_digit, TTBITMN as item_number, TTBQYOA as quantity, ");
		sSQL.append("TTBDTES as ship_date, TTBCUSN as customer, ");
		sSQL.append("FFIIDE1 as description, FFISZEI as size, FFIPCKI as pack, FFIUPCP as retail_upc, FFIUPCN as carton_upc, ");
		sSQL.append("FFJQOHH as inventory_level FROM ");
		sSQL.append(AS400ApprovedDistributions.ITEM_BY_NUMBER + " AS Item, ");
		sSQL.append(AS400ApprovedDistributions.FORCE_OUT + " AS Force, ");
		sSQL.append(AS400ApprovedDistributions.FFJ_FILE + " WHERE ");
		sSQL.append(" AND Item.FFIITMN = Force.TTBITMN AND Force.TTBCUSN in " + this.getStoreSQLFromChain(chain));
		sSQL.append("AND FFIITMN = FFJITMN AND FFIITMN = ? ");
		sSQL.append("UNION ");
		sSQL.append("SELECT FFIDGTC, OOIITMN as item_number, OOIQYOA as quantity, OOIDTES as ship_date, OOICUSN as customer, ");
		sSQL.append("FFIIDE1 as description, FFISZEI as size, FFIPCKI as pack, FFIUPCP as retail_upc, FFIUPCN as carton_upc, ");
		sSQL.append("FFJQOHH as inventory_level FROM ");
		sSQL.append(AS400ApprovedDistributions.ITEM_BY_NUMBER + ", " + AS400ApprovedDistributions.TRADE_SHOW + ", ");
		sSQL.append(AS400ApprovedDistributions.FFJ_FILE + " ");
		sSQL.append(" WHERE FFIITMN = OOIITMN AND OOICUSN in " + this.getStoreSQLFromChain(chain));
		sSQL.append(" AND FFIITMN = FFJITMN AND FFIITMN = ? ORDER BY Ship_Date");

		this.log.debug(sSQL.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sSQL.toString(), new Object[] { item, item },
				new ApprovedDistributionRowMapper(chain));

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(final String storeNumber, final String startDate, final String endDate) {
		final String customer = Spacing.setCorrectSpacing(storeNumber, Spacing.CUSTOMER);
		String start;
		try {
			start = DateChanger.convertDateToAS400(startDate);
		} catch (ParseException e) {
			this.log.error(e.getMessage());
			start = "";
		}

		String end;
		try {
			end = DateChanger.convertDateToAS400(endDate);
		} catch (ParseException e) {
			this.log.error(e.getMessage());
			end = "";
		}

		final StringBuffer sSQL = new StringBuffer("SELECT FFIDGTC as check_digit, TTBITMN as item_number, TTBQYOA as quantity, ");
		sSQL.append("TTBDTES as ship_date, TTBCUSN as customer, ");
		sSQL.append("FFIIDE1 as description, FFISZEI as size, FFIPCKI as pack, FFIUPCP as retail_upc, FFIUPCN as carton_upc, ");
		sSQL.append("FFJQOHH as inventory_level FROM ");
		sSQL.append(AS400ApprovedDistributions.ITEM_BY_NUMBER + " AS Item, " + AS400ApprovedDistributions.FORCE_OUT + " AS Force, "
				+ AS400ApprovedDistributions.FFJ_FILE + " WHERE ");
		sSQL.append("Force.TTBDTES >=? AND Force.TTBDTES <= ?");
		sSQL.append(" AND Item.FFIITMN = Force.TTBITMN AND Force.TTBCUSN =? AND FFIITMN = FFJITMN ");
		sSQL.append("UNION ");
		sSQL.append("SELECT FFIDGTC, OOIITMN as item_number, OOIQYOA as quantity, OOIDTES as ship_date, OOICUSN as customer, ");
		sSQL.append("FFIIDE1 as description, FFISZEI as size, FFIPCKI as pack, FFIUPCP as retail_upc, FFIUPCN as carton_upc, ");
		sSQL.append("FFJQOHH as inventory_level FROM ");
		sSQL.append(AS400ApprovedDistributions.ITEM_BY_NUMBER + ", " + AS400ApprovedDistributions.TRADE_SHOW + ", "
				+ AS400ApprovedDistributions.FFJ_FILE + " ");
		sSQL.append("WHERE OOIDTES >=? AND OOIDTES <=? ");
		sSQL.append(" AND FFIITMN = OOIITMN AND OOICUSN = ? AND FFIITMN = FFJITMN ORDER BY Ship_Date");

		this.log.debug(sSQL.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sSQL.toString(),
				new Object[] { start, end, customer, start, end, customer }, new ApprovedDistributionRowMapper(rpg, customer));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(final Chain chain, final String startDate, final String endDate) {
		String start;
		try {
			start = DateChanger.convertDateToAS400(startDate);
		} catch (ParseException e) {
			this.log.error(e.getMessage());
			start = "";
		}

		String end;
		try {
			end = DateChanger.convertDateToAS400(endDate);
		} catch (ParseException e) {
			this.log.error(e.getMessage());
			end = "";
		}

		final StringBuffer sSQL = new StringBuffer("SELECT FFIDGTC as check_digit, TTBITMN as item_number, TTBQYOA as quantity, ");
		sSQL.append("TTBDTES as ship_date, TTBCUSN as customer, ");
		sSQL.append("FFIIDE1 as description, FFISZEI as size, FFIPCKI as pack, FFIUPCP as retail_upc, FFIUPCN as carton_upc, ");
		sSQL.append("FFJQOHH as inventory_level FROM ");
		sSQL.append(AS400ApprovedDistributions.ITEM_BY_NUMBER + " AS Item, " + AS400ApprovedDistributions.FORCE_OUT + " AS Force, "
				+ AS400ApprovedDistributions.FFJ_FILE + " WHERE ");
		sSQL.append("Force.TTBDTES >=? AND Force.TTBDTES <= ?");
		sSQL.append(" AND Item.FFIITMN = Force.TTBITMN AND Force.TTBCUSN in " + this.getStoreSQLFromChain(chain));
		sSQL.append(" AND FFIITMN = FFJITMN ");
		sSQL.append("UNION ");
		sSQL.append("SELECT FFIDGTC, OOIITMN as item_number, OOIQYOA as quantity, OOIDTES as ship_date, OOICUSN as customer, ");
		sSQL.append("FFIIDE1 as description, FFISZEI as size, FFIPCKI as pack, FFIUPCP as retail_upc, FFIUPCN as carton_upc, ");
		sSQL.append("FFJQOHH as inventory_level FROM ");
		sSQL.append(AS400ApprovedDistributions.ITEM_BY_NUMBER + ", " + AS400ApprovedDistributions.TRADE_SHOW + ", "
				+ AS400ApprovedDistributions.FFJ_FILE + " ");
		sSQL.append("WHERE OOIDTES >=? AND OOIDTES <=? ");
		sSQL.append(" AND FFIITMN = OOIITMN AND OOICUSN in " + this.getStoreSQLFromChain(chain));
		sSQL.append(" AND FFIITMN = FFJITMN ORDER BY Ship_Date");

		this.log.debug(sSQL.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sSQL.toString(),
				new Object[] { start, end, start, end}, new ApprovedDistributionRowMapper(chain));
	}

	private String getStoreSQLFromChain(Chain chain) {
		final StringBuffer sql = new StringBuffer("(");
		final List<Store> stores = chain.getStores();
		for (int i = 0; i < stores.size(); i++) {
			if (i == stores.size() - 1) {
				sql.append("'" + Spacing.setCorrectSpacing(stores.get(i).getNumber(), Spacing.CUSTOMER) + "'");
			} else {
				sql.append("'" + Spacing.setCorrectSpacing(stores.get(i).getNumber(), Spacing.CUSTOMER) + "', ");
			}
		}

		sql.append(")");
		return sql.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(final String storeNumber, final String itemNumber,
			final String startDate, final String endDate) {
		final String customer = Spacing.setCorrectSpacing(storeNumber, Spacing.CUSTOMER);
		final String item = Spacing.setCorrectSpacing(itemNumber, Spacing.ITEM);

		String start;
		try {
			start = DateChanger.convertDateToAS400(startDate);
		} catch (ParseException e) {
			this.log.error(e.getMessage());
			start = "";
		}

		String end;
		try {
			end = DateChanger.convertDateToAS400(endDate);
		} catch (ParseException e) {
			this.log.error(e.getMessage());
			end = "";
		}

		final StringBuffer sSQL = new StringBuffer("SELECT FFIDGTC as check_digit, TTBITMN as item_number, TTBQYOA as quantity, ");
		sSQL.append("TTBDTES as ship_date, TTBCUSN as customer, ");
		sSQL.append("FFIIDE1 as description, FFISZEI as size, FFIPCKI as pack, FFIUPCP as retail_upc, FFIUPCN as carton_upc, ");
		sSQL.append("FFJQOHH as inventory_level FROM ");
		sSQL.append(AS400ApprovedDistributions.ITEM_BY_NUMBER + " AS Item, " + AS400ApprovedDistributions.FORCE_OUT + " AS Force, "
				+ AS400ApprovedDistributions.FFJ_FILE + " WHERE ");
		sSQL.append("Force.TTBDTES >=? AND Force.TTBDTES <=?");
		sSQL.append(" AND Item.FFIITMN = Force.TTBITMN AND Force.TTBCUSN =? AND FFIITMN = FFJITMN AND FFJITMN = ? ");
		sSQL.append("UNION ");
		sSQL.append("SELECT FFIDGTC, OOIITMN as item_number, OOIQYOA as quantity, OOIDTES as ship_date, OOICUSN as customer, ");
		sSQL.append("FFIIDE1 as description, FFISZEI as size, FFIPCKI as pack, FFIUPCP as retail_upc, FFIUPCN as carton_upc, ");
		sSQL.append("FFJQOHH as inventory_level FROM ");
		sSQL.append(AS400ApprovedDistributions.ITEM_BY_NUMBER + ", " + AS400ApprovedDistributions.TRADE_SHOW + ", "
				+ AS400ApprovedDistributions.FFJ_FILE + " ");
		sSQL.append("WHERE OOIDTES >= ? AND OOIDTES <= ? ");
		sSQL.append(" AND FFIITMN = OOIITMN AND OOICUSN = ? AND FFIITMN = FFJITMN AND FFJITMN = ? ORDER BY Ship_Date");

		this.log.debug(sSQL.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sSQL.toString(),
				new Object[] { start, end, customer, item, start, end, customer, item }, new ApprovedDistributionRowMapper(rpg, customer));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ApprovedDistributionsItem> getApprovedDistributions(final Chain chain, final String itemNumber,
			final String startDate, final String endDate) {
		final String item = Spacing.setCorrectSpacing(itemNumber, Spacing.ITEM);

		String start;
		try {
			start = DateChanger.convertDateToAS400(startDate);
		} catch (ParseException e) {
			this.log.error(e.getMessage());
			start = "";
		}

		String end;
		try {
			end = DateChanger.convertDateToAS400(endDate);
		} catch (ParseException e) {
			this.log.error(e.getMessage());
			end = "";
		}

		final StringBuffer sSQL = new StringBuffer("SELECT FFIDGTC as check_digit, TTBITMN as item_number, TTBQYOA as quantity, ");
		sSQL.append("TTBDTES as ship_date, TTBCUSN as customer, ");
		sSQL.append("FFIIDE1 as description, FFISZEI as size, FFIPCKI as pack, FFIUPCP as retail_upc, FFIUPCN as carton_upc, ");
		sSQL.append("FFJQOHH as inventory_level FROM ");
		sSQL.append(AS400ApprovedDistributions.ITEM_BY_NUMBER + " AS Item, " + AS400ApprovedDistributions.FORCE_OUT + " AS Force, "
				+ AS400ApprovedDistributions.FFJ_FILE + " WHERE ");
		sSQL.append("Force.TTBDTES >=? AND Force.TTBDTES <=?");
		sSQL.append(" AND Item.FFIITMN = Force.TTBITMN AND Force.TTBCUSN in " + this.getStoreSQLFromChain(chain));
		sSQL.append("AND FFIITMN = FFJITMN AND FFJITMN = ? ");
		sSQL.append("UNION ");
		sSQL.append("SELECT FFIDGTC, OOIITMN as item_number, OOIQYOA as quantity, OOIDTES as ship_date, OOICUSN as customer, ");
		sSQL.append("FFIIDE1 as description, FFISZEI as size, FFIPCKI as pack, FFIUPCP as retail_upc, FFIUPCN as carton_upc, ");
		sSQL.append("FFJQOHH as inventory_level FROM ");
		sSQL.append(AS400ApprovedDistributions.ITEM_BY_NUMBER + ", " + AS400ApprovedDistributions.TRADE_SHOW + ", "
				+ AS400ApprovedDistributions.FFJ_FILE + " ");
		sSQL.append("WHERE OOIDTES >= ? AND OOIDTES <= ? ");
		sSQL.append(" AND FFIITMN = OOIITMN AND OOICUSN in " + this.getStoreSQLFromChain(chain));
		sSQL.append(" AND FFIITMN = FFJITMN AND FFJITMN = ? ORDER BY Ship_Date");

		this.log.debug(sSQL.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sSQL.toString(),
				new Object[] { start, end, item, start, end, item }, new ApprovedDistributionRowMapper(chain));
	}

	public static final class ApprovedDistributionRowMapper implements RowMapper {
		private RpgSrp rpg;
		private String customer;
		private Chain chain;
		
		public ApprovedDistributionRowMapper(Chain chain){
			this.rpg = null;
			this.customer = "";
			this.chain = chain;
		}
		
		public ApprovedDistributionRowMapper(RpgSrp rpg, String customer) {
			this.rpg = rpg;
			this.customer = customer;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			final ApprovedDistributionsItem item = new ApprovedDistributionsItem();
			item.setCartonUPCNumber(rs.getString("carton_upc"));
			item.setCheckDigit(rs.getString("check_digit"));
			item.setDescription(rs.getString("description"));
			item.setInventoryLevel(rs.getString("inventory_level"));
			item.setNumber(rs.getString("item_number"));
			item.setPack(rs.getString("pack"));
			item.setSize(rs.getString("size"));
			item.setRetailUPCNumber(rs.getString("retail_upc"));
			item.setShipDate(rs.getString("ship_date"));
			item.setQuantity(rs.getInt("quantity"));
			String storeNum = rs.getString("customer").trim();
			
			if(StringUtils.isNotBlank(this.customer)){
    			try {
    				final Map<String, BigDecimal> map = this.rpg.getSrpData(this.customer, 
    						Spacing.setCorrectSpacing(item.getNumber(),
    						Spacing.ITEM));
    				item.setRetail(map.get(RpgSrp.SRP_NAME));
    				item.setPrice(map.get(RpgSrp.PRICE_NAME));
    			} catch (Exception e) {
    				item.setRetail(new BigDecimal(0));
    				item.setPrice(new BigDecimal(0));
    			}
			}else{
    			try {
    				final Map<String, BigDecimal> map = this.rpg.getSrpData(
    						Spacing.setCorrectSpacing(storeNum, Spacing.CUSTOMER), 
    						Spacing.setCorrectSpacing(item.getNumber(),
    						Spacing.ITEM));
    				item.setRetail(map.get(RpgSrp.SRP_NAME));
    				item.setPrice(map.get(RpgSrp.PRICE_NAME));
    			} catch (Exception e) {
    				item.setRetail(new BigDecimal(0));
    				item.setPrice(new BigDecimal(0));
    			}		
    			
    			item.setStoreName(this.getStoreNameFromChain(chain, storeNum));
			}

			return item;
		}

		private String getStoreNameFromChain(Chain chain, String storeNumber) {
			for(Store store : chain.getStores()){
				String number = store.getNumber().trim();
				if(number.equalsIgnoreCase(storeNumber.trim())){
					return store.getName().trim();
				}
			}
			
			return "";
		}
	}
}
