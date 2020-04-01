package com.parksexpress.dao.spring.as400;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.as400.util.DateChanger;
import com.parksexpress.as400.util.Spacing;
import com.parksexpress.dao.MovementDAO;
import com.parksexpress.domain.item.MovementItem;

public class AS400MovementDAO implements MovementDAO {
	private SimpleJdbcTemplate simpleJdbcTemplate;

	public AS400MovementDAO() {
	}

	public void setDataSource(final DataSource ds) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MovementItem> getMovement(String startDate, String endDate, String priceBookCode, String storeNumber) throws Exception {
		final String sql = "SELECT FFIDGTC as CHECK_DIGIT, HHIITMN AS ITEM_NUMBER, SUM(HHIEXSN) AS EXT_COST_AMOUNT, "
				+ "SUM(HHIQYSA) AS ORDERED, " + "SUM(HHIEXSR) AS EXT_SRP, FFIPCKI AS PACK, FFISZEI AS SIZE, "
				+ "FFIIDE1 AS DESCRIPTION, "
				+ "FFIUPCN AS UPC, FFIUPCP as Retail_UPC, FFJVNDN AS VENDOR_NUMBER "
				+ "FROM FFIITMAC0 as Item_Master, FFJITMBC2 as Item_Balance,  HHIORDDC0 as History "
				+ "WHERE History.HHICUSN = ? AND History.HHIDTEI >= ? AND History.HHIDTEI <= ? "
				+ "AND Item_Master.FFIITMN = History.HHIITMN AND Item_Balance.FFJPBHN = ? "
				+ "AND FFJCLSN NOT IN (' 95', '200', '999') AND Item_Balance.FFJITMN = History.HHIITMN "
				+ "GROUP BY FFIDGTC, HHIITMN, FFIPCKI, FFISZEI, FFIIDE1, FFIUPCN, FFIUPCP, FFJVNDN "
				+ "ORDER BY ITEM_NUMBER, PACK, SIZE, DESCRIPTION, UPC, VENDOR_NUMBER ";

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] {
				Spacing.setCorrectSpacing(storeNumber, Spacing.CUSTOMER), DateChanger.convertDateToAS400(startDate), 
				DateChanger.convertDateToAS400(endDate), Spacing.setCorrectSpacing(priceBookCode, Spacing.HEADER), }, 
				new ItemMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MovementItem> getMovement(String startDate, String endDate, String storeNumber) throws Exception {
		final String sql = "SELECT FFIDGTC as CHECK_DIGIT, HHIITMN AS ITEM_NUMBER, SUM(HHIEXSN) AS EXT_COST_AMOUNT, "
			+ "SUM(HHIQYSA) AS ORDERED, " + "SUM(HHIEXSR) AS EXT_SRP, FFIPCKI AS PACK, FFISZEI AS SIZE, "
			+ "FFIIDE1 AS DESCRIPTION, "
			+ "FFIUPCN AS UPC, FFIUPCP as Retail_UPC, FFJVNDN AS VENDOR_NUMBER "
			+ "FROM FFIITMAC0 as Item_Master, FFJITMBC2 as Item_Balance,  HHIORDDC0 as History "
			+ "WHERE History.HHICUSN = ? AND History.HHIDTEI >= ? AND History.HHIDTEI <= ? "
			+ "AND Item_Master.FFIITMN = History.HHIITMN "
			+ "AND FFJCLSN NOT IN (' 95', '200', '999') " + "AND Item_Balance.FFJITMN = History.HHIITMN "
			+ "GROUP BY FFIDGTC, HHIITMN, FFIPCKI, FFISZEI, FFIIDE1, FFIUPCN, FFIUPCP, FFJVNDN "
			+ "ORDER BY ITEM_NUMBER, PACK, SIZE, DESCRIPTION, UPC, VENDOR_NUMBER ";

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[] {
				Spacing.setCorrectSpacing(storeNumber, Spacing.CUSTOMER), DateChanger.convertDateToAS400(startDate), 
				DateChanger.convertDateToAS400(endDate), }, 
				new ItemMapper());
	}

	private static final class ItemMapper implements RowMapper {
		public ItemMapper() {
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final MovementItem item = new MovementItem();
			item.setNumber(rs.getString("ITEM_NUMBER").trim());
			item.setCheckDigit(rs.getString("CHECK_DIGIT"));
			item.setPack(rs.getString("PACK"));
			item.setDescription(rs.getString("DESCRIPTION"));
			item.setSize(rs.getString("SIZE"));
			item.setCartonUPCNumber(rs.getString("UPC"));
			item.setRetailUPCNumber(rs.getString("Retail_UPC"));
			item.setVendorNumber(rs.getString("VENDOR_NUMBER"));
			
			final float fSRP = rs.getFloat("EXT_SRP");
			final int fQty = rs.getInt("ORDERED");
			final float fCost = rs.getFloat("EXT_COST_AMOUNT");

			final float fProfitDollars = fSRP - fCost;
			float fProfitPercent = 0;

			if (fSRP != 0) {
				fProfitPercent = (fProfitDollars / fSRP) * 100;
			}

			final DecimalFormat format = new DecimalFormat("#########.##");
			format.setMinimumFractionDigits(2);
			format.setMaximumFractionDigits(2);

			item.setExtendedCostAmount(new BigDecimal(fCost));
			item.setSrp(new BigDecimal(fSRP));
			item.setFullCase(fQty);
			item.setProfitDollars(new BigDecimal(fProfitDollars));
			item.setProfitPercent(format.format(fProfitPercent));
			return item;
		}
	}

}
