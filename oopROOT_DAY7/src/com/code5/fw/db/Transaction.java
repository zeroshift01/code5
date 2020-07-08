package com.code5.fw.db;

import java.sql.Connection;

/**
 * @author seuk
 *
 */
public abstract class Transaction {

	/**
	 * @return
	 * @throws Exception
	 */
	protected abstract Connection getConnection() throws Exception;

	/**
	 * 
	 */
	public abstract void commit();

	/**
	 * 
	 */
	public abstract void rollback();

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
}
