package com.code5.fw.db;

import java.sql.Connection;
import java.sql.Driver;

/**
 * @author seuk
 *
 */
public class Transaction_SQLITE_POOL extends Transaction {

	/**
	 * 
	 */
	private Connection conn = null;

	/**
	 *
	 */
	protected Connection getConnection() throws Exception {

		if (this.conn != null) {
			return this.conn;
		}

		// [1]
		Driver driver = (Driver) Class.forName("tomct.jdbc.pool.Driver").getConstructor().newInstance();
		this.conn = driver.connect("jdbc:tomcat:pool:sqlite", null);

		return this.conn;

	}

}
