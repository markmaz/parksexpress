package com.parksexpress.dao.spring.mysql;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.parksexpress.dao.ItemDAO;
import com.parksexpress.dao.PriceBookDAO;
import com.parksexpress.domain.FamilyPricingException;
import com.parksexpress.domain.ItemPricingException;
import com.parksexpress.domain.PriceBook;
import com.parksexpress.domain.PriceBookClass;
import com.parksexpress.domain.PriceBookFamily;
import com.parksexpress.domain.PriceBookHeader;
import com.parksexpress.domain.Pricing;
import com.parksexpress.domain.item.Item;

public class JDBCPriceBookDAO implements PriceBookDAO {
	private ItemDAO itemDAO;

	private Logger log = Logger.getLogger(JDBCPriceBookDAO.class);
	
	private SimpleJdbcTemplate simpleJdbcTemplate;
	
	public JDBCPriceBookDAO(){
	}

	private static final class PriceBookClassMapper implements RowMapper {
		public PriceBookClassMapper(){
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final PriceBookClass pbc = new PriceBookClass();
			pbc.setClassCode(rs.getString("class_code"));
			pbc.setDescription(rs.getString("description"));
			pbc.setPriceBookClassID(rs.getLong("priceBookClassID"));
			return pbc;
		}
	}
	
	private static final class PriceBookFamilyMapper implements RowMapper {
		public PriceBookFamilyMapper(){
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final PriceBookFamily family = new PriceBookFamily();
			family.setDescription(rs.getString("description"));
			family.setFamilyCode(rs.getString("family_code"));
			family.setPriceBookFamilyID(rs.getLong("priceBookFamilyID"));
			return family;
		}
	}
	
	private static final class PriceBookHeaderMapper implements RowMapper {
		public PriceBookHeaderMapper(){
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final PriceBookHeader pbh = new PriceBookHeader();
			pbh.setHeaderCode(rs.getString("header_code"));
			pbh.setDescription(rs.getString("description"));
			pbh.setPriceBookHeaderID(rs.getLong("priceBookHeaderID"));
			return pbh;
		}
	}

	private static final class SRPBookMapper implements RowMapper {
		public SRPBookMapper(){
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final Map<String, String> map = new HashMap<String, String>();
			map.put("srpBook", rs.getString("srpBook"));
			map.put("storeNumber", rs.getString("storeNumber"));
			map.put("bookLevel", rs.getString("bookLevel"));

			return map;
		}

	}

	public boolean addClassPricing(final PriceBookClass priceBookClass, final String srpBook) {
		this.log.debug("Entering addClassPricing");
		this.log.debug("PriceBookClass: " + priceBookClass + " srpBook:" + srpBook);
		final String sql = "INSERT INTO class_family_pricing (pricingLevel, srp, srpCode, levelID, priceBook) "
				+ "VALUES(?, ?, ?, ?, ?)";
		this.log.debug(sql);
		
		String isFixed = "S";
		Object[] object = null;
		final String pricingLevel = "C";

		if (priceBookClass.getPricing().isFixed()) {
			this.log.debug("Pricing is fixed");
			isFixed = "D";
			object = new Object[] { pricingLevel,
					priceBookClass.getPricing().getPrice(), isFixed,
					priceBookClass.getClassCode(), srpBook, };
		} else {
			this.log.debug("Pricing is not fixed");
			object = new Object[] { pricingLevel,
					priceBookClass.getPricing().getPercent(), isFixed,
					priceBookClass.getClassCode(), srpBook, };
		}
		
		this.log.debug("updating the database");
		final int result = this.simpleJdbcTemplate.update(sql, object);

		this.log.debug("Rows updated:" + result);
		if (result == 0) {
			return false;
		}
		
		return true;
	}

	public boolean addFamilyPricing(final PriceBookFamily family, final String srpBook) {
		this.deleteClassFamilyPricing(family.getFamilyCode(), srpBook);
		final String sql = "INSERT INTO class_family_pricing (pricingLevel, srp, srpCode, levelID, priceBook) "
				+ "VALUES(?, ?, ?, ?, ?)";

		String isFixed = "S";
		Object[] object = null;
		final String pricingLevel = "F";

		if (family.getPricing().isFixed()) {
			isFixed = "D";
			object = new Object[] { pricingLevel,
					family.getPricing().getPrice(), isFixed,
					family.getFamilyCode(), srpBook, };
		} else {
			object = new Object[] { pricingLevel,
					family.getPricing().getPercent(), isFixed,
					family.getFamilyCode(), srpBook, };
		}

		final int result = this.simpleJdbcTemplate.update(sql, object);

		if (result == 0) {
			return false;
		}
		
		return true;
	}

