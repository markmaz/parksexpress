package com.parksexpress.dao.spring.as400;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.as400.util.DateChanger;
import com.parksexpress.as400.util.Spacing;
import com.parksexpress.domain.item.AllowanceItem;
import com.parksexpress.domain.item.FuturePriceChangeItem;
import com.parksexpress.domain.item.Item;
import com.parksexpress.domain.item.PriceChangeItem;

public class AS400ItemDAO extends AS400ItemDAOAdapter {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private Logger log = Logger.getRootLogger();

	public AS400ItemDAO() {
	}

	public void setDataSource(DataSource ds) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllBrands() {
		final String sql = "SELECT DISTINCT FFIBNCD FROM FFIITMAC0 ORDER BY FFIBNCD";
		this.log.debug(sql);

		final List<Map> list = this.simpleJdbcTemplate.getJdbcOperations().queryForList(sql);
		final List<String> brands = new ArrayList<String>();
		
		for(Map map : list){
			brands.add((String)map.get("FFIBNCD"));
		}
		
		return brands;
	}

	@Override
	public Item getItem(String itemNumber) {
		final String sql = "SELECT FFIDGTC AS CHECK_DIGIT, FFIITMN AS ITEM_NUMBER, FFIPCKI AS PACK, "
				+ " FFISZEI AS SIZE, FFIIDE1 AS DESCRIPTION, FFIUPCN AS UPC, "
				+ " FFIUPCP as Retail_UPC, FFJVNDN AS VENDOR_NUMBER, EEEPRZ1 AS MARKET_COST "
				+ " FROM FFIITMAC0 Item_Master, FFJITMBC1 Item_Balance, EEEITMEP " 
				+ " WHERE Item_Master.FFIITMN = Item_Balance.FFJITMN "
				+ " AND EEEITMN = FFIITMN AND FFJCLSN NOT IN (' 95', '200', '999') " 
				+ " AND FFIITMN = ? ORDER BY FFJPBHN";

		return (Item) this.simpleJdbcTemplate.getJdbcOperations().queryForObject(sql,
				new Object[] { Spacing.setCorrectSpacing(itemNumber, Spacing.ITEM) }, new ItemMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getSuspendedItems(final String orderGuide) {
		final String sql = "SELECT FFIDGTC AS CHECK_DIGIT, FFIITMN AS ITEM_NUMBER, "
				+ " FFIPCKI AS PACK, FFISZEI AS SIZE, FFIIDE1 AS DESCRIPTION, "
				+ " FFIUPCN AS UPC, FFIUPCP as Retail_UPC, FFJVNDN AS VENDOR_NUMBER, " + " EEEPRZ1 AS MARKET_COST "
				+ " FROM FFIITMAC0 Item_Master, FFJITMBC1 Item_Balance, EEEITMEP, BBQORDGJ0 "
				+ " WHERE Item_Master.FFIITMN = Item_Balance.FFJITMN " + " AND BBQITMN = FFJITMN " + " AND BBQORGN = '"
				+ Spacing.setCorrectSpacing(orderGuide, Spacing.ORDER_GUIDE) + "' "
				+ " AND EEEITMN = FFIITMN AND FFJCLSN NOT IN (' 95', '200', '999')" 
				+ " AND Item_Balance.FFJARCD = 'S' ORDER BY FFJPBHN";

		this.log.info(sql.toString());
		return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new ItemMapper());

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getDiscontinuedItems(final String startDate, final String endDate, final String number, final String type,
			final String orderGuide) {
		String sql = "SELECT FFIDGTC AS CHECK_DIGIT, FFIITMN AS ITEM_NUMBER, FFIPCKI AS PACK, "
				+ " FFISZEI AS SIZE, FFIIDE1 AS DESCRIPTION, FFIUPCN AS UPC, "
				+ " FFIUPCP as Retail_UPC, FFJVNDN AS VENDOR_NUMBER, EEEPRZ1 AS MARKET_COST "
				+ " FROM FFIITMAC0 Item_Master, FFJITMBC1 Item_Balance, EEEITMEP, BBQORDGJ0 "
				+ " WHERE Item_Master.FFIITMN = Item_Balance.FFJITMN " + " AND BBQITMN = FFJITMN " + " AND BBQORGN = '"
				+ Spacing.setCorrectSpacing(orderGuide, Spacing.ORDER_GUIDE) + "' " + " AND EEEITMN = FFIITMN "
				+ " AND FFJCLSN NOT IN (' 95', '200', '999') " + " AND Item_Balance.FFJARCD = 'D' " + " AND FFJDTDC >= ? "
				+ " AND FFJDTDC <= ? ";

		if (!StringUtils.isEmpty(type)) {
			if (type.equalsIgnoreCase("item")) {
				sql += " AND FFIITMN = '" + Spacing.setCorrectSpacing(number, Spacing.ITEM) + "'";
			}

			if (type.equalsIgnoreCase("family")) {
				sql += " AND FFJPBHF = '" + Spacing.setCorrectSpacing(number, Spacing.FAMILY) + "'";
			}
		}

		sql += " ORDER BY FFJPBHN";

		this.log.info("Start date: " + startDate);
		this.log.info("End date: " + endDate);
		this.log.info(sql.toString());
		return this.simpleJdbcTemplate.getJdbcOperations().query(sql,
				new Object[] { this.getStartDate(startDate), this.getEndDate(endDate), }, new ItemMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getNewItems(final String startDate, final String endDate, final String number, final String type) {

		String sql = "SELECT FFIDGTC AS CHECK_DIGIT, FFIITMN AS ITEM_NUMBER, FFIPCKI AS PACK, FFISZEI AS SIZE, "
				+ "FFIIDE1 AS DESCRIPTION, FFIUPCN AS UPC, FFIUPCP as Retail_UPC, FFJVNDN AS VENDOR_NUMBER, " 
				+ "EEEPRZ1 AS MARKET_COST "
				+ "FROM FFIITMAC0 Item_Master, FFJITMBC0 Item_Balance, EEEITMEP " 
				+ "WHERE Item_Master.FFIITMN = Item_Balance.FFJITMN "
				+ "AND EEEITMN = FFIITMN " + "AND Item_Balance.FFJCDTE >= ? " + "AND Item_Balance.FFJCDTE <= ? ";

		if (!StringUtils.isEmpty(type)) {
			if (type.equalsIgnoreCase("item")) {
				sql += " AND FFIITMN = '" + Spacing.setCorrectSpacing(number, Spacing.ITEM) + "'";
			}

			if (type.equalsIgnoreCase("family")) {
				sql += " AND FFJPBHF = '" + Spacing.setCorrectSpacing(number, Spacing.FAMILY) + "'";
			}
		}

		sql += "AND FFJCLSN NOT IN (' 95', '200', '999') ORDER BY FFJPBHN";

		this.log.info(sql.toString());
		return this.simpleJdbcTemplate.getJdbcOperations().query(sql,
				new Object[] { this.getStartDate(startDate), this.getEndDate(endDate), }, new ItemMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AllowanceItem> getAllowances(final String startDate, final String endDate, final String number, final String type) {
		final String convertedEndDate = this.getEndDate(endDate);
		final String convertedStartDate = this.getStartDate(startDate);

		final StringBuffer sql = new StringBuffer("SELECT FFIDGTC, BBNITMN AS ITEM_NUMBER, ");
		sql.append("BBNALBC AS ALLOWANCE, ");
		sql.append("BBNDTEF AS START_DATE, BBNDETT AS END_DATE, FFIPCKI AS PACK, ");
		sql.append("FFISZEI AS SIZE, FFIIDE1 AS DESCRIPTION, FFIUPCN AS UPC, FFIUPCP as Retail_UPC, ");
		sql.append("FFJVNDN AS VENDOR_NUMBER, EEEPRZ1 AS MARKET_COST ");
		sql.append("FROM FFIITMAP Item_Master, FFJITMBC1");
		sql.append(" Item_Balance, BBNCODDP  Allowance, EEEITMEL0 ");
		sql.append("WHERE BBNDTEF >= ? ");
		sql.append("AND BBNDETT <= ? ");
		sql.append("AND BBNDETT <> 20011231 ");

		if (!StringUtils.isEmpty(type)) {
			if (type.equalsIgnoreCase("item")) {
				sql.append(" AND FFIITMN = '" + Spacing.setCorrectSpacing(number, Spacing.ITEM) + "'");
			}

			if (type.equalsIgnoreCase("family")) {
				sql.append(" AND FFJPBHF = '" + Spacing.setCorrectSpacing(number, Spacing.FAMILY) + "'");
			}
		}

		sql.append("AND BBNITMN = FFIITMN ");
		sql.append("AND Item_Master.FFIITMN = Item_Balance.FFJITMN ");
		sql.append("AND EEEITMN = FFIITMN ORDER BY BBNDTEF DESC");

		this.log.info(sql.toString());
		return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(), 
				new Object[] { convertedStartDate, convertedEndDate },
				new AllowanceItemMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceChangeItem> getPriceChanges(final String startDate, final String endDate, final String code, final String codeType,
			final String storeNumber, final String orderGuideNumber) {
		final String end = this.getEndDate(endDate);
		final String start = this.getStartDate(startDate);
		final String store = Spacing.setCorrectSpacing(storeNumber, Spacing.CUSTOMER);
		String codeName = "";

		if (codeType.equalsIgnoreCase("Family")) {
			codeName = Spacing.setCorrectSpacing(code, Spacing.FAMILY);
		}

		if (codeType.equalsIgnoreCase("Item")) {
			codeName = Spacing.setCorrectSpacing(code, Spacing.ITEM);
		}

		final String priceChangeFile = "IDSDATA.CCPRCHGL3";
		final StringBuffer sql = new StringBuffer("SELECT PRCCSCD, FFJPBHN as PRICE_BOOK_HEADER, ");
		sql.append("FFJPBHF as PRICE_BOOK_FAMILY, FFIDGTC as CHECK_DIGIT, PRCOPRZ AS OLD_PRICE, ");
		sql.append("PRCNPRZ AS NEW_PRICE, PRCDATE AS EFFECTIVE_DATE, ");
		sql.append("FFIUPCP as Retail_UPC, FFIUPCN as UPC, PRCOSRP AS OLD_SRP, PRCNSRP AS NEW_SRP, PRCNFXP AS FIXED, ");
		sql.append("PRCITMN AS ITEM_NUMBER, FFIIDE1 AS DESCRIPTION, FFIPCKI AS PACK, FFISZEI AS SIZE FROM " + priceChangeFile);
		sql.append(" AS PRICE, IDSDATA.FFIITMAC0 AS ITEM, IDSDATA.FFJITMBP, IDSDATA.BBQORDGL0 WHERE FFIITMN = PRCITMN ");
		sql.append("AND FFIITMN = FFJITMN ");
		sql.append("AND FFJCLSN NOT IN (' 95', '200', '999') ");
		sql.append("AND PRCITMN = FFJITMN ");
		sql.append("AND PRCCUSN = ? ");
		sql.append("AND BBQITMN = FFJITMN ");
		sql.append("AND BBQORGN = ? ");

		if (codeType.equalsIgnoreCase("Family")) {
			sql.append("AND FFJPBHF = ? ");
		}

		if (codeType.equalsIgnoreCase("Item")) {
			sql.append("AND PRCITMN = ? ");
		}

		sql.append("AND PRCDATE >= ? AND PRCDATE <= ? GROUP BY PRCCSCD, FFJPBHN, FFJPBHF, FFIDGTC, PRCOPRZ, PRCNPRZ, PRCDATE, ");
		sql.append("FFIUPCP, FFIUPCN, PRCITMN, FFIIDE1, FFIPCKI, FFISZEI, PRCOSRP, PRCNSRP, PRCNFXP ");
		sql.append("ORDER BY PRCCSCD, FFJPBHN, FFJPBHF, PRCITMN, PRCDATE");

		this.log.info("Order Guide Number:" + orderGuideNumber);
		this.log.info("Store: " + store);
		this.log.info(sql.toString());

		if (StringUtils.isBlank(codeName)) {
			return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(), new Object[] { store, Spacing.setCorrectSpacing(orderGuideNumber, Spacing.ORDER_GUIDE), start, end },
					new PriceChangeItemMapper());
		} else {
			return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(), 
					new Object[] { store, Spacing.setCorrectSpacing(orderGuideNumber, Spacing.ORDER_NUMBER), codeName, start, end },
					new PriceChangeItemMapper());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FuturePriceChangeItem> getFuturePriceChanges(String orderGuideNumber) {
		final StringBuffer sql = new StringBuffer("SELECT DISTINCT PCCPBHN, PCCITMN, PCCCLSN, PCCPBHF, ");
		sql.append("PCCDTEF, PCCPRZ1 as Zone_1, ");
		sql.append("PCCPRZ2 as Zone_2,  PCFPRZ1 as Future_1, PCFPRZ2 as Future_2, ");
		sql.append("(PCFPRZ1 - PCCPRZ1) as Diff1, (PCFPRZ2 - PCCPRZ2) as Diff2, ");
		sql.append("FFIIDE1, FFIPCKI, FFISZEI, FFIUPCN, FFIUPCP, FFIDGTC ");
		sql.append("FROM CCPPCCGP, FFIITMAC0, BBQORDGJ0 ");
		sql.append("WHERE FFIITMN = PCCITMN AND BBQITMN = PCCITMN AND BBQORGN = ? ");
		sql.append("GROUP BY PCCPBHN, PCCITMN, PCCCLSN, PCCPBHF, PCCDTEF, PCCPRZ1, PCCPRZ2, PCFPRZ1, PCFPRZ2, ");
		sql.append("FFIIDE1, FFIPCKI, FFISZEI, FFIUPCN, FFIUPCP, FFIDGTC");

		this.log.info(sql.toString());
		return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(),
				new Object[] { Spacing.setCorrectSpacing(orderGuideNumber, Spacing.ORDER_GUIDE) }, 
				new FuturePriceChangeItemMapper());
	}

	private String getStartDate(final String startDate) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String convertedStart;

		if (StringUtils.isEmpty(startDate)) {
			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -1);
			convertedStart = sdf.format(cal.getTime());
		} else {
			try {
				convertedStart = DateChanger.convertDateToAS400(startDate);
			} catch (ParseException e) {
				final Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, -1);
				convertedStart = sdf.format(cal.getTime());
			}
		}

		this.log.info("Start-Date: " + convertedStart);
		return convertedStart;
	}

	private String getEndDate(final String endDate) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String convertedEnd = "";

		if (StringUtils.isEmpty(endDate)) {
			convertedEnd = sdf.format(Calendar.getInstance().getTime());
		} else {
			try {
				convertedEnd = DateChanger.convertDateToAS400(endDate);
			} catch (ParseException e) {
				convertedEnd = sdf.format(Calendar.getInstance().getTime());
			}
		}

		this.log.info("End-Date: " + convertedEnd);
		return convertedEnd;
	}

	private static final class AllowanceItemMapper implements RowMapper {
		public AllowanceItemMapper() {
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final AllowanceItem item = new AllowanceItem();
			item.setNumber(rs.getString("ITEM_NUMBER").trim());
			item.setCheckDigit(rs.getString("FFIDGTC"));
			item.setPack(rs.getString("PACK"));
			item.setDescription(rs.getString("DESCRIPTION"));
			item.setSize(rs.getString("SIZE"));
			item.setCartonUPCNumber(rs.getString("UPC"));
			try {
				item.setExpirationDate(DateChanger.convertDateFromAS400(rs.getString("END_DATE")));
			} catch (ParseException e) {
				item.setExpirationDate("");
			}

			try {
				item.setStartDate(DateChanger.convertDateFromAS400(rs.getString("START_DATE")));
			} catch (ParseException e) {
				item.setStartDate("");
			}

			item.setCostAllowance(new Float(rs.getFloat("ALLOWANCE")));
			item.setRetailUPCNumber(rs.getString("Retail_UPC"));
			item.setMarketCost(new Float(rs.getFloat("MARKET_COST")));
			return item;
		}
	}

	private static final class ItemMapper implements RowMapper {
		public ItemMapper() {
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final Item item = new Item();
			item.setNumber(rs.getString("ITEM_NUMBER").trim());
			item.setCheckDigit(rs.getString("CHECK_DIGIT"));
			item.setPack(rs.getString("PACK"));
			item.setDescription(rs.getString("DESCRIPTION"));
			item.setSize(rs.getString("SIZE"));
			item.setCartonUPCNumber(rs.getString("UPC"));
			item.setRetailUPCNumber(rs.getString("Retail_UPC"));
			item.setMarketCost(new Float(rs.getFloat("MARKET_COST")));
			return item;
		}
	}

	private static final class PriceChangeItemMapper implements RowMapper {
		public PriceChangeItemMapper() {
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final PriceChangeItem item = new PriceChangeItem();
			item.setNumber(rs.getString("ITEM_NUMBER"));
			item.setCartonUPCNumber(rs.getString("UPC"));
			item.setRetailUPCNumber(rs.getString("Retail_UPC"));
			item.setDescription(rs.getString("DESCRIPTION"));
			item.setCheckDigit(rs.getString("CHECK_DIGIT"));
			item.setEffectiveDate(rs.getString("EFFECTIVE_DATE"));
			item.setOldPrice(rs.getFloat("OLD_PRICE"));
			item.setNewPrice(rs.getFloat("NEW_PRICE"));
			item.setPack(rs.getString("PACK"));
			item.setSize(rs.getString("SIZE"));
			item.setPriceBookFamilyCode(rs.getString("PRICE_BOOK_FAMILY"));
			item.setPriceBookHeaderCode(rs.getString("PRICE_BOOK_HEADER"));
			item.setNewSrp(rs.getFloat("NEW_SRP"));
			item.setOldSrp(rs.getFloat("OLD_SRP"));
			item.setFixedOrPercent(rs.getString("FIXED"));
			return item;
		}
	}

	private static final class FuturePriceChangeItemMapper implements RowMapper {
		public FuturePriceChangeItemMapper() {
		}

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			final FuturePriceChangeItem item = new FuturePriceChangeItem();
			item.setPack(rs.getString("FFIPCKI").trim());
			item.setSize(rs.getString("FFISZEI").trim());
			item.setRetailUPCNumber(rs.getString("FFIUPCP").trim());
			item.setCartonUPCNumber(rs.getString("FFIUPCN").trim());
			item.setNumber(rs.getString("PCCITMN"));
			item.setCheckDigit(rs.getString("FFIDGTC"));
			item.setDescription(rs.getString("FFIIDE1"));
			item.setEffectiveDate(rs.getString("PCCDTEF"));

			final String className = rs.getString("PCCCLSN").trim();

			if (className.equals("10") || className.equals("15") || className.equals("18") || className.equals("19")
					|| className.equals("20")) {
				item.setOldPrice(rs.getFloat("Zone_2"));
				item.setNewPrice(rs.getFloat("Future_2"));
				item.setDifference(rs.getFloat("Diff2"));
			} else {
				item.setOldPrice(rs.getFloat("Zone_1"));
				item.setNewPrice(rs.getFloat("Future_1"));
				item.setDifference(rs.getFloat("Diff1"));
			}

			item.setPriceBookHeaderCode(rs.getString("PCCPBHN"));
			return item;
		}

	}

}
