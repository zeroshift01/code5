package com.code5.fw.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 */
public class Transaction_SQLITE_JDBC_CODE5 extends Transaction {

	private static String URL = InitYaml.get().dec("Transaction_SQLITE_JDBC_CODE5.URL");

	/**
	 *
	 */
	protected Connection createConnection() throws SQLException {

		SQLiteConfig config = new SQLiteConfig();
		Connection conn = org.sqlite.JDBC.createConnection(URL, config.toProperties());

		return conn;

	}

}
