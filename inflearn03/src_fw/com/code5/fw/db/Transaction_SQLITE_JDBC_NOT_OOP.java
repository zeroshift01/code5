package com.code5.fw.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author zero
 *
 */
public class Transaction_SQLITE_JDBC_NOT_OOP extends Transaction {

	/**
	 *
	 */
	protected Connection createConnection() throws SQLException {

		String url = "jdbc:sqlite:C:\\public\\sqlite\\code5.db";
		org.sqlite.SQLiteConfig config = new org.sqlite.SQLiteConfig();
		Properties properties = config.toProperties();

		org.sqlite.SQLiteConnection conn = org.sqlite.JDBC.createConnection(url, properties);

		return conn;

	}

}
