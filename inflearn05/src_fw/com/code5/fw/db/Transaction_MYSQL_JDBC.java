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

		String url = "jdbc:mysql://1.2.3.4:112233/mysql";
		String id = "id";
		String pin = "pin";

		Connection conn = DriverManager.getConnection(url, id, pin);
		return conn;

	}

}