	public boolean addItemPricing(final Item item, final String srpBook) {
		this.deletePricingForItem(item, srpBook);

		final String sql = "INSERT INTO item_pricing (priceBook, srpPercentage, srpFixedPrice, isFixed, itemNumber) "
				+ "VALUES(?, ?, ?, ?, ?)";

		String isFixed = "N";

		if (item.getPricing().isFixed()) {
			isFixed = "Y";
		}

		final int result = this.simpleJdbcTemplate.update(sql, new Object[] { srpBook,
				item.getPricing().getPercent(), item.getPricing().getPrice(),
				isFixed, item.getNumber(), });

		if (result == 0) {
			return false;
		} 
		
		return true;
	}

	public boolean deleteClassFamilyPricing(final String family, final String srpBook) {
		String sql = "DELETE FROM class_family_pricing WHERE levelID = ? AND priceBook = ?";
		int result = this.simpleJdbcTemplate.update(sql, new Object[] { family,
				srpBook, });

		sql = "DELETE FROM future_family_pricing WHERE levelID = ? AND priceBook = ?";
		result = this.simpleJdbcTemplate.update(sql, new Object[] { family,
				srpBook, });
		
		if (result == 0) {
			return false;
		}
		
		return true;
	} 

	public boolean deletePricingForItem(final Item item, final String priceBook) {
		final String itemLevelBook = this.getItemLevelSRPBookName(priceBook);

		// Delete existing pricing
		final String sql = "DELETE FROM item_pricing WHERE itemNumber = ? AND priceBook = ?";
		final int i = this.simpleJdbcTemplate.update(sql, new Object[] { item.getNumber(),
				itemLevelBook, });

		if (i > 0) {
			return true;
		}

		return false;
	}

	public PriceBookClass getClass(final String criteria) {
		final String sql = "SELECT class_code, description, priceBookClassID FROM price_book_class WHERE class_code = ?";
		this.log.debug(sql.toString());

		return (PriceBookClass) this.simpleJdbcTemplate
				.getJdbcOperations()
				.queryForObject(sql.toString(), new Object[] { criteria },
						new int[] { Types.VARCHAR }, new PriceBookClassMapper());
	}

	public PriceBookFamily getFamily(final String criteria) {
		final String sql = "SELECT family_code, description, priceBookFamilyID FROM price_book_family WHERE family_code = ?";
		this.log.debug(sql.toString());

		final PriceBookFamily family = (PriceBookFamily) this.simpleJdbcTemplate
				.getJdbcOperations().queryForObject(sql.toString(),
						new Object[] { criteria }, new int[] { Types.VARCHAR },
						new PriceBookFamilyMapper());

		family.setItems(this.itemDAO.getItemsByFamily(family.getFamilyCode()));
		return family;
	}

