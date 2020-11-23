package com.code5.fw.web;

import com.code5.fw.db.Transaction;
import com.code5.fw.db.Transaction_SQLITE_JDBC;

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
	 * 
	 * [3]
	 * 
	 * @return
	 */
	public static Transaction getThread() {
		Transaction transaction = TL.get();
		if (transaction != null) {
			return transaction;

		}

		// [3]
		transaction = createDefaultTransaction();
		setThread(transaction);

		return transaction;
	}

	/**
	 * @param transaction
	 * 
	 *                    [1]
	 */
	static void setThread(Transaction transaction) {
		TL.set(transaction);
	}

	/**
	 * [2]
	 */
	static void removeThread() {

		TL.remove();
	}

	/**
	 * @return
	 * 
	 *         [4]
	 */
	private static Transaction createDefaultTransaction() {
		return new Transaction_SQLITE_JDBC();
	}

}
