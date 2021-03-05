package com.code5.fw.web;

import com.code5.fw.db.Transaction;
import com.code5.fw.db.Transaction_SQLITE_JDBC;

/**
 * @author zero
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

	/**
	 * @throws Exception
	 */
	public static void commit() throws Exception {
		Transaction transaction = TL.get();
		if (transaction == null) {
			throw new Exception();
		}

		transaction.commit();

	}

	/**
	 * 
	 */
	public static void rollback() {
		Transaction transaction = TL.get();
		if (transaction == null) {
			return;
		}

		transaction.rollback();
	}
}
