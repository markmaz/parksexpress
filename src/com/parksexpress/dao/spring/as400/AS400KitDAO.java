package com.parksexpress.dao.spring.as400;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.as400.util.Spacing;
import com.parksexpress.dao.KitDAO;
import com.parksexpress.domain.Kit;
import com.parksexpress.domain.item.KitItem;

public class AS400KitDAO implements KitDAO {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private Logger log = Logger.getRootLogger();
	
	public AS400KitDAO(){}
	
	public void setDataSource(DataSource ds) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Kit getKit(String kitNumber) {
		this.log.debug("Kit number: " + kitNumber);
		final String sql = "SELECT MMFITMK, FFIUPCP, FFIUPCN, FFIIDE1, FFIDGTC FROM MMFKITHL0, FFIITMAP " +
					 "WHERE MMFITMK = ? AND MMFITMK = FFIITMN";
		
		Kit kit = null;
		
		try{
			kit = (Kit)this.simpleJdbcTemplate.getJdbcOperations().queryForObject(
				sql, new Object[]{Spacing.setCorrectSpacing(kitNumber, Spacing.ITEM)}, new KitMapper());

			if(kit == null){
				return null;
			}
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		
		
		final String itemSql = "SELECT FFIDGTC, FFIITMN, FFIUPCP, FFIUPCN, FFIIDE1, FFIPCKI, FFISZEI, FFJVNDN, EEEPRZ1, MMGQCVL  " +
					 "FROM MMGKITDL0, FFIITMAP, FFJITMBP, EEEITMEP " +
					 "WHERE MMGITMK = ? AND MMGITMN = FFIITMN AND FFJITMN = FFIITMN AND EEEITMN = FFIITMN";
		
		final List<KitItem> kitItems = (List)this.simpleJdbcTemplate.getJdbcOperations().query(
				itemSql, new Object[]{Spacing.setCorrectSpacing(kitNumber, Spacing.ITEM)}, new KitItemMapper());
		
		kit.setComponents(kitItems);
		
		return kit;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Kit> search(String criteria) {
		this.log.debug("Criteria: " + criteria);
		final String sql = "SELECT MMFITMK, FFIUPCP, FFIUPCN, FFIIDE1, FFIDGTC FROM MMFKITHL0, FFIITMAP " +
					 "WHERE FFIIDE1 LIKE '" + criteria + "%' OR MMFITMK LIKE '" + criteria + "%' AND MMFITMK = FFIITMN";
		
		this.log.debug(sql);
		final List<Kit> list = (List<Kit>)this.simpleJdbcTemplate.getJdbcOperations().query(sql, new KitMapper());
		return list;
	}

	private static final class KitMapper implements RowMapper {
		public KitMapper(){}
		
		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			final Kit kit = new Kit();
			kit.setCartonUPCNumber(rs.getString("FFIUPCN"));
			kit.setCheckDigit(rs.getString("FFIDGTC"));
			kit.setDescription(rs.getString("FFIIDE1"));
			kit.setRetailUPCNumber(rs.getString("FFIUPCP"));
			kit.setNumber(rs.getString("MMFITMK").trim());
			return kit;
		}
	}
	
	private static final class KitItemMapper implements RowMapper {
		public KitItemMapper(){}
		
		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			final KitItem kitItem = new KitItem();
			
			kitItem.setCartonUPCNumber(rs.getString("FFIUPCN"));
			kitItem.setCheckDigit(rs.getString("FFIDGTC"));
			kitItem.setDescription(rs.getString("FFIIDE1"));
			kitItem.setNumber(rs.getString("FFIITMN"));
			kitItem.setPack(rs.getString("FFIPCKI"));
			kitItem.setQuantity(rs.getInt("MMGQCVL"));
			kitItem.setRetailUPCNumber(rs.getString("FFIUPCP"));
			kitItem.setSize(rs.getString("FFISZEI"));
			kitItem.setPrice(rs.getBigDecimal("EEEPRZ1"));
			return kitItem;
		}
	}
}