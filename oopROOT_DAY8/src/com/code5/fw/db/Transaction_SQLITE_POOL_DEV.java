package com.code5.fw.db;

import java.sql.Connection;
import java.sql.Driver;

/**
 * @author seuk
 *
 */
public class Transaction_SQLITE_POOL_DEV extends Transaction {

	/**
	 *
	 */
	protected Connection _getConnection() throws Exception {

		Driver driver = (Driver) Class.forName("tomct.jdbc.pool.Driver").getConstructor().newInstance();
		return driver.connect("jdbc:tomcat:pool:sqlite", null);

	}

}
