package com.parksexpress.as400.util;

import org.apache.log4j.Logger;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400ConnectionPool;
import com.ibm.as400.access.ConnectionPoolException;

public class BaseAS400 {

	public static AS400ConnectionPool connectionPool;
	public static final int CONNECTIONS = 10;

	private String programName;
	private String username;
	private String password;
	private String server;
	private Logger log = Logger.getRootLogger();

	public BaseAS400() {
	}

	public BaseAS400(final String programName, final String username, final String password, final String server) {
		this.programName = programName;
		this.username = username;
		this.password = password;
		this.server = server;
	}

	public void setProgramName(final String programName) {
		this.programName = programName;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setServer(final String server) {
		this.server = server;
	}

	public AS400 getConnection() throws Exception {
		return BaseAS400.connectionPool.getConnection(this.server, this.username, this.password);
	}

	@SuppressWarnings("static-access")
	public void setConnectionPool(final AS400ConnectionPool connectionPool) {
		BaseAS400.connectionPool = connectionPool;

		try {
			this.connectionPool.fill(this.server, this.username, this.password, 
					AS400.COMMAND, BaseAS400.CONNECTIONS);
		} catch (ConnectionPoolException e) {
			this.log.fatal(e.getMessage());
		}
	}

	public String getProgramName() {
		return this.programName;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getServer() {
		return this.server;
	}

}