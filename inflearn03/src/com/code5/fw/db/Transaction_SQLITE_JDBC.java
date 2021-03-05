package com.code5.fw.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

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
		Properties properties = config.toProperties();

		Connection conn = org.sqlite.JDBC.createConnection("jdbc:sqlite:C:\\public\\sqlite\\code5.db", properties);

		conn.setAutoCommit(false);

		return conn;

	}

}
