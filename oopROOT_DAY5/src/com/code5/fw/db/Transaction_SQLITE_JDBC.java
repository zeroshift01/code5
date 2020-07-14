package com.code5.fw.db;

import java.sql.Connection;

import org.sqlite.SQLiteConfig;

/**
 * @author seuk
 *
 */
public class Transaction_SQLITE_JDBC extends Transaction {

	/**
	 * 
	 */
	private Connection conn = null;

	/**
	 *
	 */
	protected Connection getConnection() throws Exception {

		// [2]
		if (this.conn != null) {
			return this.conn;
		}

		// [1]
		SQLiteConfig config = new SQLiteConfig();
		this.conn = org.sqlite.JDBC.createConnection("jdbc:sqlite:C:\\onedrive\\public\\sqlitecode5.db",
				config.toProperties());

		return this.conn;

	}

}
