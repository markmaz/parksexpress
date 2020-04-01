package com.parksexpress.dao.spring.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.dao.EmailAddressDAO;
import com.parksexpress.domain.EmailAddress;

public class JDBCEmailAddress implements EmailAddressDAO {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}
	
	@Override
	public EmailAddress getEmailAddress(int id) {
		final String sql = "SELECT emailAddressID, emailAddress, chain_code FROM email_addresses WHERE emailAddressID = ?";
		return (EmailAddress) simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[]{id}, new EmailAddressMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmailAddress> getAll() {
		final String sql = "SELECT emailAddressID, emailAddress, chain_code FROM email_addresses";
		return simpleJdbcTemplate.getJdbcOperations().query(sql, new EmailAddressMapper());
	}

	@Override
	public void delete(int id) {
		final String sql = "DELETE FROM email_addresses WHERE emailAddressID = ?";
		simpleJdbcTemplate.getJdbcOperations().update(sql, new Object[]{id});
	}

	@Override
	public void edit(int id, String emailAddress, String chainCode) {
		final String sql = "UPDATE email_addresses set emailAddress = ?, chain_code=? WHERE emailAddressID=?";
		simpleJdbcTemplate.getJdbcOperations().update(sql, new Object[]{emailAddress, chainCode, id});
	}

	@Override
	public void add(String emailAddress, String chainCode) {
		final String sql = "INSERT INTO email_addresses (emailAddress, chain_code) VALUES(?, ?)";
		simpleJdbcTemplate.getJdbcOperations().update(sql, new Object[]{emailAddress, chainCode});
	}

	public static final class EmailAddressMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			EmailAddress email = new EmailAddress();
			email.setChainCode(rs.getString("chain_code"));
			email.setEmailAddress(rs.getString("emailAddress"));
			email.setId(rs.getInt("emailAddressID"));
			
			return email;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getChainsInEmails() {
		final String sql = "SELECT DISTINCT chain_code FROM email_addresses";
		List list = simpleJdbcTemplate.getJdbcOperations().queryForList(sql);
		List<String> chains = new ArrayList<String>();
		
		for(int i = 0; i < list.size(); i++){
			Map<String, String> map = (Map)list.get(i);
			chains.add(map.get("chain_code"));
		}
		
		return chains;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmailAddress> getEmailByChain(String chain) {
		final String sql = "SELECT emailAddressID, emailAddress, chain_code FROM email_addresses WHERE chain_code=?";
		return simpleJdbcTemplate.getJdbcOperations().query(sql, new Object[]{chain}, new EmailAddressMapper());
	}
}
