package com.code5.fw.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * @author zero
 *
 */
public class Transaction_SQLITE_POOL extends Transaction {

	/**
	 *
	 */
	protected Connection createConnection() throws SQLException {

		try {

			Context ctx = new InitialContext();

			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/code5");

			Connection conn = ds.getConnection();

			return conn;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SQLException(ex);
		}

	}

}
