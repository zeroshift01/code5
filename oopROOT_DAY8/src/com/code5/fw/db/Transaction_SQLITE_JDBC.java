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
	protected Connection _getConnection() throws Exception {

		SQLiteConfig config = new SQLiteConfig();
		return org.sqlite.JDBC.createConnection("jdbc:sqlite:C:\\onedrive\\public\\sqlitecode5.db",
				config.toProperties());

	}

}
