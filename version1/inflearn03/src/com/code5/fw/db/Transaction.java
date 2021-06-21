package com.code5.fw.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author zero
 * 
 * 
 * 
 *
 */
public abstract class Transaction {

	// conn 생성 -> createConnection, 늦은 객체 생성
	// SQL 기능에 필요한 자원 생성 -> PrepareStatement, Statement, ResultSet
	// conn 반납 -> SQL 기능에 사용된 자원 반납, close

	// conn.setAutoCommit(false); -> setAutoCommitFalse
	// conn.commit(); -> commit
	// conn.rollback(); -> rollback

	/**
	 * 
	 */
	private Connection conn = null;

	/**
	 * @return
	 */
	protected abstract Connection createConnection() throws SQLException;

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
	public void commit() throws SQLException {

		if (this.conn == null) {
			return;
		}

		this.close();
		this.conn.commit();

	}

	/**
	 * 
	 */
	public void rollback() throws SQLException {

		if (this.conn == null) {
			return;
		}

		this.close();
		this.conn.rollback();

	}

	/**
	 * 
	 */
	private ArrayList<Statement> stList = new ArrayList<Statement>();

	/**
	 * 
	 */
	private ArrayList<ResultSet> rsList = new ArrayList<ResultSet>();

	/**
	 * @param SQL
	 * @return
	 * @throws Exception
	 */
	PreparedStatement prepareStatement(String SQL) throws SQLException {
		Connection connection = getConnection();
		PreparedStatement ps = connection.prepareStatement(SQL);
		stList.add(ps);
		return ps;

	}

	/**
	 * @return
	 * @throws SQLException
	 */
	Statement createStatement() throws SQLException {
		Connection connection = getConnection();
		Statement st = connection.createStatement();
		stList.add(st);
		return st;

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
	void close() {

		for (int i = 0; i < rsList.size(); i++) {
			try {
				rsList.get(i).close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		rsList.clear();

		for (int i = 0; i < stList.size(); i++) {
			try {
				stList.get(i).close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		stList.clear();

	}

	/**
	 * 
	 */
	private boolean setAutoCommit = false;

	/**
	 * 
	 */
	public void setAutoCommitFalse() throws SQLException {

		if (setAutoCommit) {
			return;
		}

		setAutoCommit = true;

		conn.setAutoCommit(false);
	}

}
