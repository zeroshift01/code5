package com.code5.fw.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author zero
 * 
 * 
 *         try-with-resources
 *
 */
public abstract class Transaction {

	/**
	 * @return
	 * 
	 *         TODO [1]
	 */
	protected abstract Connection createConnection() throws SQLException;

	/**
	 * TODO [2-1]
	 */
	private Connection conn = null;

	/**
	 * 
	 * TODO [2-2]
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
	 * TODO [3-1]
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
	 * TODO [3-1]
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
	 * TODO [4-1]
	 */
	private ArrayList<PreparedStatement> psList = new ArrayList<PreparedStatement>();

	/**
	 * TODO [4-2]
	 */
	private ArrayList<ResultSet> rsList = new ArrayList<ResultSet>();

	/**
	 * @param SQL
	 * @return
	 * @throws Exception
	 * 
	 *                   TODO [5-1]
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
	 *                      TODO [5-2]
	 */
	ResultSet getResultSet(PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.executeQuery();
		rsList.add(rs);
		return rs;

	}

	/**
	 * TODO [6]
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
	 * TODO [7]
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

}
