package com.code5.fw.web;

import com.code5.fw.db.Transaction;
import com.code5.fw.trace.Trace;

/**
 * @author seuk
 *
 */
public class TransactionContext {

	/**
	 * 
	 */
	private static Trace trace = new Trace(TransactionContext.class);

	/**
	 * 
	 */
	private static ThreadLocal<Transaction> TL = new ThreadLocal<Transaction>();

	/**
	 * @return
	 */
	public static Transaction getThread() {

		trace.write("getThread");

		Transaction transaction = TL.get();
		if (transaction != null) {
			return transaction;
		}

		transaction = Transaction.getTransaction();

		setThread(transaction);
		return transaction;
	}

	/**
	 * @param transaction
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
			transaction.close();
		}
		TL.remove();

		trace.write("removeThread");
	}

}
