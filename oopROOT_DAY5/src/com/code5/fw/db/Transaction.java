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
	protected abstract Connection getConnection() throws Exception;

	/**
	 * [2]
	 */
	public void commit() {
		try {
			getConnection().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * [3]
	 */
	public void rollback() {
		try {
			getConnection().rollback();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @return
	 * 
	 *         [4]
	 */
	public static Transaction getTransaction() {
		return getTransaction("com.code5.fw.db.Transaction_SQLITE_JDBC");
	}

	/**
	 * @return
	 * 
	 *         [5]
	 */
	public static Transaction getTransaction(String DB_CLASS_NAME) {

		if ("com.code5.fw.db.Transaction_MYSQL_JDBC".equals(DB_CLASS_NAME)) {
			return new Transaction_MYSQL_JDBC();
		}

		if ("com.code5.fw.db.Transaction_MYSQL_POOL".equals(DB_CLASS_NAME)) {
			return new Transaction_MYSQL_POOL();
		}

		if ("com.code5.fw.db.Transaction_SQLITE_JDBC".equals(DB_CLASS_NAME)) {
			return new Transaction_SQLITE_JDBC();
		}

		if ("com.code5.fw.db.Transaction_SQLITE_POOL".equals(DB_CLASS_NAME)) {
			return new Transaction_SQLITE_JDBC();
		}

		throw new RuntimeException();
	}
}
