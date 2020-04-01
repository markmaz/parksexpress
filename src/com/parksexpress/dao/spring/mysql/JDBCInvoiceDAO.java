package com.parksexpress.dao.spring.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.dao.InvoiceDAO;
import com.parksexpress.domain.Invoice;

public class JDBCInvoiceDAO implements InvoiceDAO {
	private SimpleJdbcTemplate simpleJDBCTemplate;
	
	public JDBCInvoiceDAO(){
	}

	public void setDataSource(final DataSource ds){
		this.simpleJDBCTemplate = new SimpleJdbcTemplate(ds);
	}
	
	public List<Invoice> getInvoicesByChain(final String chainCode) {
		final String sql = "SELECT invoiceID, invoiceNumber, invoiceDate, storeNumber, invoiceAmount, code, invoiceType " +
		             "FROM invoices i, stores s WHERE s.chainCode = ? AND s.storeNumber = i.storeNumber order by invoiceNumber, invoiceDate";
		
		return null;
	}

	public List<Invoice> getInvoicesByStore(final String storeNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	private static class InvoiceMapper implements RowMapper{
		public InvoiceMapper(){
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
	        DecimalFormat format = new DecimalFormat("#########.##");
	        format.setMaximumFractionDigits(2);
	        format.setMinimumFractionDigits(2);
	        
			final Invoice invoice = new Invoice();
			invoice.setID(rs.getFloat("invoiceID"));
			invoice.setNumber(rs.getString("invoiceNumber"));
			invoice.setDate(rs.getString("invoiceDate"));
			invoice.setStoreNumber(rs.getString("storeNumber"));
			invoice.setAmount(rs.getBigDecimal("nvoiceAmount"));
			invoice.setCode(rs.getString("code"));
			invoice.setType(rs.getString("invoiceType"));
			
			return invoice;
		}
	}
}