	public List<FamilyPricingException> getFamilyExceptionPricing(
			final PriceBookFamily family, final String chainCode, final int priority) {
			String sql = "SELECT DISTINCT cfp.*, ssm.*, bm.bookLevel, pbf.description "
				+ "FROM srp_store_map bm, chain c, class_family_pricing cfp, stores s, "
				+ "srp_store_book_map ssm, price_book_family pbf "
				+ "WHERE c.chainCode = ? "
				+ "AND s.chainCode = c.chainCode "
				+ "AND bm.storeNumber = s.number "
				+ "AND bm.srpBook = cfp.priceBook "
				+ "AND ssm.srpBook = bm.srpBook "
				+ "AND cfp.levelID = ? "
				+ "AND pbf.family_code = cfp.levelID "
				+ "AND pricingLevel = 'F' ";

		if (priority == PriceBook.CHAIN_FAMILY_PRIORITY) {
			sql += "AND bm.bookLevel < " + PriceBook.CHAIN_FAMILY_NAME;
		} 
		
//		This should keep the exceptions to their zone.
//		else {
//			sql += "AND bm.bookLevel < " + priority;
//		}
 
		final SqlRowSet rowSet = this.simpleJdbcTemplate.getJdbcOperations()
				.queryForRowSet(sql,
						new Object[] { chainCode, family.getFamilyCode() });

		final List<FamilyPricingException> list = new ArrayList<FamilyPricingException>();
		while (rowSet.next()) {
			final PriceBook book = new PriceBook(rowSet.getString("priceBook"),
					rowSet.getString("storeDescription"), rowSet
							.getInt("bookLevel"), rowSet
							.getString("storeNumber"));
			final PriceBookFamily pbFamily = new PriceBookFamily();
			pbFamily.setFamilyCode(rowSet.getString("levelID"));
			pbFamily.setDescription(rowSet.getString("description"));

			final Pricing pricing = new Pricing();
			pricing.setCost(new BigDecimal(0));

			if (rowSet.getString("srpCode").equalsIgnoreCase("D")) {
				pricing.setFixed(true);
				pricing.setPrice(rowSet.getBigDecimal("srp"));
				pricing.setPercent(new BigDecimal(0));
			} else {
				pricing.setFixed(false);
				pricing.setPercent(rowSet.getBigDecimal("srp"));
				pricing.setPrice(new BigDecimal(0));
			}

			pricing.setPricingLevel(rowSet.getString("pricingLevel"));
			pbFamily.setPricing(pricing);
			list.add(new FamilyPricingException(pbFamily, book));
		}

		return list;
	}

	public PriceBookHeader getHeader(final String criteria) {
		final String sql = "SELECT header_code, description, priceBookHeaderID FROM price_book_header WHERE header_code = ?";
		this.log.debug(sql.toString());
		PriceBookHeader header = new PriceBookHeader();
		
		try{
			header =  (PriceBookHeader) this.simpleJdbcTemplate.getJdbcOperations()
					.queryForObject(sql.toString(), new Object[] { criteria.trim() },
							new int[] { Types.VARCHAR },
							new PriceBookHeaderMapper());
		}catch(EmptyResultDataAccessException e){
			header = new PriceBookHeader();
		}
		
		return header;
	}

