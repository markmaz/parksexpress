package com.parksexpress.dao.spring.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.dao.HistoryDAO;
import com.parksexpress.domain.User;
import com.parksexpress.domain.item.HistoryItem;
import com.parksexpress.util.DateUtil;

public class JDBCHistoryDAO implements HistoryDAO {
	public static final String PRIMARY_TABLE = "transaction_history";

	private SimpleJdbcTemplate simpleJDBCTemplate;

	public JDBCHistoryDAO() {
	}

	public void setDataSource(final DataSource ds) {
		this.simpleJDBCTemplate = new SimpleJdbcTemplate(ds);
	}

	public boolean addHistoryItem(final String date, final String details, final long userID, final String chainCode) {
		final String sql = "INSERT into " + JDBCHistoryDAO.PRIMARY_TABLE + " (historyID, userID, chainCode, transactionDate, details) "
				+ "VALUES(null, ?, ?, ?, ?)";

		final int i = this.simpleJDBCTemplate.update(sql, new Object[] { userID, chainCode, date, details });

		if (i == 0) {
			return true;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public List<HistoryItem> getHistoryItems(final String date, final long userID, final String chainCode) {
		final String sql = "SELECT chainCode, details, transactionDate, h.userID, firstName, lastName " +
							"FROM " + JDBCHistoryDAO.PRIMARY_TABLE + " as h, users u " +
							" WHERE transactionDate = ? AND h.userID = ? AND chainCode = ? " +
							" AND u.userID = h.userID";

		return this.simpleJDBCTemplate.getJdbcOperations().query(sql, new Object[] { date, userID, chainCode },
				new int[] { Types.VARCHAR, Types.INTEGER, Types.VARCHAR }, new HistoryItemMapper());
	}

	private static class HistoryItemMapper implements RowMapper {
		public HistoryItemMapper() {
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final HistoryItem item = new HistoryItem();
			item.setChainCode(rs.getString("chainCode"));
			item.setDetails(rs.getString("details"));
			try {
				item.setTransactionDate(DateUtil.convertMySQLDate(rs.getString("transactionDate")));
			} catch (ParseException e) {
				item.setTransactionDate(rs.getString("transactionDate"));
			}
			
			User user = new User();
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			user.setId(rs.getLong("userID"));
			item.setUser(user);
			return item;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoryItem> getHistoryItems(String startDate, int limit, long userID, String chainCode) {
		final String sql = "SELECT chainCode, details, transactionDate, h.userID, firstName, lastName " +
		"FROM " + JDBCHistoryDAO.PRIMARY_TABLE + " as h, users u " +
		" WHERE transactionDate >= ? " +
		" AND u.userID = h.userID AND chainCode = ? LIMIT ?" ;

		return this.simpleJDBCTemplate.getJdbcOperations().query(sql, new Object[] { startDate, chainCode, limit },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER }, new HistoryItemMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoryItem> getHistoryItems(String startDate, String endDate, long userID, String chainCode) {
		final String sql = "SELECT chainCode, details, transactionDate, h.userID, firstName, lastName " +
		"FROM " + JDBCHistoryDAO.PRIMARY_TABLE + " as h, users u " +
		" WHERE  u.userID = h.userID AND transactionDate >= ? AND transactionDate <= ? AND chainCode = ?" ;

		return this.simpleJDBCTemplate.getJdbcOperations().query(sql, new Object[] { startDate, endDate, chainCode },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR}, new HistoryItemMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistoryItem> getHistoryItems(String date, String chainCode) {
		final String sql = "SELECT chainCode, details, transactionDate, h.userID, firstName, lastName " +
		"FROM " + JDBCHistoryDAO.PRIMARY_TABLE + " as h, users u " +
		" WHERE  u.userID = h.userID AND transactionDate >= ? AND transactionDate <= ? AND chainCode = ?" ;
		
		return this.simpleJDBCTemplate.getJdbcOperations().query(sql, new Object[] { date, date, chainCode },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR}, new HistoryItemMapper());
	}
}
