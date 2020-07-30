package com.code5.fw.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

/**
 * @author seuk
 *
 */
class Transaction_SQLITE_JDBC extends Transaction {

	/**
	 *
	 */
	public String defaultCrypt() {
		return "ARIA";
	}

	/**
	 * 
	 */
	private Connection conn = null;

	/**
	 *
	 */
	protected Connection getConnection() throws SQLException {

		if (this.conn != null) {
			return this.conn;
		}

		SQLiteConfig config = new SQLiteConfig();
		this.conn = org.sqlite.JDBC.createConnection("jdbc:sqlite:C:\\onedrive\\public\\sqlitecode5.db",
				config.toProperties());

		this.conn.setAutoCommit(false);

		return this.conn;

	}

}