	public List<ItemPricingException> getItemLevelPriceBookExceptionsForFamilyForAllZones(
			final String chainCode, final String familyCode) {
		final String sql = "SELECT DISTINCT ip.*, ssm.*, bm.bookLevel, i.description, i.itemNumber, i.check_digit "
				+ "FROM srp_store_map bm, chain c, item_pricing ip, "
				+ "stores s, srp_store_book_map ssm, items i "
				+ "WHERE c.chainCode = ? "
				+ "AND s.chainCode = c.chainCode "
				+ "AND bm.storeNumber = s.number "
				+ "AND bm.srpBook = ip.priceBook "
				+ "AND ssm.srpBook = bm.srpBook "
				+ "AND i.itemNumber = ip.itemNumber "
				+ "AND i.status = 'A' "
				+ "AND i.priceBookFamily = ? " + "AND bookLevel < 100 order by storeDescription, description";

		final SqlRowSet rowSet = this.simpleJdbcTemplate.getJdbcOperations()
				.queryForRowSet(sql, new Object[] { chainCode, familyCode });

		final List<ItemPricingException> list = new ArrayList<ItemPricingException>();

		while (rowSet.next()) {
			final ItemPricingException ipe = new ItemPricingException();
			final PriceBook book = new PriceBook();
			book.setDescription(rowSet.getString("storeDescription"));
			book.setNumber(rowSet.getString("srpBook"));
			book.setPriority(rowSet.getInt("bookLevel"));
			ipe.setBook(book);

			final Item item = new Item();
			item.setNumber(rowSet.getString("itemNumber"));
			item.setDescription(rowSet.getString("description"));
			item.setCheckDigit(rowSet.getString("check_digit"));
			boolean isFixed = false;

			if (rowSet.getString("isFixed").equalsIgnoreCase("Y")) {
				isFixed = true;
			}

			item.setPricing(new Pricing(rowSet.getBigDecimal("srpFixedPrice"),
					rowSet.getBigDecimal("srpPercentage"), isFixed, null, null,
					null));
			ipe.setItem(item);

			list.add(ipe);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public String getItemLevelSRPBookName(final String familyLevelBook) {
		String sql = "SELECT * from srp_store_map WHERE srpBook = ?";
		final List<Map> list = this.simpleJdbcTemplate.getJdbcOperations().query(
				sql.toString(), new Object[] { familyLevelBook },
				new int[] { Types.VARCHAR }, new SRPBookMapper());

		final Map map = list.get(0);
		
		if(((String)map.get("bookLevel")).equalsIgnoreCase(PriceBook.CHAIN_ITEM_NAME) 
				|| ((String)map.get("bookLevel")).equalsIgnoreCase(PriceBook.ZONE_ITEM_NAME)){
			return familyLevelBook;
		}
		
		final int levelModifier = 10;
		final int priority = new Integer((String) map.get("bookLevel")).intValue() - levelModifier;
		sql = "SELECT srpBook FROM srp_store_map WHERE bookLevel = ? AND storeNumber = ?";

		return this.simpleJdbcTemplate.queryForObject(sql, String.class,
				new Object[] { new Integer(priority), map.get("storeNumber") });
	}

	@SuppressWarnings("unchecked")
	public String getChainItemLevelSRPBookName(final String familyLevelBook) {
		String sql = "SELECT * from srp_store_map WHERE srpBook = ?";
		final List<Map> list = this.simpleJdbcTemplate.getJdbcOperations().query(
				sql.toString(), new Object[] { familyLevelBook },
				new int[] { Types.VARCHAR }, new SRPBookMapper());

		final Map map = list.get(0);
		
		if(((String)map.get("bookLevel")).equalsIgnoreCase(PriceBook.CHAIN_ITEM_NAME) 
				|| ((String)map.get("bookLevel")).equalsIgnoreCase(PriceBook.ZONE_ITEM_NAME)){
			return familyLevelBook;
		}
		
		final int priority = 90;
		sql = "SELECT srpBook FROM srp_store_map WHERE bookLevel = ? AND storeNumber = ?";

		return this.simpleJdbcTemplate.queryForObject(sql, String.class,
				new Object[] { new Integer(priority), map.get("storeNumber") });
	}
	
	public PriceBook getPriceBookByZone(final String zone) {
		final String sql = "SELECT distinct sm.srpBook, sm.bookLevel, ssm.storeDescription, ssm.storeNumber  "
				+ "FROM srp_store_map sm, srp_store_book_map ssm "
				+ "WHERE sm.srpBook = ? "
				+ "AND sm.srpBook = ssm.srpBook order by sm.srpBook";

		final SqlRowSet rowSet = this.simpleJdbcTemplate.getJdbcOperations()
				.queryForRowSet(sql, new Object[] { zone });
		final PriceBook book = new PriceBook();

		while (rowSet.next()) {
			book.setDescription(rowSet.getString("storeDescription"));
			book.setNumber(rowSet.getString("srpBook"));
			book.setCustomerNumber(rowSet.getString("storeNumber"));
			book.setPriority(rowSet.getInt("bookLevel"));
		}

		return book;
	}

	public List<PriceBook> getPriceBooksByPriority(final int priority,
			final List<PriceBook> parentBooks) {
		final String sql = "SELECT DISTINCT sm.srpBook, sm.bookLevel, sm.storeNumber, ssm.storeDescription, " +
				"ssm.storeNumber as customerNumber  from srp_store_map sm, stores s, srp_store_book_map ssm "
				+ "WHERE sm.bookLevel = ? " + "AND sm.srpBook = ssm.srpBook ";

		final List<PriceBook> bookList = new ArrayList<PriceBook>();

		for (PriceBook parent : parentBooks) {
			final StringBuffer buffSQL = new StringBuffer(sql);
			buffSQL.append("AND sm.storeNumber in ("
					+ this.makeSqlList(parent.getStores()) + ")");
			final SqlRowSet rowSet = this.simpleJdbcTemplate.getJdbcOperations()
					.queryForRowSet(buffSQL.toString(),
							new Object[] { new Integer(priority) });
			bookList.addAll(this.getPriceBooks(priority, rowSet, parent));
		}

		return bookList;
	}

	public List<PriceBook> getPriceBooksByPriority(final int priority,
			final String chainCode) {
		final String sql = "SELECT distinct sm.srpBook, sm.bookLevel, sm.storeNumber, ssm.storeDescription, " +
				"ssm.storeNumber as customerNumber  "
				+ "FROM srp_store_map sm, stores s, srp_store_book_map ssm "
				+ "WHERE s.chainCode = ? "
				+ "AND sm.bookLevel = ? "
				+ "AND s.number = sm.storeNumber "
				+ "AND sm.srpBook = ssm.srpBook order by sm.srpBook";

		final SqlRowSet rowSet = this.simpleJdbcTemplate.getJdbcOperations()
				.queryForRowSet(sql,
						new Object[] { chainCode, new Integer(priority) });

		return this.getPriceBooks(priority, rowSet);
	}

	public List<Pricing> getPricingForFamily(final String chainCode,
			final String familyCode, final int priority) {
		final String sql = "SELECT DISTINCT cfp.*, ssm.* "
				+ "FROM srp_store_map bm, chain c, class_family_pricing cfp, stores s, srp_store_book_map ssm "
				+ "WHERE c.chainCode = ? " + "AND s.chainCode = c.chainCode "
				+ "AND bm.storeNumber = s.number "
				+ "AND bm.srpBook = cfp.priceBook "
				+ "AND ssm.srpBook = bm.srpBook " + "AND cfp.levelID = ? "
				+ "AND bm.bookLevel = ? ";

		final SqlRowSet rowSet = this.simpleJdbcTemplate.getJdbcOperations()
				.queryForRowSet(sql,
						new Object[] { chainCode, familyCode, priority });

		final List<Pricing> list = new ArrayList<Pricing>();

		while (rowSet.next()) {
			final Pricing pricing = new Pricing();
			pricing.setCost(new BigDecimal(0));

			if (rowSet.getString("srpCode").equalsIgnoreCase("D")) {
				pricing.setFixed(true);
				pricing.setPrice(rowSet.getBigDecimal("srp"));
				pricing.setPercent(new BigDecimal(0));
			} else {
				pricing.setFixed(false);
				pricing.setPercent(rowSet.getBigDecimal("srp"));
				pricing.setPrice(new BigDecimal(0));
			}

			pricing.setPricingLevel(rowSet.getString("pricingLevel"));
			list.add(pricing);
		}

		return list;
	}

	public List<Pricing> getPricingForFamily(final String chainCode, final String book,
			final String familyCode) {
		final String sql = "SELECT DISTINCT cfp.*, ssm.* "
				+ "FROM srp_store_map bm, chain c, class_family_pricing cfp, stores s, srp_store_book_map ssm "
				+ "WHERE c.chainCode = ? " + "AND s.chainCode = c.chainCode "
				+ "AND bm.storeNumber = s.number " + "AND bm.srpBook = ? "
				+ "AND bm.srpBook = cfp.priceBook "
				+ "AND ssm.srpBook = bm.srpBook " + "AND levelID = ? ";

		final SqlRowSet rowSet = this.simpleJdbcTemplate.getJdbcOperations()
				.queryForRowSet(sql,
						new Object[] { chainCode, book, familyCode });

		final List<Pricing> list = new ArrayList<Pricing>();

		while (rowSet.next()) {
			final Pricing pricing = new Pricing();
			pricing.setCost(new BigDecimal(0));

			if (rowSet.getString("srpCode").equalsIgnoreCase("D")) {
				pricing.setFixed(true);
				pricing.setPrice(rowSet.getBigDecimal("srp"));
				pricing.setPercent(new BigDecimal(0));
			} else {
				pricing.setFixed(false);
				pricing.setPercent(rowSet.getBigDecimal("srp"));
				pricing.setPrice(new BigDecimal(0));
			}

			pricing.setPricingLevel(rowSet.getString("pricingLevel"));
			list.add(pricing);
		}

		return list;
	}

	public Pricing getPricingForItem(final String itemNumber, final String priceBook) {
		final String itemLevelPriceBook = this.getItemLevelSRPBookName(priceBook);
		final String sql = "SELECT priceBook, srpPercentage, srpFixedPrice, isFixed, itemNumber "
				+ "FROM item_pricing WHERE itemNumber = ? "
				+ "AND priceBook = ? ";

		SqlRowSet rowSet = this.simpleJdbcTemplate.getJdbcOperations()
				.queryForRowSet(sql,
						new Object[] { itemNumber, itemLevelPriceBook });
		
		Pricing pricing = null;

		if(!rowSet.next()){
			final String chainItemLevelPriceBook = this.getChainItemLevelSRPBookName(priceBook);
			rowSet = this.simpleJdbcTemplate.getJdbcOperations()
			.queryForRowSet(sql,
					new Object[] { itemNumber, chainItemLevelPriceBook });
		}else{
			rowSet.beforeFirst();
		}
		
		while (rowSet.next()) {
			pricing = new Pricing();
			final String fixedOrPercent = rowSet.getString("isFixed");

			if (fixedOrPercent.equalsIgnoreCase("Y")) {
				pricing.setFixed(true);
				pricing.setPrice(new BigDecimal(rowSet
						.getString("srpFixedPrice")));
				pricing.setPercent(new BigDecimal("0.00"));
			} else {
				pricing.setFixed(false);
				pricing.setPercent(new BigDecimal(rowSet
						.getString("srpPercentage")));
				pricing.setPrice(new BigDecimal("0.00"));
			}

		}

		return pricing;
	}

	public PriceBookHeader getSkeleton(final String code, final int type) {
		switch (type) {
    		case HEADER:
    			return this.getPriceBookHeaderByHeader(code);
    		case CLASS:
    			return this.getPriceBookHeaderByClass(code);
    		case FAMILY:
    			return this.getPriceBookHeaderByFamily(code);
    		case ITEM:
    			return this.getPriceBookHeaderByItem(code);
    		default:
    			return null;
		}
	}

	public List<PriceBook> getZones(final int priority, final String chainCode) {
		final String sql = "SELECT distinct sm.srpBook, sm.bookLevel, ssm.storeDescription, ssm.storeNumber  "
				+ "FROM srp_store_map sm, stores s, srp_store_book_map ssm "
				+ "WHERE s.chainCode = ? "
				+ "AND sm.bookLevel = ? "
				+ "AND s.number = sm.storeNumber "
				+ "AND sm.srpBook = ssm.srpBook order by sm.srpBook";

		final SqlRowSet rowSet = this.simpleJdbcTemplate.getJdbcOperations()
				.queryForRowSet(sql,
						new Object[] { chainCode, new Integer(priority) });
		final List<PriceBook> books = new ArrayList<PriceBook>();

		while (rowSet.next()) {
			final PriceBook book = new PriceBook();
			book.setDescription(rowSet.getString("storeDescription"));
			book.setNumber(rowSet.getString("srpBook"));
			book.setPriority(priority);
			book.setCustomerNumber(rowSet.getString("storeNumber"));
			books.add(book);
		}

		return books;
	}

	@SuppressWarnings("unchecked")
	public List<PriceBookClass> searchClasses(final String criteria) {
		final String sql = "SELECT class_code, description, priceBookClassID FROM price_book_class WHERE " +
				"MATCH(class_code, description) AGAINST(? IN BOOLEAN MODE)";
		this.log.debug(sql.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(),
				new Object[] { criteria + "*" }, new int[] { Types.VARCHAR },
				new PriceBookClassMapper());
	}

	@SuppressWarnings("unchecked")
	public List<PriceBookFamily> searchFamilies(final String criteria) {
		final String sql = "SELECT family_code, description, priceBookFamilyID FROM price_book_family WHERE " +
				"family_code like '" + criteria + "%' OR description like '" + criteria + "%'";
		this.log.debug(sql.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(), new PriceBookFamilyMapper());
	}

	@SuppressWarnings("unchecked")
	public List<PriceBookHeader> searchHeaders(final String criteria) {
		final String sql = "SELECT header_code, description, priceBookHeaderID FROM price_book_header " +
				"WHERE MATCH(header_code, description) AGAINST(? IN BOOLEAN MODE)";
		this.log.debug(sql.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(),
				new Object[] { criteria + "*" }, new int[] { Types.VARCHAR },
				new PriceBookHeaderMapper());
	}

	public void setDataSource(final DataSource ds) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
	}

	public void setItemDAO(final ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	public boolean updateFutureFamilyPricing(final PriceBookFamily family,
			final String srpBook) {
		final String sql = "INSERT INTO future_family_pricing (pricingLevel, srp, srpCode, levelID, priceBook, effectiveDate) "
				+ "VALUES(?, ?, ?, ?, ?, ?)";

		String isFixed = "S";
		Object[] object = null;
		final String pricingLevel = "C";

		if (family.getPricing().isFixed()) {
			isFixed = "D";
			object = new Object[] { pricingLevel,
					family.getPricing().getPrice(), isFixed,
					family.getFamilyCode(), srpBook,
					family.getPricing().getEffectiveDate(), };
		} else {
			object = new Object[] { pricingLevel,
					family.getPricing().getPercent(), isFixed,
					family.getFamilyCode(), srpBook,
					family.getPricing().getEffectiveDate(), };
		}

		final int result = this.simpleJdbcTemplate.update(sql, object);
		
		if(result > 0){
			return true;
		}
		
		return false;
	}

	public boolean updateFuturePricingForItem(final Item item, final String priceBook,
			final String effectiveDate, float srp, String isFixed) {
		
		final String sql = "INSERT INTO future_item_pricing VALUES(null, ?, ?, ?, ?, ?)";
		final int result = this.simpleJdbcTemplate.update(sql, new Object[] {
				effectiveDate, item.getNumber(), priceBook, srp, isFixed});

		if (result == 0) {
			return false;
		}
		
		return true;
	}

	public boolean updatePricingForItem(final Item item, final String priceBook) {
		final String itemLevelBook = this.getItemLevelSRPBookName(priceBook);

		// Delete existing pricing
		String sql = "DELETE FROM item_pricing WHERE itemNumber = ? AND priceBook = ?";
		this.simpleJdbcTemplate.update(sql, new Object[] { item.getNumber(),
				itemLevelBook, });

		String yesNo = "Y";

		if (!item.getPricing().isFixed()) {
			yesNo = "N";
		}

		sql = "INSERT INTO item_pricing (itemNumber, priceBook, srpPercentage, srpFixedPrice, isFixed) VALUES(?, ?, ?, ?, ?)";
		final int i = this.simpleJdbcTemplate.update(sql, new Object[] { item.getNumber(),
				itemLevelBook, item.getPricing().getPercent(),
				item.getPricing().getPrice(), yesNo, });

		if (i > 0) {
			return true;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	private List<PriceBookClass> getClassesByHeader(final String headerCode) {
		final String sql = "select distinct pbc.class_code, pbc.description, pbc.priceBookClassID "
				+ "from price_book_class pbc, srp_heirarchy s "
				+ "WHERE s.priceBookHeader= ? "
				+ "AND pbc.class_code = s.itemClass order by pbc.description";

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(),
				new Object[] { headerCode }, new int[] { Types.VARCHAR },
				new PriceBookClassMapper());
	}

	@SuppressWarnings("unchecked")
	private List<PriceBookFamily> getFamiliesByClass(final String classCode) {
		final String sql = "select distinct pbf.family_code, pbf.description, pbf.priceBookFamilyID "
				+ "from price_book_family pbf, srp_heirarchy s "
				+ "WHERE s.itemClass= ? "
				+ "AND pbf.family_code = s.priceBookFamily order by pbf.description";

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(),
				new Object[] { classCode }, new int[] { Types.VARCHAR },
				new PriceBookFamilyMapper());
	}

	private PriceBookHeader getPriceBookHeaderByClass(final String classCode) {
		final String sql = "SELECT distinct priceBookHeader FROM srp_heirarchy WHERE itemClass = ?";
		final String headerCode = this.simpleJdbcTemplate.queryForObject(sql,
				String.class, classCode);

		return this.getPriceBookHeaderByHeader(headerCode);
	}

	private PriceBookHeader getPriceBookHeaderByFamily(final String familyCode) {
		final String sql = "SELECT distinct priceBookHeader FROM srp_heirarchy WHERE priceBookFamily = ?";
		String headerCode = "";
		
		try{
		headerCode = this.simpleJdbcTemplate.queryForObject(sql,
				String.class, familyCode);
		}catch(EmptyResultDataAccessException e){
			headerCode = "";
		}
		return this.getPriceBookHeaderByHeader(headerCode);
	}

	private PriceBookHeader getPriceBookHeaderByHeader(final String headerCode) {
		final PriceBookHeader pbh = this.getHeader(headerCode);
		
		if(StringUtils.isNotEmpty(pbh.getHeaderCode())){
			pbh.setPriceBookClasses(this.getClassesByHeader(pbh.getHeaderCode()));
	
			for (int i = 0; i < pbh.getPriceBookClasses().size(); i++) {
				final String classCode = pbh.getPriceBookClasses().get(i).getClassCode();
				pbh.getPriceBookClasses().get(i).setPriceBookFamilies(
						this.getFamiliesByClass(classCode));
			}
		}
		return pbh;
	}

	private PriceBookHeader getPriceBookHeaderByItem(final String itemCode) {
		return this.getPriceBookHeaderByHeader(this.itemDAO
				.getHeaderCodeForItem(itemCode));
	}

	private List<PriceBook> getPriceBooks(final int priority, final SqlRowSet rowSet) {
		PriceBook book = new PriceBook();
		boolean isFirstTime = true;
		final List<PriceBook> books = new ArrayList<PriceBook>();

		while (rowSet.next()) {
			final String bookName = rowSet.getString("srpBook");

			if (bookName.equalsIgnoreCase(book.getNumber())) {
				book.getStores().add(rowSet.getString("storeNumber"));
			} else {
				if (!isFirstTime) {
					books.add(book);
				} else {
					book = new PriceBook();
					book.setDescription(rowSet.getString("storeDescription"));
					book.setNumber(rowSet.getString("srpBook"));
					book.setCustomerNumber(rowSet.getString("customerNumber"));
					book.setPriority(priority);
					book.getStores().add(rowSet.getString("storeNumber"));
					isFirstTime = false;
				}
			}
		}

		books.add(book);
		return books;
	}

	private List<PriceBook> getPriceBooks(final int priority, final SqlRowSet rowSet,
			final PriceBook parent) {
		PriceBook book = new PriceBook();
		final boolean isFirstTime = true;
		final List<PriceBook> books = new ArrayList<PriceBook>();

		while (rowSet.next()) {
			final String bookName = rowSet.getString("srpBook");

			if (bookName.equalsIgnoreCase(book.getNumber())) {
				book.getStores().add(rowSet.getString("storeNumber"));
			} else {
				if (!isFirstTime) {
					books.add(book);
				}

				book = new PriceBook();
				book.setDescription(rowSet.getString("storeDescription"));
				book.setNumber(rowSet.getString("srpBook"));
				book.setCustomerNumber(rowSet.getString("customerNumber"));
				book.setPriority(priority);
				book.getStores().add(rowSet.getString("storeNumber"));
				book.setParent(parent);
			}
		}

		books.add(book);
		return books;
	}

	private String makeSqlList(final List<String> list) {
		// Clean up list
		final StringBuffer listing = new StringBuffer(list.toString());
		listing.deleteCharAt(0);
		listing.deleteCharAt(listing.length() - 1);

		final StringTokenizer tokenizer = new StringTokenizer(listing.toString(), ",");
		final StringBuffer buff = new StringBuffer();

		while (tokenizer.hasMoreTokens()) {
			buff.append("'" + tokenizer.nextToken() + "',");
		}

		buff.deleteCharAt(buff.length() - 1);
		return buff.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceBookClass> getAllPriceBookClasses() {
		final String sql = "SELECT description, class_code, priceBookClassID FROM price_book_class " 
			 + "WHERE class_code NOT IN('200', '999', '900', '990') ORDER BY class_code";
		this.log.debug(sql);
		return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new PriceBookClassMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceBookFamily> getAllPriceBookFamilies() {
		final String sql = "SELECT description, family_code, priceBookFamilyID FROM price_book_family ORDER BY family_code";
		this.log.debug(sql);
		return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new PriceBookFamilyMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceBookHeader> getAllPriceBookHeaders() {
		final String sql = "SELECT description, header_code, priceBookHeaderID FROM price_book_header " + 
						   "WHERE header_code NOT IN ('200') ORDER BY header_code";
		this.log.debug(sql);
		return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new PriceBookHeaderMapper());
	}
}