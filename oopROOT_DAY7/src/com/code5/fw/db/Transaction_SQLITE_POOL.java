package com.code5.fw.db;

import java.sql.Connection;

import org.sqlite.SQLiteConfig;

/**
 * @author seuk
 *
 */
class Transaction_SQLITE_POOL extends Transaction {

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

		SQLiteConfig config = new SQLiteConfig();
		this.conn = org.sqlite.JDBC.createConnection("jdbc:sqlite:C:\\onedrive\\public\\sqlitecode5.db",
				config.toProperties());

		return this.conn;

	}

	/**
	 * 
	 */
	public void commit() {
		try {
			this.conn.commit();
		} catch (Exception ex) {

		}
	}

	/**
	 * 
	 */
	public void rollback() {
		try {
			this.conn.rollback();
		} catch (Exception ex) {

		}
	}

}
