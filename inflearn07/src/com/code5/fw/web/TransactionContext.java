package com.code5.fw.web;

import java.sql.SQLException;

import com.code5.fw.db.Transaction;
import com.code5.fw.db.Transaction_SQLITE_JDBC;
import com.code5.fw.trace.Trace;

/**
 * @author zero
 *
 */
public class TransactionContext {

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
	public static Transaction getThread() {
		Transaction transaction = TL.get();
		if (transaction != null) {

			trace.write("getThread");
			return transaction;

		}

		transaction = createDefaultTransaction();
		setThread(transaction);

		trace.write("getThread");
		return transaction;
	}

	/**
	 * @param transaction
	 * 
	 * 
	 */
	static void setThread(Transaction transaction) {
		TL.set(transaction);
		trace.write("setThread");
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
		trace.write("removeThread");
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
