package com.code5.fw.web;

import java.sql.SQLException;

import com.code5.fw.data.InitYaml;
import com.code5.fw.db.Transaction;

/**
 * @author zero
 *
 */
public class TransactionContext {

	// Transaction 을 공통기능으로 제공하는 유틸 클래스
	// Transaction null = SQL 기능 사용 안함
	// Transaction null 을 가정 commit(), rollback() 구현, 개발자가 쉽게 코드를 만들 수 있음

	/**
	 * 
	 */
	private static String TRANSACTION_DEFAULT = InitYaml.get().s("TRANSACTION.JOB");

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
			return transaction;

		}

		transaction = createDefaultTransaction();
		set(transaction);

		return transaction;
	}

	/**
	 * @param transaction
	 * 
	 * 
	 */
	static void set(Transaction transaction) {
		TL.set(transaction);
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
			return;
		}

		transaction.commit();
	}

	/**
	 * 
	 */
	public static void rollback() throws SQLException {

		Transaction transaction = TL.get();
		if (transaction == null) {
			return;
		}

		transaction.rollback();

	}
}
