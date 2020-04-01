package com.parksexpress.dao.spring.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.parksexpress.dao.ChainDAO;
import com.parksexpress.dao.StoreDAO;
import com.parksexpress.dao.UserDAO;
import com.parksexpress.domain.Permission;
import com.parksexpress.domain.User;

public class JDBCUserDAO implements UserDAO {
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private SimpleJdbcInsert insertUser;
	private StoreDAO storeDAO;
	private ChainDAO chainDAO;
	
	private Logger log = Logger.getLogger(JDBCUserDAO.class);

	public JDBCUserDAO(){
	}

	public void setDataSource(final DataSource ds) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(ds);
		this.insertUser = new SimpleJdbcInsert(ds).withTableName("users")
				.usingGeneratedKeyColumns("userid");
	}

	public void setStoreDAO(final StoreDAO storeDAO) {
		this.storeDAO = storeDAO;
	}

	public void setChainDAO(final ChainDAO chainDAO){
		this.chainDAO = chainDAO;
	}
	
	public User isAuthorized(final String username, final String password)
			throws InvalidCredentialsException {
		final String sql = "SELECT firstName, lastName, password, emailAddress, username, userid "
				+ "FROM users where username = ? AND password =?";
		this.log.debug(sql);
		
		try{
			final User user = (User) this.simpleJdbcTemplate.getJdbcOperations()
					.queryForObject(sql, new Object[] { username, password },
							new int[] { Types.VARCHAR, Types.VARCHAR },
							new UserMapper());
	
			if (user == null) {
				throw new InvalidCredentialsException("Invalid user");
			}
	
			user.setStores(this.storeDAO.getStoresForUser(user.getId(), true));
			user.setPermissions(this.getPermissions(new Long(user.getId()).intValue()));

			if(user.getStores().size() > 0){
				final String chainCode = user.getStores().get(0).getChainCode();
				user.setZones(this.storeDAO.getZones(chainCode));
				user.setOrderGuideNumber(this.getOrderGuideNumber(chainCode));
			}
			
			return user;
		}catch(EmptyResultDataAccessException e){
			throw new InvalidCredentialsException("Invalid user");
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> search(final String searchCriteria, final int searchType) {
		String sql = "SELECT userid, firstName, lastName, password, emailAddress, username "
				+ "FROM users WHERE ";

		switch (searchType) {
    		case USERNAME:
    			sql += "username ";
    			break;
    		case EMAIL:
    			sql += "emailAddress ";
    			break;
    		case FIRSTNAME:
    			sql += "firstName ";
    			break;
    		case LASTNAME:
    			sql += "lastName ";
    			break;
    		default:
    			sql += "username ";
		}

		sql += " like ? ORDER BY username";
		this.log.debug(sql);

		final List<User> list = this.simpleJdbcTemplate.getJdbcOperations().query(
				sql, new Object[] { searchCriteria + "%" },
				new int[] { java.sql.Types.VARCHAR }, new UserMapper());
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAll() {
		final String sql = "SELECT userid, firstName, lastName, password, emailAddress, username "
				+ "FROM users order by username";

		return this.simpleJdbcTemplate.getJdbcOperations().query(sql,
				new UserMapper());
	}

	public User getUser(final int userID) {
		final String sql = "SELECT userid, emailAddress, firstName, lastName, password, username "
				+ "FROM users WHERE userid = ?";

		return (User) this.simpleJdbcTemplate.getJdbcOperations().queryForObject(
				sql, new Object[] { new Integer(userID) },
				new int[] { Types.INTEGER }, new UserMapper());
	}

	public static final class UserMapper implements RowMapper {
		public UserMapper(){
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final User user = new User();
			user.setId(rs.getLong("userid"));
			user.setEmailAddress(rs.getString("emailAddress"));
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			user.setPassword(rs.getString("password"));
			user.setUsername(rs.getString("username"));
			return user;
		}
	}

	public static final class PermissionMapper implements RowMapper {
		public PermissionMapper(){
		}

		public Object mapRow(final ResultSet rs, final int row) throws SQLException {
			final Permission permission = new Permission();
			permission.setDescription(rs.getString("description"));
			permission.setName(rs.getString("name"));
			permission.setId(rs.getInt("permissionID"));
			return permission;
		}
	}

	public void delete(final User user) {
		this.log.debug("Deleting user:" + user.toString());
		this.simpleJdbcTemplate.getJdbcOperations().update(
				"DELETE FROM users WHERE userid = ?",
				new Object[] { user.getId() });
	}

	public Number saveOrUpdate(final User user) {
		if (user.getId() == 0) {
			return this.insert(user);
		} else {
			return this.update(user);
		}
	}

	private Number update(final User user) {
		this.log.debug("updating user: " + user.toString());
		this.simpleJdbcTemplate
				.getJdbcOperations()
				.update(
						"UPDATE users SET emailAddress=?, "
								+ "lastName=?, firstName=?, password=?, username=? WHERE userid=?",
						new Object[] { user.getEmailAddress(),
								user.getLastName(), user.getFirstName(),
								user.getPassword(), user.getUsername(),
								new Long(user.getId()), });

		return user.getId();
	}

	private Number insert(final User user) {
		this.log.debug("adding user: " + user.toString());
		final SqlParameterSource parameters = new MapSqlParameterSource().addValue(
				"emailAddress", user.getEmailAddress()).addValue("lastName",
				user.getLastName()).addValue("firstName", user.getFirstName())
				.addValue("username", user.getUsername()).addValue("password",
						user.getPassword()).addValue("active", "Y");

		return this.insertUser.executeAndReturnKey(parameters);
	}

	public void addStoreToUser(final int userID, final String storeNumber) {
		final String sql = "INSERT INTO userstoremap (userID, storeNumber) VALUES(?, ?)";
		this.log.warn(sql);

		this.simpleJdbcTemplate.getJdbcOperations().update(sql,
				new Object[] { new Integer(userID), storeNumber });
	}

	public void removeStoreFromUser(final int userID, final String storeNumber) {
		final String sql = "DELETE FROM userstoremap WHERE userID = ? AND storeNumber = ?";
		this.log.warn(sql);

		this.simpleJdbcTemplate.getJdbcOperations().update(sql,
				new Object[] { new Integer(userID), storeNumber });
	}

	public void removeAllStoresFromUser(final long userID) {
		final String sql = "DELETE FROM userstoremap WHERE userID = ?";
		this.log.debug(sql);

		this.simpleJdbcTemplate.getJdbcOperations().update(sql,
				new Object[] { new Long(userID) });
	}

	@SuppressWarnings("unchecked")
	public List<Permission> getPermissions(final int userID) {
		final String sql = "SELECT p.name, p.description, p.permissionID, priority "
					 + "FROM permissions p, permissions_users_map pum "
					 + "WHERE pum.userID = ? AND pum.permissionID = p.permissionID";
		
		final List<Permission> list = this.simpleJdbcTemplate.getJdbcOperations()
			.query(sql, new Object[]{userID}, new PermissionMapper());
		return list;
	}

	@SuppressWarnings("unchecked")
	public boolean hasPermission(final String permission, final int userID) {
		final String sql = "SELECT p.name, p.description, p.permissionID, priority "
			 + "FROM permissions p, permissions_users_map pum "
			 + "WHERE pum.userID = ? AND pum.permissionID = p.permissionID AND p.name = ?";
		
		final List list = this.simpleJdbcTemplate.queryForList(sql, new Object[]{userID, permission});
		
		if(list.size() == 0){
			return false;
		}
		
		return true;
	}

	public void addPermission(final String permission, final int userID) {
		final Permission p = this.getPermission(permission);
		
		if(p != null){
			final String sql = "INSERT INTO permissions_users_map (userID, permissionID) VALUES (?, ?)";
			this.simpleJdbcTemplate.getJdbcOperations().update(sql, new Object[]{userID, p.getId()});
		}
	}

	public void removePermission(final String permission, final int userID) {
		final Permission p = this.getPermission(permission);
		
		if(p != null){
			final String sql = "DELETE FROM permissions_users_map WHERE userID = ? AND permissionID = ?";
			this.simpleJdbcTemplate.update(sql, new Object[]{userID, p.getId()});
		}
		
	}
	
	public void removeAllPermissions(final int userID){
		final String sql = "DELETE FROM permission_user_map WHERE userID = ?";
		this.simpleJdbcTemplate.update(sql, new Object[]{userID});
	}
	
	public Permission getPermission(final String name){
		final String sql = "SELECT permissionID, name, description, priority FROM permissions WHERE name = ?";
		return (Permission) this.simpleJdbcTemplate.getJdbcOperations()
			.queryForObject(sql, new Object[]{name}, new PermissionMapper());
	}
	
	@SuppressWarnings("unchecked")
	public List<Permission> getPermissions(){
		final String sql = "SELECT permissionID, name, description, priority FROM permissions";
		final List<Permission> list = this.simpleJdbcTemplate.getJdbcOperations().query(sql, new PermissionMapper());
		return list;
	}

	public String getOrderGuideNumber(final String chainCode) {
		final String sql = "SELECT orderGuide FROM chainorderguidemap where chainCode = ?";
		return this.simpleJdbcTemplate.queryForObject(sql, String.class, chainCode);
	}
}
