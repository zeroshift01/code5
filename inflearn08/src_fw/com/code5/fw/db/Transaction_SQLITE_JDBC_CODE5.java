package com.code5.fw.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

/**
 * @author zero
 *
 */
public class Transaction_SQLITE_JDBC extends Transaction {

	/**
	 *
	 */
	protected Connection createConnection() throws SQLException {

		SQLiteConfig config = new SQLiteConfig();
		Connection conn = org.sqlite.JDBC.createConnection("jdbc:sqlite:C:/public/code5/doc/code5.db",
				config.toProperties());

		return conn;

	}

}
