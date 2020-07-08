package com.code5.fw.db;

import java.sql.Connection;
import java.sql.Driver;

/**
 * @author seuk
 *
 */
public class Transaction_MYSQL_POOL extends Transaction {

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

		Driver driver = (Driver) Class.forName("tomct.jdbc.pool.Driver").getConstructor().newInstance();
		this.conn = driver.connect("jdbc:tomcat:pool:DB¿Ã∏ß", null);

		return this.conn;

	}

}
