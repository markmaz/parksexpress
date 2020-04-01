package com.parksexpress.dao.spring.as400;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.as400.util.Spacing;
import com.parksexpress.dao.VendorDAO;
import com.parksexpress.domain.Vendor;

public class AS400VendorDAO implements VendorDAO {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private Logger log = Logger.getLogger(AS400VendorDAO.class);
	
	public AS400VendorDAO() {
	}

	public void setDataSource(DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vendor> getAll() {
		final String sql = "SELECT FFVVNDN AS NUMBER, FFVVNMV AS NAME FROM FFVVNDAP WHERE FFVAPEI <> 'E' ORDER BY FFVVNMV";
		this.log.debug(sql);
		
		final List<Vendor> list = this.simpleJdbcTemplate.getJdbcOperations().query(sql, new VendorRowMapper());
		this.log.debug("Vendor List size is: " + list.size());
		
		return list;
	}

	@Override
	public Vendor getVendorByName(String name) {
		this.log.debug("Vendor Name: " + name);
		final String sql = "SELECT FFVVNDN AS NUMBER, FFVVNMV AS NAME FROM FFVVNDAP WHERE FFVVNMV = ? ";
		return (Vendor)this.simpleJdbcTemplate.getJdbcOperations().queryForObject(sql, new Object[]{name}, new VendorRowMapper());
	}

	@Override
	public Vendor getVendorByNumber(String number) {
		this.log.debug("Vendor Number: " + number);
		final String sql = "SELECT FFVVNDN AS NUMBER, FFVVNMV AS NAME FROM FFVVNDAP WHERE FFVVNDN = ? ";
		return (Vendor)this.simpleJdbcTemplate.getJdbcOperations().queryForObject(sql, new Object[]{Spacing.setCorrectSpacing(number, Spacing.VENDOR)}, new VendorRowMapper());
	}
	
	private static final class VendorRowMapper implements RowMapper{
		public VendorRowMapper(){}
		
		@Override
		public Object mapRow(ResultSet resultSet, int row) throws SQLException {
			return new Vendor(resultSet.getString("NAME"), resultSet.getString("NUMBER"));
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vendor> findVendor(String vendorPart) {
		final String sql = "SELECT FFVVNDN AS NUMBER, FFVVNMV AS NAME FROM FFVVNDAP WHERE FFVAPEI <> 'E' " +
						   "AND (FFVVNDN LIKE '%" + vendorPart.toUpperCase() + "%' OR FFVVNMV LIKE '" + vendorPart.toUpperCase() + "%')";
		
		final List<Vendor> list = this.simpleJdbcTemplate.getJdbcOperations().query(sql, new VendorRowMapper());
		return list;
	}

}
