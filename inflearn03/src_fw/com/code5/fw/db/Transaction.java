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
 */
public abstract class Transaction {

	// 트랜잭션 기능을 제공할
	// setAutoCommitFalse(), commit(), rollback() 구현

	// 위 기능을 위해 JDBC Connection 객체를 맴버객체로 보관
	// 위임전략, 필요한 것만 사용

	// 객체지향, Connection 객체는 실행 시점에 종류가 결정되고 생성됨

	// setAutoCommitFalse(), PreparedStatement() 사용시 createConnection() 실행
	// 늦은 객체 생성, 생성비용이 높은 객체사용에 효과적

	// SQL 실행에 필요한 자원을 개발자가 고민 없이 마무리
	// closeConnection()

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

		if (this.conn == null) {
			this.conn = getConnection();
		}

		PreparedStatement ps = this.conn.prepareStatement(SQL);
		stList.add(ps);
		return ps;

	}

	/**
	 * @return
	 * @throws SQLException
	 */
	Statement createStatement() throws SQLException {
		if (this.conn == null) {
			this.conn = getConnection();
		}
		Statement st = this.conn.createStatement();
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

		if (this.conn == null) {

			this.conn = createConnection();
		}

		setAutoCommit = true;

		conn.setAutoCommit(false);
	}

	/**
	 * @param tx
	 * @return
	 */
	public static Transaction createTransaction(String tx) {

		if ("com.code5.fw.db.Transaction_SQLITE_POOL".equals(tx)) {
			return new Transaction_SQLITE_POOL();
		}

		if ("com.code5.fw.db.Transaction_SQLITE_JDBC_CODE5".equals(tx)) {
			return new Transaction_SQLITE_JDBC_CODE5();
		}

		if ("com.code5.fw.db.Transaction_SQLITE_JDBC_CODE5_DEV".equals(tx)) {
			return new Transaction_SQLITE_JDBC_CODE5_DEV();
		}

		throw new RuntimeException();
	}
}
