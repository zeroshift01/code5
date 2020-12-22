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
	 * @return
	 * 
	 *         TODO [1]
	 */
	public static Transaction getThread() {
		Transaction transaction = TL.get();
		if (transaction != null) {
			return transaction;

		}

		transaction = createDefaultTransaction();
		setThread(transaction);

		return transaction;
	}

	/**
	 * @param transaction
	 * 
	 * 
	 */
	static void setThread(Transaction transaction) {
		TL.set(transaction);
	}

	/**
	 *
	 */
	static void removeThread() {
		Transaction transaction = TL.get();
		if (transaction != null) {
			TL.get().closeConnection();
		}
		
		TL.remove();
	}

	/**
	 * @return
	 * 
	 *         TODO [2]
	 */
	private static Transaction createDefaultTransaction() {
		return new Transaction_SQLITE_JDBC();
	}

}