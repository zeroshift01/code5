package com.code5.fw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 */
public class Transaction_MYSQL_JDBC extends Transaction {

	private static String URL = InitYaml.get().dec("Transaction_MYSQL_JDBC.URL");

	/**
	 *
	 */
	protected Connection createConnection() throws SQLException {

		String[] xx = URL.split(",");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			throw new SQLException(ex.getMessage());
		}

		Connection conn = DriverManager.getConnection(xx[0], xx[1], xx[2]);
		return conn;

	}

}
