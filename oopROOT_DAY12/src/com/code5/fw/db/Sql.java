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
	 * 
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
		// 싱글톤 패턴
	}

	/**
	 * 
	 * 
	 * 
	 * @return
	 */
	public static Sql getSql() {
		return sql;
	}

	/**
	 * 
	 * 
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
	 * 
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

	/**
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 */
	public Table getTable(Box box, String FORM_NO) throws SQLException {

		Transaction transaction = TransactionContext.getThread();

		return sqlRunner.getTable(transaction, box, FORM_NO);
	}
}
