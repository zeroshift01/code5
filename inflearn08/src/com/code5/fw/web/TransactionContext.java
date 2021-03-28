package com.code5.fw.web;

import com.code5.fw.data.InitProperty;
import com.code5.fw.db.Transaction;

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
	 */
	private static Transaction createDefaultTransaction() {
		String tx = InitProperty.TRANSACTION_DEFAULT();
		return Transaction.createTransaction(tx);
	}

}
