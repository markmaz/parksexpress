package com.parksexpress.dao.spring.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.dao.StoreDAO;
import com.parksexpress.domain.Chain;
import com.parksexpress.domain.Store;
import com.parksexpress.domain.Zone;

public class JDBCStoreDAO implements StoreDAO {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private Logger log = Logger.getRootLogger();

	public JDBCStoreDAO(){
	}

	public void setDataSource(final DataSource ds) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
	}

	public Store getStore(final String storeNumber) {
		final String sql = "SELECT storeID, address, amountDue, chainCode, city, lastOrder, name, "
				+ "number, state, terms, zip FROM stores WHERE number = ?";

		return (Store) this.simpleJdbcTemplate.getJdbcOperations().queryForObject(
				sql, new Object[] { storeNumber }, new int[] { Types.VARCHAR },
				new StoreMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Store> search(final String criteria) {
		final String sql = "SELECT storeID, address, amountDue, chainCode, city, lastOrder, name, "
				+ "number, state, terms, zip FROM stores WHERE number like ? OR name like ?";
		this.log.warn(sql);

		final List<Store> list = this.simpleJdbcTemplate.getJdbcOperations().query(
				sql, new Object[] { criteria + "%", criteria + "%" },
				new int[] { java.sql.Types.VARCHAR, java.sql.Types.VARCHAR },
				new StoreMapper());
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Store> getStoresForUser(final long id, final boolean withZones) {
		String sql = "SELECT s.storeID, address, amountDue, c.chainCode, city, lastOrder, s.name, c.name as chainName, "
				+ "number, state, terms, zip FROM stores s, userstoremap u, chain c "
				+ "WHERE number = u.storeNumber AND u.userID = ? and s.chainCode = c.chainCode";
		
		if(!withZones){
			sql += " number < 8000";
		}

		sql += " ORDER BY name";
		
		this.log.warn(sql);

		final List<Store> list = this.simpleJdbcTemplate.getJdbcOperations().query(
				sql, new Object[] { new Long(id) },
				new int[] { java.sql.Types.INTEGER }, new StoreMapperWithChain());
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Store> getStoresInChain(final String chainCode, final boolean withZones) {
		String sql = "SELECT storeID, address, amountDue, chainCode, city, lastOrder, name, "
				+ "number, state, terms, zip FROM stores "
				+ "WHERE chainCode = ?";

		if(!withZones){
			sql += " number < 8000";
		}
		
		this.log.warn(sql);

		final List<Store> list = this.simpleJdbcTemplate.getJdbcOperations().query(
				sql, new Object[] { chainCode },
				new int[] { java.sql.Types.VARCHAR }, new StoreMapper());
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Store> getStoresForZone(final String zone) {
		final String sql = "SELECT storeID, address, amountDue, chainCode, city, lastOrder, name, "
				+ "number, state, terms, zip FROM stores s, srp_store_map ssm "
				+ "WHERE srpBook = ? " + "AND s.number = ssm.storeNumber AND s.number < 8000";

		this.log.warn(sql);

		final List<Store> list = this.simpleJdbcTemplate.getJdbcOperations().query(
				sql, new Object[] { zone },
				new int[] { java.sql.Types.VARCHAR }, new StoreMapper());
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Zone> getZones(final String chainCode) {
		final String sql = "select * from stores where chainCode = ? and number > 8000 order by number";
		final List<Zone> list = this.simpleJdbcTemplate.getJdbcOperations().query(sql,
				new Object[] { chainCode },
				new int[] { java.sql.Types.VARCHAR }, new ZoneMapper());
		
		return list;
	}

	private static final class StoreMapper implements RowMapper {
		public StoreMapper(){
		}
		
		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final Store store = new Store();
			store.setAddress(rs.getString("address"));
			store.setAmountDue(rs.getBigDecimal("amountDue"));
			store.setChainCode(rs.getString("chainCode"));
			store.setCity(rs.getString("city"));
			store.setLastOrder(rs.getString("lastOrder"));
			store.setName(rs.getString("name"));
			store.setNumber(rs.getString("number"));
			store.setState(rs.getString("state"));
			store.setTerms(rs.getString("terms"));
			store.setZip(rs.getString("zip"));
			store.setStoreID(rs.getLong("storeID"));
			return store;
		}

	}
	private static final class StoreMapperWithChain implements RowMapper {
		public StoreMapperWithChain(){
		}
		
		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final Store store = new Store();
			store.setAddress(rs.getString("address"));
			store.setAmountDue(rs.getBigDecimal("amountDue"));
			store.setChainCode(rs.getString("chainCode"));
			store.setCity(rs.getString("city"));
			store.setLastOrder(rs.getString("lastOrder"));
			store.setName(rs.getString("name"));
			store.setNumber(rs.getString("number"));
			store.setState(rs.getString("state"));
			store.setTerms(rs.getString("terms"));
			store.setZip(rs.getString("zip"));
			store.setStoreID(rs.getLong("storeID"));
			
			Chain chain = new Chain();
			chain.setName(rs.getString("chainName"));
			chain.setNumber(store.getChainCode());
			store.setChain(chain);
			return store;
		}

	}
	
	public static final class ZoneMapper implements RowMapper {
		public ZoneMapper(){
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final Zone zone = new Zone();
			zone.setName(rs.getString("name"));
			zone.setNumber(rs.getString("number"));
			return zone;
		}

	}

}
