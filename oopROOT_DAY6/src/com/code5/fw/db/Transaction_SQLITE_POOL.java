package com.code5.fw.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

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
	protected Connection getConnection() throws SQLException {

		if (this.conn != null) {
			return this.conn;
		}

		try {

			Context context = new InitialContext();
			DataSource dataSource = (DataSource) context.lookup("java:/jdbc/sqlite/sqlitecode5");
			this.conn = dataSource.getConnection();

			return this.conn;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

}
