package com.code5.fw.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.code5.fw.trace.Trace;

/**
 * @author seuk
 * 
 * 
 */
public abstract class Transaction {

	/**
	 * 
	 */
	private Trace trace = new Trace(this);

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

		trace.write("getConnection [" + this.hashCode() + "]");

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

			trace.write("commit [" + this.hashCode() + "]");

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

			trace.write("rollback [" + this.hashCode() + "]");

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

		trace.write("close [" + this.hashCode() + "]");

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

			trace.write("closeConnection [" + this.hashCode() + "]");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
