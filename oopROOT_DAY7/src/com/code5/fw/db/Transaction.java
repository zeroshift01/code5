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
	 * 
	 */
	private ArrayList<PreparedStatement> psList = new ArrayList<PreparedStatement>();

	/**
	 * 
	 */
	private ArrayList<ResultSet> rsList = new ArrayList<ResultSet>();

	/**
	 * @return
	 * @throws Exception
	 */
	protected abstract Connection getConnection() throws SQLException;

	/**
	 * 
	 */
	public void commit() {
		try {
			getConnection().commit();
		} catch (Exception ex) {

		}
	}

	/**
	 * 
	 */
	public void rollback() {
		try {
			getConnection().rollback();
		} catch (Exception ex) {

		}

	}

	/**
	 * @return
	 */
	public static Transaction getTransaction() {
		return getTransaction("com.code5.fw.db.Transaction_SQLITE_JDBC");
	}

	/**
	 * @return
	 */
	public static Transaction getTransaction(String DB_CLASS_NAME) {

		if ("com.code5.fw.db.Transaction_SQLITE_JDBC".equals(DB_CLASS_NAME)) {
			return new Transaction_SQLITE_JDBC();
		}

		if ("com.code5.fw.db.Transaction_SQLITE_POOL".equals(DB_CLASS_NAME)) {
			return new Transaction_SQLITE_JDBC();
		}

		throw new RuntimeException();
	}

	/**
	 * @param SQL
	 * @return
	 * @throws Exception
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

			getConnection().close();

		} catch (Exception ex) {

		}
	}

}
