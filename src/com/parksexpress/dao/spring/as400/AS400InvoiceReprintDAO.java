package com.parksexpress.dao.spring.as400;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.as400.util.DateChanger;
import com.parksexpress.as400.util.Spacing;
import com.parksexpress.dao.InvoiceReprintDAO;
import com.parksexpress.domain.InvoiceReprint;

public class AS400InvoiceReprintDAO implements InvoiceReprintDAO {
	private SimpleJdbcTemplate simpleJDBCTemplate;

	public AS400InvoiceReprintDAO() {
	}

	public void setDataSource(final DataSource ds) {
		this.simpleJDBCTemplate = new SimpleJdbcTemplate(ds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceReprint> getInvoicesForChain(String chainCode, String startDate, String endDate) {
		final String sql = "SELECT DISTINCT HHIORNR, HHIINVN, HHICUSN, HHIDTEI FROM HHIORDDC9 WHERE HHIZUAT = ? "
				+ "AND HHIDTEI > ? AND HHIDTEI < ? ORDER BY HHIDTEI";
		return this.simpleJDBCTemplate.getJdbcOperations().query(sql,
				new Object[] { Spacing.setCorrectSpacing(chainCode, Spacing.CHAIN_CODE), startDate, endDate },
				new InvoiceReprintRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceReprint> getInvoicesForStore(String storeNumber, String startDate, String endDate) {
		final String sql = "SELECT DISTINCT HHIORNR, HHIINVN, HHICUSN, HHIDTEI FROM HHIORDDC2 WHERE HHICUSN = ? "
				+ "AND HHIDTEI > ? AND HHIDTEI < ? ORDER BY HHIDTEI";
		
		return this.simpleJDBCTemplate.getJdbcOperations().query(sql,
				new Object[] { Spacing.setCorrectSpacing(storeNumber, Spacing.CUSTOMER), startDate, endDate },
				new InvoiceReprintRowMapper());
	}
	
	private static class InvoiceReprintRowMapper implements RowMapper {
		public InvoiceReprintRowMapper(){}

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			final InvoiceReprint invoiceReprint = new InvoiceReprint();
			try {
				invoiceReprint.setInvoiceDate(DateChanger.convertDateFromAS400(rs.getString("HHIDTEI")));
			} catch (ParseException e) {
				invoiceReprint.setInvoiceDate("Parsing Error");
			}
			invoiceReprint.setInvoiceNumber(rs.getString("HHIINVN"));
			invoiceReprint.setOrderNumber(rs.getString("HHIORNR"));
			invoiceReprint.setStoreNumber(rs.getString("HHICUSN"));
			return invoiceReprint;
		}
	}

}
