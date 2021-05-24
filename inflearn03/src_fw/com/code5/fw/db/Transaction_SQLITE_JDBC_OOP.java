package com.code5.fw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author zero
 *
 */
public class Transaction_SQLITE_JDBC_OOP extends Transaction {

	// DriverManager 객체에 org.sqlite.JDBC 객체를 등록
	// Reflection, DriverManager 객체에 org.sqlite.JDBC 객체 등록

	/**
	 *
	 */
	protected Connection createConnection() throws SQLException {

		try {

			String className = "org.sqlite.JDBC";
			Class.forName(className);

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}

		String url = "jdbc:sqlite:C:\\public\\sqlite\\code5.db";
		Connection conn = DriverManager.getConnection(url);

		return conn;

	}

}
