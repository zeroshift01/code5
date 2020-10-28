package com.code5.fw.web;

import com.code5.fw.data.InitProperty;
import com.code5.fw.db.Transaction;
import com.code5.fw.db.Transaction_SQLITE_JDBC;
import com.code5.fw.db.Transaction_SQLITE_POOL_DEV;
import com.code5.fw.db.Transaction_SQLITE_POOL_SIL;

/**
 * @author seuk
 *
 */
public class TransactionContext {

	/**
	 * 
	 */
	private static ThreadLocal<Transaction> TL = new ThreadLocal<Transaction>();

	/**
	 * @return
	 */
	private static Transaction createTransaction() {

		// [1]
		String DB_CLASS_NAME = InitProperty.DBMS_NAME();

		if ("com.code5.fw.db.Transaction_SQLITE_JDBC".equals(DB_CLASS_NAME)) {
			return new Transaction_SQLITE_JDBC();
		}

		if ("com.code5.fw.db.Transaction_SQLITE_POOL_DEV".equals(DB_CLASS_NAME)) {
			return new Transaction_SQLITE_POOL_DEV();
		}

		if ("com.code5.fw.db.Transaction_MYSQL_POOL_SIL".equals(DB_CLASS_NAME)) {
			return new Transaction_SQLITE_POOL_SIL();
		}

		throw new RuntimeException();
	}

	/**
	 * 
	 * [1]
	 * 
	 * @return
	 */
	public static Transaction getThread() {
		Transaction transaction = TL.get();
		if (transaction != null) {
			return transaction;
		}

		transaction = createTransaction();

		TL.set(transaction);
		return transaction;
	}

	/**
	 * [3]
	 */
	static void removeThread() {

		TL.remove();
	}

}
