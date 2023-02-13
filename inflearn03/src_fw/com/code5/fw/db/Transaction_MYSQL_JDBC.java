package com.code5.fw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author zero
 *
 */
public class Transaction_MYSQL_JDBC extends Transaction {

	static {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 *
	 */
	protected Connection createConnection() throws SQLException {

		String url = "jdbc:mysql://3.38.252.50:50356/mysql";
		String id = "code5";
		String pin = "code55";

		Connection conn = DriverManager.getConnection(url, id, pin);
		return conn;

	}

}
