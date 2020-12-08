package com.code5.fw.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author seuk
 *
 */
public abstract class Transaction {

	/**
	 * @return
	 * 
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
	public void close() {
		try {

			for (int i = 0; i < rsList.size(); i++) {
				rsList.get(i).close();
			}

			for (int i = 0; i < psList.size(); i++) {
				psList.get(i).close();
			}

			this.conn.close();

		} catch (Exception ex) {

		}
	}
}
