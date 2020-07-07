package com.code5.fw.db;

import java.sql.Connection;

/**
 * @author seuk
 *
 */
public abstract class Transaction {

	/**
	 * @return
	 */
	protected abstract Connection getConnection() throws Exception;

	/**
	 * 
	 */
	public void commit() {
		try {
			getConnection().commit();
		} catch (Exception ex) {
			// Exception 발생을 로그로 기록할 예정
		}
	}

	/**
	 * 
	 */
	public void rollback() {
		try {
			getConnection().rollback();
		} catch (Exception ex) {
			// Exception 발생을 로그로 기록할 예정
		}
	}

	/**
	 * @return
	 */
	public static Transaction getTransaction() {
		return getTransaction("com.code5.fw.db.Transaction_MYSQL_JDBC");
	}

	/**
	 * @return
	 */
	public static Transaction getTransaction(String DB_CLASS_NAME) {

		if ("com.code5.fw.db.Transaction_MYSQL_JDBC".equals(DB_CLASS_NAME)) {
			return new Transaction_MYSQL_JDBC();
		}

		if ("com.code5.fw.db.Transaction_MYSQL_POOL".equals(DB_CLASS_NAME)) {
			return new Transaction_MYSQL_POOL();
		}

		throw new RuntimeException();
	}
}
