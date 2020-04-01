package com.parksexpress.dao.spring.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.dao.ChainDAO;
import com.parksexpress.domain.Chain;
import com.parksexpress.domain.RoundingCode;

public class JDBCChainDAO implements ChainDAO {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private Logger logger = Logger.getRootLogger();
	
	public JDBCChainDAO(){
	}

	public void setDataSource(final DataSource ds) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
	}
	
	public boolean isChain(final String chainCode){
		final String sql = "SELECT count(*) from chain where chainCode = '" + chainCode + "'";
		int count = this.simpleJdbcTemplate.getJdbcOperations().queryForInt(sql);
		
		if(count == 0){
			return false;
		}else{
			return true;
		}
	}
 
	public Chain getChain(final String chainCode) {
		final String sql = "SELECT chainCode, chainID, bookNumber, name FROM chain WHERE chainCode = ?";

		return (Chain) this.simpleJdbcTemplate.getJdbcOperations().queryForObject(
				sql, new Object[] { chainCode }, new int[] { Types.VARCHAR },
				new ChainMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Chain> search(final String criteria) {
		final String sql = "SELECT chainCode, chainID, bookNumber, name FROM chain " +
					 "WHERE chainCode like ? OR name like ?";
		this.logger.warn(sql);
		
		final List<Chain> list = this.simpleJdbcTemplate.getJdbcOperations().query(
				sql, new Object[] {criteria + "%", criteria + "%"},
				new int[] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR}, new ChainMapper());
		return list;
	}

	private static final class ChainMapper implements RowMapper {
		public ChainMapper(){
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final Chain chain = new Chain();
			chain.setBookNumber(rs.getString("bookNumber"));
			chain.setChainID(rs.getLong("chainID"));
			chain.setName(rs.getString("name"));
			chain.setNumber(rs.getString("chainCode"));
			return chain;
		}

	}
	
	private static final class RoundingCodeMapper implements RowMapper {
		public RoundingCodeMapper(){
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final RoundingCode code = new RoundingCode();
			code.setCode(rs.getString("roundType"));
			final float[] codes = new float[10];
			
			codes[0] = rs.getFloat("code1");
			codes[1] = rs.getFloat("code2");
			codes[2] = rs.getFloat("code3");
			codes[3] = rs.getFloat("code4");
			codes[4] = rs.getFloat("code5");
			codes[5] = rs.getFloat("code6");
			codes[6] = rs.getFloat("code7");
			codes[7] = rs.getFloat("code8");
			codes[8] = rs.getFloat("code9");
			codes[9] = rs.getFloat("code10");
			code.setCodes(codes);
			
			return code;
			
		} 
	}

	public RoundingCode getRoundingCode(final String chainCode) {
		final String sql = "SELECT * FROM roundingcodes WHERE chainCode = ?";

		return (RoundingCode) this.simpleJdbcTemplate.getJdbcOperations().queryForObject(
				sql, new Object[] { chainCode }, new int[] { Types.VARCHAR },
				new RoundingCodeMapper());
	}	
}
