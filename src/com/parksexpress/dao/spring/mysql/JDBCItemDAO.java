package com.parksexpress.dao.spring.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.domain.item.Item;

public class JDBCItemDAO extends JDBCItemDAOAdapter {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private Logger log = Logger.getRootLogger();

	public JDBCItemDAO(){
	}

	public void setDataSource(final DataSource ds) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
	}

	public Item getItem(final String itemNumber) {
		this.log.debug(itemNumber);
		final StringBuffer sql = new StringBuffer("SELECT DISTINCT itemID, i.itemNumber, pack, size, description, " +
				"check_digit, retailUPCNumber, cartonUPCNumber, vendorNumber, priceBookHeader, " +
				"priceBookFamily, itemClass, zone1 as marketCost, status FROM items i, item_cost ic");
		sql.append(" WHERE i.itemNumber = ? and i.itemNumber = ic.itemNumber order by description");
		this.log.warn(sql.toString());

		return (Item) this.simpleJdbcTemplate.getJdbcOperations().queryForObject(
				sql.toString(), new Object[] { itemNumber },
				new int[] { Types.VARCHAR }, new ItemMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Item> getItemsByClass(final String itemClass) {
		final StringBuffer sql = new StringBuffer("SELECT itemID, i.itemNumber, pack, size, description, " +
				"check_digit, retailUPCNumber, cartonUPCNumber, vendorNumber, priceBookHeader, priceBookFamily, " +
				"itemClass, status, zone1 as marketCost FROM items i, item_cost ic ");
		sql.append(" WHERE itemClass = ?  and status ='A' and i.itemNumber = ic.itemNumber order by description");
		this.log.warn(sql.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(),
				new Object[] { itemClass }, new int[] { Types.VARCHAR },
				new ItemMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Item> getItemsByFamily(final String family) {
		final StringBuffer sql = new StringBuffer("SELECT itemID, i.itemNumber, pack, size, description, check_digit, " +
				"retailUPCNumber, cartonUPCNumber, vendorNumber, priceBookHeader, priceBookFamily, itemClass, " +
				"status, zone1 as marketCost FROM items i, item_cost ic ");
		sql.append(" WHERE priceBookFamily = ? and status ='A' and i.itemNumber = ic.itemNumber order by description");
		this.log.warn(sql.toString());

		List list = this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(),
				new Object[] { family }, new int[] { Types.VARCHAR },
				new ItemMapper());
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> getDiscontinuedAndSuspendedItemsByFamily(final String family){
		final StringBuffer sql = new StringBuffer("SELECT itemID, i.itemNumber, pack, size, description, check_digit, " +
				"retailUPCNumber, cartonUPCNumber, vendorNumber, priceBookHeader, priceBookFamily, itemClass, " +
				"status, zone1 as marketCost FROM items i, item_cost ic ");
		sql.append(" WHERE priceBookFamily = ? and status in ('D', 'S') and i.itemNumber = ic.itemNumber order by description");
		this.log.warn(sql.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(),
				new Object[] { family }, new int[] { Types.VARCHAR },
				new ItemMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Item> getItemsByHeader(final String header) {
		final StringBuffer sql = new StringBuffer("SELECT itemID, i.itemNumber, pack, size, description, " +
				"check_digit, retailUPCNumber, cartonUPCNumber, vendorNumber, priceBookHeader, priceBookFamily, " +
				"itemClass, status, zone1 as marketCost FROM items i, item_cost ic ");
		sql.append(" WHERE priceBookHeader = ? and status ='A' and i.itemNumber = ic.itemNumber order by description");
		this.log.warn(sql.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(),
				new Object[] { header }, new int[] { Types.VARCHAR },
				new ItemMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Item> search(final String criteria) {
		final StringBuffer sql = new StringBuffer("SELECT itemID, i.itemNumber, pack, size, description, " +
				"check_digit, retailUPCNumber, cartonUPCNumber, vendorNumber, priceBookHeader, priceBookFamily, " +
				"itemClass, status, zone1 as marketCost FROM items i, item_cost ic ");
		sql.append(" WHERE MATCH(description, i.itemNumber, retailUPCNumber, cartonUPCNumber) AGAINST(? IN BOOLEAN MODE) " +
				"and i.itemNumber = ic.itemNumber AND status = 'A' order by description");
		this.log.warn(sql.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(),
				new Object[] { criteria + "*" }, new int[] { Types.VARCHAR },
				new ItemMapper());
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> find(final String criteria) {
		final StringBuffer sql = new StringBuffer("SELECT itemID, i.itemNumber, pack, size, description, " +
				"check_digit, retailUPCNumber, cartonUPCNumber, vendorNumber, priceBookHeader, priceBookFamily, " +
				"itemClass, status, zone1 as marketCost FROM items i, item_cost ic ");
		sql.append(" WHERE (i.itemNumber like '%" + criteria +"%' OR description like '" + criteria +"%' " +
				   "OR retailUPCNumber like '" + criteria + "%' OR cartonUPCNumber like '" + criteria + "%' " +
				   "OR priceBookFamily like '" + criteria + "%') " +
				   "and i.itemNumber = ic.itemNumber AND status = 'A' order by description");
		this.log.warn(sql.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(),
				new ItemMapper());
	}

	public String getHeaderCodeForItem(final String itemCode) {
		return this.simpleJdbcTemplate.queryForObject(
				"SELECT priceBookHeader FROM items WHERE itemNumber = ?",
				String.class, itemCode);
	}

	private static final class ItemMapper implements RowMapper {
		public ItemMapper(){
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final Item item = new Item();
			item.setCartonUPCNumber(rs.getString("cartonUPCNumber"));
			item.setRetailUPCNumber(rs.getString("retailUPCNumber"));
			item.setCheckDigit(rs.getString("check_digit"));
			item.setDescription(rs.getString("description"));
			item.setItemID(rs.getLong("itemID"));
			item.setNumber(rs.getString("itemNumber"));
			item.setPack(rs.getString("pack"));
			item.setSize(rs.getString("size"));
			item.setVendorNumber(rs.getString("vendorNumber"));
			item.setPriceBookClassCode(rs.getString("itemClass"));
			item.setPriceBookFamilyCode(rs.getString("priceBookFamily"));
			item.setPriceBookHeaderCode(rs.getString("priceBookHeader"));
			item.setStatus(rs.getString("status"));
			item.setMarketCost(rs.getFloat("marketCost"));

			return item;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Item> getItemsInOrderGuide(final String chainCode, final String familyCode) {
		final StringBuffer sql = new StringBuffer("SELECT itemID, i.itemNumber, pack, size, description, " +
				"check_digit, retailUPCNumber, cartonUPCNumber, vendorNumber, priceBookHeader, priceBookFamily, " +
				"itemClass, status, zone1 as marketCost FROM items i, order_guides g, chainorderguidemap om, " +
				"item_cost ic WHERE i.itemNumber = g.itemNumber and i.priceBookFamily = ? " +
				"and om.chainCode = ? and number = om.orderGuide and i.itemNumber = ic.itemNumber order by description");
		this.log.warn(sql.toString());

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql.toString(),
				new Object[] {familyCode, chainCode }, new int[] { Types.VARCHAR, Types.VARCHAR },
				new ItemMapper());
	}

	public int orderGuideItemAdd(final String chainCode, final Item item) {
		//Get the order guide name
		final String sql = "SELECT orderGuide FROM chainorderguidemap where chainCode = ?";
		final String orderGuide = this.simpleJdbcTemplate.queryForObject(sql, String.class, chainCode);
		
		final String insert = "INSERT INTO order_guides VALUES(null, ?, ?)";
		return this.simpleJdbcTemplate.getJdbcOperations().update(insert, new Object[]{orderGuide, item.getNumber()});
	}

	public int orderGuideItemRemove(final String chainCode, final Item item) {
		final String sql = "SELECT orderGuide FROM chainorderguidemap where chainCode = ?";
		final String orderGuide = this.simpleJdbcTemplate.queryForObject(sql, String.class, chainCode);
		
		final String delete = "DELETE FROM order_guides WHERE number = ? and itemNumber = ?";
		return this.simpleJdbcTemplate.getJdbcOperations().update(delete, new Object[]{orderGuide, item.getNumber()});
	}

	public boolean isItemInOrderGuide(final String chainCode, final String itemNumber) {
		final String sql = "SELECT orderGuide FROM chainorderguidemap where chainCode = ?";
		final String orderGuide = this.simpleJdbcTemplate.queryForObject(sql, String.class, chainCode);

		final String checkSql = "SELECT count(*) from order_guides WHERE itemNumber = ? AND number=?";
		final int count = this.simpleJdbcTemplate.queryForInt(checkSql, new Object[]{itemNumber, orderGuide});
		
		if(count == 0){
			return false;
		}
		
		return true;
	}
}