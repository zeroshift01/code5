package com.code5.fw.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 */
public class CreateConncectionByJDBC_test {

	// jdbc:sqlite:/a/b/code5.db
	private String url = InitYaml.get().s("Transaction_SQLITE_JDBC_CODE5_DEV.URL");

	/**
	 * @return
	 * @throws SQLException
	 */
	@Test
	public void createConnectionBySQLite() throws Exception {

		org.sqlite.SQLiteConfig config = new org.sqlite.SQLiteConfig();
		Properties properties = config.toProperties();

		Connection conn = org.sqlite.JDBC.createConnection(url, properties);
		conn.setAutoCommit(true);

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void createConnectionByDriverManager() throws Exception {

		String className = "org.sqlite.JDBC";
		Class.forName(className);

		Connection conn = DriverManager.getConnection(url);
		conn.setAutoCommit(true);

	}

}
