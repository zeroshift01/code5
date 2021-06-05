package com.code5.fw.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 */
public class Transaction_SQLITE_JDBC_CODE5_DEV extends Transaction {

	private static String URL = InitYaml.get().s("Transaction_SQLITE_JDBC_CODE5_DEV.URL");

	/**
	 * @return
	 */
	public static String getURL() {
		return URL;
	}

	/**
	 *
	 */
	protected Connection createConnection() throws SQLException {

		String url = URL;

		SQLiteConfig config = new SQLiteConfig();
		Connection conn = org.sqlite.JDBC.createConnection(url, config.toProperties());

		return conn;

	}

}
