package com.code5.fw.web;

import java.sql.SQLException;

import com.code5.fw.data.InitYaml;
import com.code5.fw.db.Transaction;
import com.code5.fw.trace.Trace;

/**
 * @author zero
 *
 */
public class TransactionContext {

	/**
	 * 
	 */
	private static String TRANSACTION_DEFAULT = InitYaml.get().s("TRANSACTION.JOB");

	/**
	 * 
	 */
	private static Trace trace = new Trace(TransactionContext.class);

	/**
	 * 
	 */
	private TransactionContext() {

	}

	/**
	 * 
	 */
	private static ThreadLocal<Transaction> TL = new ThreadLocal<Transaction>();

	/**
	 * @return
	 * 
	 * 
	 */
	public static Transaction get() {
		Transaction transaction = TL.get();
		if (transaction != null) {

			trace.write("getThread");
			return transaction;

		}

		transaction = createDefaultTransaction();
		set(transaction);

		trace.write("getThread");
		return transaction;
	}

	/**
	 * @param transaction
	 * 
	 * 
	 */
	static void set(Transaction transaction) {
		TL.set(transaction);
		trace.write("setThread");
	}

	/**
	 *
	 */
	static void remove() {
		Transaction transaction = TL.get();
		if (transaction != null) {
			TL.get().closeConnection();
		}

		TL.remove();
		trace.write("removeThread");
	}

	/**
	 * @return
	 */
	private static Transaction createDefaultTransaction() {
		return Transaction.createTransaction(TRANSACTION_DEFAULT);
	}

	/**
	 * @throws Exception
	 */
	public static void commit() throws SQLException {

		Transaction transaction = TL.get();
		if (transaction == null) {
			trace.write("commit null");
			return;
		}

		transaction.commit();
		trace.write("commit");
	}

	/**
	 * 
	 */
	public static void rollback() throws SQLException {

		Transaction transaction = TL.get();
		if (transaction == null) {
			trace.write("rollback null");
			return;
		}

		transaction.rollback();
		trace.write("rollback");

	}
}
