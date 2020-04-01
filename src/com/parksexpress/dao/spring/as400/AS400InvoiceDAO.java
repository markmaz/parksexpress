package com.parksexpress.dao.spring.as400;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.as400.util.DateChanger;
import com.parksexpress.as400.util.Spacing;
import com.parksexpress.dao.InvoiceDAO;
import com.parksexpress.domain.Invoice;

public class AS400InvoiceDAO implements InvoiceDAO {

	private SimpleJdbcTemplate simpleJDBCTemplate;

	public AS400InvoiceDAO(){}
	
	public void setDataSource(final DataSource ds) {
		this.simpleJDBCTemplate = new SimpleJdbcTemplate(ds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Invoice> getInvoicesByChain(String chainCode) {
		final String sql = "SELECT FFDAMDU AS AMT_DUE, RRCCUSN AS CUSTOMER_NUMBER, RRCINVN AS INVOICE_NUMBER, "
				+ "RRCRTPE AS TYPE, RRCJCDE AS NEWCODE, RRCINAM AS INVOICE_AMT, RRCDTEI AS INVOICE_DATE "
				+ "FROM RRCRECAP AS Invoice, FFDCSTBP Customer " 
				+ "WHERE Invoice.RRCCUSN IN (SELECT FFDCUSN FROM FFDCSTBP, BBSCHNHP "
				+ "WHERE BBSCTDC = ? AND BBSCSCD = FFDCSCD) "
				+ "AND Invoice.RRCCUSN = Customer.FFDCUSN ORDER BY INVOICE_DATE, INVOICE_NUMBER";

		return this.simpleJDBCTemplate.getJdbcOperations().query(sql,
				new Object[] { Spacing.setCorrectSpacing(chainCode, Spacing.CHAIN_CODE) }, new InvoiceMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Invoice> getInvoicesByStore(String storeNumber) {
        final String sql = "SELECT FFDAMDU AS AMT_DUE, RRCCUSN AS CUSTOMER_NUMBER, RRCINVN AS INVOICE_NUMBER, " +
        "RRCRTPE AS TYPE, RRCJCDE AS NEWCODE, RRCINAM AS INVOICE_AMT, RRCDTEI AS INVOICE_DATE " +
        "FROM RRCRECAP AS Invoice, FFDCSTBP Customer " +
        "WHERE Invoice.RRCCUSN = ? " +
        "AND Invoice.RRCCUSN = Customer.FFDCUSN ORDER BY INVOICE_DATE, INVOICE_NUMBER";

		return this.simpleJDBCTemplate.getJdbcOperations().query(sql,
				new Object[] { Spacing.setCorrectSpacing(storeNumber, Spacing.CUSTOMER) }, new InvoiceMapper());
	}

	private static class InvoiceMapper implements RowMapper {
		public InvoiceMapper() {
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final DecimalFormat format = new DecimalFormat("#########.##");
			format.setMaximumFractionDigits(2);
			format.setMinimumFractionDigits(2);
			final Invoice invoice = new Invoice();
			invoice.setStoreNumber(rs.getString("CUSTOMER_NUMBER"));
			invoice.setAmount(rs.getBigDecimal("AMT_DUE"));
			try {
				invoice.setDate(DateChanger.convertDateFromAS400(rs.getString("INVOICE_DATE")));
			} catch (ParseException e) {
				invoice.setDate("");
			}
			invoice.setNumber(rs.getString("INVOICE_NUMBER"));

			final int type = rs.getInt("TYPE");

			if (StringUtils.isEmpty(rs.getString("NEWCODE"))) {
				invoice.setType(rs.getString("NEWCODE"));
				invoice.setAdjustmentAmount(rs.getBigDecimal("INVOICE_AMT"));
			} else {
				switch (type) {
				case 1:
					if (rs.getFloat("INVOICE_AMT") < 0) {
						invoice.setType(Invoice.CREDIT_MEMO);
						invoice.setAdjustmentAmount(rs.getBigDecimal("INVOICE_AMT"));
					} else {
						invoice.setType(Invoice.INVOICE);
						invoice.setInvoiceAmount(rs.getBigDecimal("INVOICE_AMT"));
					}

					break;
				case 2:
					invoice.setType(Invoice.PAYMENT);
					invoice.setPaymentAmount(rs.getBigDecimal("INVOICE_AMT"));
					break;
				case 3:
					invoice.setType(Invoice.ADJUSTMENT);
					invoice.setAdjustmentAmount(rs.getBigDecimal("INVOICE_AMT"));
					break;
				case 4:
					invoice.setType(Invoice.FINAL);
					break;
				default:
					invoice.setType(Invoice.INVOICE);
					invoice.setAmount(new BigDecimal(0));
				}
			}
			
			return invoice;

		}
	}
}
