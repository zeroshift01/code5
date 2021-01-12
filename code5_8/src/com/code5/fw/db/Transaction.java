package com.code5.fw.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author seuk
 * 
 * 
 */
public abstract class Transaction {

	/**
	 * @return
	 * 
	 */
	protected abstract Connection createConnection() throws SQLException;

	/**
	 *
	 */
	private Connection conn = null;

	/**
	 * 
	 *
	 * 
	 * @throws Exception
	 */
	private Connection getConnection() throws SQLException {

		if (this.conn == null) {
			this.conn = createConnection();
		}
		return conn;
	}

	/**
	 *
	 */
	public void commit() {

		try {

			if (this.conn == null) {
				return;
			}

			this.conn.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 *
	 */
	public void rollback() {
		try {

			if (this.conn == null) {
				return;
			}

			this.conn.rollback();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 *
	 */
	private ArrayList<PreparedStatement> psList = new ArrayList<PreparedStatement>();

	/**
	 *
	 */
	private ArrayList<ResultSet> rsList = new ArrayList<ResultSet>();

	/**
	 * @param SQL
	 * @return
	 * @throws Exception
	 * 
	 */
	PreparedStatement prepareStatement(String SQL) throws SQLException {
		Connection connection = getConnection();
		PreparedStatement ps = connection.prepareStatement(SQL);
		psList.add(ps);
		return ps;

	}

	/**
	 * @param ps
	 * @return
	 * @throws SQLException
	 * 
	 */
	ResultSet getResultSet(PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.executeQuery();
		rsList.add(rs);
		return rs;

	}

	/**
	 *
	 */
	void close() {

		for (int i = 0; i < rsList.size(); i++) {
			try {
				rsList.get(i).close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		rsList.clear();

		for (int i = 0; i < psList.size(); i++) {
			try {
				psList.get(i).close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		psList.clear();

	}

	/**
	 *
	 */
	public void closeConnection() {

		try {

			if (conn == null) {
				return;
			}

			this.close();
			conn.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * @param tx
	 * @return
	 */
	public static Transaction createTransaction(String tx) {

		if ("com.code5.fw.db.Transaction_SQLITE_POOL".equals(tx)) {
			return new Transaction_SQLITE_POOL();
		}

		if ("com.code5.fw.db.Transaction_SQLITE_POOL_TEST".equals(tx)) {
			return new Transaction_SQLITE_POOL_TEST();
		}

		if ("com.code5.fw.db.Transaction_SQLITE_POOL_PRODUCT".equals(tx)) {
			return new Transaction_SQLITE_POOL_PRODUCT();
		}

		return new Transaction_SQLITE_JDBC();
	}

}
