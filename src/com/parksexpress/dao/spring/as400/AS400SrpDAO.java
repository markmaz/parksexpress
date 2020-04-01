package com.parksexpress.dao.spring.as400;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.as400.util.DateChanger;
import com.parksexpress.dao.SrpDAO;
import com.parksexpress.domain.EffectiveDate;
import com.parksexpress.util.DateUtil;

public class AS400SrpDAO implements SrpDAO {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private Logger log = Logger.getLogger(AS400VendorDAO.class);

	public AS400SrpDAO() {
	}

	public void setDataSource(DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public EffectiveDate getEffectiveDateInFamily(String book,
			String familyNumber) {
		String sql = "SELECT COUNT(SSCTTNP) AS Cnt, SSCSCDT, SSCSRPP FROM SSCSRPCP  WHERE SSCSRPN = ? AND SSCTTNP = ? GROUP BY SSCSCDT, SSCSRPP";
		log.debug(sql);

		List<EffectiveDate> list = this.simpleJdbcTemplate.getJdbcOperations()
				.query(sql, new Object[] { book, familyNumber },
						new FamilyEffectiveDateMapper());

		if (list == null || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public EffectiveDate getEffectiveDateInItem(String book, String itemNumber) {
		String sql = "SELECT COUNT(SSBITMN) AS Cnt, SSBDTEF, SSBFXPR, SSBSRPP, SSBPRCS FROM SSBSRPDL0 "
				+ "WHERE SSBITMN = ? AND SSBDTEF >= ? AND SSBSRPN = ? GROUP BY SSBDTEF, SSBFXPR, SSBSRPP, SSBPRCS ";
		log.debug(sql);

		List<EffectiveDate> list;
		try {
			list = this.simpleJdbcTemplate.getJdbcOperations().query(
					sql,
					new Object[] { itemNumber,
							DateChanger.convertDateToAS400(DateUtil.today()),
							book }, new ItemEffectiveDateMapper());
			if (list == null || list.size() == 0) {
				return null;
			} else {
				return list.get(0);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static final class FamilyEffectiveDateMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			EffectiveDate effectiveDate = new EffectiveDate();
			effectiveDate.setFutureCost(rs.getFloat("SSCSRPP"));
			try {
				effectiveDate.setStartDate(DateChanger.convertDateFromAS400(rs
						.getString("SSCSCDT")));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return effectiveDate;
		}
	}

	private static final class ItemEffectiveDateMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			EffectiveDate effectiveDate = new EffectiveDate();

			if (rs.getString("SSBFXPR").equals("Y")) {
				effectiveDate.setFutureCost(rs.getFloat("SSBPRCS"));
			} else {
				effectiveDate.setFutureCost(rs.getFloat("SSBSRPP"));
			}
			try {
				effectiveDate.setStartDate(DateChanger.convertDateFromAS400(rs
						.getString("SSBDTEF")));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return effectiveDate;
		}
	}
}
