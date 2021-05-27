package com.code5.fw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

/**
 * @author zero
 *
 */
public class CreateConncectionByJDBC {

	/**
	 * @return
	 * @throws SQLException
	 */
	@Test
	public void createConnection_COP() throws Exception {

		org.sqlite.SQLiteConfig config = new org.sqlite.SQLiteConfig();
		Properties properties = config.toProperties();

		String url = "jdbc:sqlite:C:/public/code5/doc/code5.db";

		Connection conn = org.sqlite.JDBC.createConnection(url, properties);
		conn.setAutoCommit(true);

	}

	/**
	 * @return
	 * @throws SQLException
	 */
	@Test
	public void createConnection_OOP() throws Exception {

		String className = "org.sqlite.JDBC";
		Class.forName(className);

		String url = "jdbc:sqlite:C:/public/code5/doc/code5.db";

		Connection conn = DriverManager.getConnection(url);
		conn.setAutoCommit(true);

	}

}
