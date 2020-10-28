package com.code5.fw.db;

import java.sql.Connection;

/**
 * @author seuk
 *
 */
public abstract class Transaction {

	/**
	 * @return
	 * 
	 *         [1]
	 */
	protected abstract Connection _getConnection() throws Exception;

	/**
	 * 
	 */
	private Connection conn = null;

	/**
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		this.conn = _getConnection();
		return conn;
	}

	/**
	 * [2]
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
	public void close() {
		try {

			if (this.conn == null) {
				return;
			}

			this.conn.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
