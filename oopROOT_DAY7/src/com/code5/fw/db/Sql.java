package com.code5.fw.db;

import java.sql.SQLException;

import com.code5.fw.data.Table;
import com.code5.fw.web.Box;
import com.code5.fw.web.TransactionContext;

/**
 * @author seuk
 *
 */
public class Sql {

	/**
	 * [1]
	 */
	private SqlRunner sqlRunner = new SqlRunner();

	/**
	 * 
	 */
	private static Sql sql = new Sql();

	/**
	 * 
	 */
	private Sql() {
		// ΩÃ±€≈Ê ∆–≈œ¿‘¥œ¥Ÿ.
	}

	/**
	 * 
	 * [2]
	 * 
	 * @return
	 */
	public static Sql getSql() {
		return sql;
	}

	/**
	 * 
	 * [3]
	 * 
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 */
	public Table getTable(String FORM_NO) throws SQLException {

		Transaction transaction = TransactionContext.getThread();
		Box box = Box.getThread();

		return sqlRunner.getTable(transaction, box, FORM_NO);
	}

	/**
	 * 
	 * [4]
	 * 
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 */
	public int executeSql(String FORM_NO) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		Box box = Box.getThread();

		return sqlRunner.executeSql(transaction, box, FORM_NO);
	}

}
