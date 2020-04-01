package com.parksexpress.as400.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400ConnectionPool;
import com.ibm.as400.access.ConnectionPoolException;
public class TestBaseAS400 {
	@Test
	public void testGettersAndSetters(){
		String username = "test";
		String password = "test";
		String server = "test";
		String programName = "test";
		
		BaseAS400 base = new BaseAS400(programName, username, password, server);
		assertEquals(username, base.getUsername());
		assertEquals(password, base.getPassword());
		assertEquals(server, base.getServer());
		assertEquals(programName, base.getProgramName());
		
		BaseAS400 base2 = new BaseAS400();
		base2.setPassword(password);
		base2.setUsername(username);
		base2.setServer(server);
		base2.setProgramName(programName);
		
		assertEquals(username, base2.getUsername());
		assertEquals(password, base2.getPassword());
		assertEquals(server, base2.getServer());
		assertEquals(programName, base2.getProgramName());		
	}
	
	@Test
	public void testSuccessfulSettingConnectionPool(){
		AS400ConnectionPool pool = mock(AS400ConnectionPool.class);
		String username = "test";
		String password = "test";
		String server = "test";
		String programName = "test";
		
		BaseAS400 base = new BaseAS400(programName, username, password, server);
		base.setConnectionPool(pool);
		try {
			verify(pool).fill(server, username, password, AS400.COMMAND, 10);
		} catch (ConnectionPoolException e) {
			fail();
		}
	}
	
	@Test
	public void testUnuccessfulSettingConnectionPool(){
		AS400ConnectionPool pool = mock(AS400ConnectionPool.class);
		String username = "test";
		String password = "test";
		String server = "test";
		String programName = "test";
		
		BaseAS400 base = new BaseAS400(programName, username, password, server);
		base.setConnectionPool(pool);
		ConnectionPoolException cpe = mock(ConnectionPoolException.class);
		try {
			doThrow(cpe).when(pool).fill(server, username, password, AS400.COMMAND, 10);
		} catch (ConnectionPoolException e) {
			fail();
		}
	}	
}
