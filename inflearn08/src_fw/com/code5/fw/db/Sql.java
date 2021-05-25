package com.code5.fw.db;

import java.sql.SQLException;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class Sql {

	/**
	 * 
	 */
	private SqlRunner sqlRunner = SqlRunner.getSqlRunner();

	/**
	 * 
	 */
	private String className = null;

	/**
	 * @param object
	 */
	public Sql(Object object) {
		String className = object.getClass().getName();
		this.className = className;
	}

	/**
	 * @param key
	 * @return
	 */
	private String setKey(String key) {
		return this.className + "." + key;
	}

	/**
	 *
	 */
	public int executeSql(Transaction transaction, Box box, String key) throws SQLException {
		key = setKey(key);
		return sqlRunner.executeSql(transaction, box, key);
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public int executeSql(String key) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		Box box = BoxContext.getThread();
		return executeSql(transaction, box, key);
	}

	/**
	 * @param key
	 * @param box
	 * @return
	 * @throws SQLException
	 */
	public int executeSql(String key, Box box) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		return executeSql(transaction, box, key);
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public Table getTable(String key) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		Box box = BoxContext.getThread();
		return getTable(transaction, box, key);
	}

	/**
	 * @param key
	 * @param box
	 * @return
	 * @throws SQLException
	 */
	public Table getTable(String key, Box box) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		return getTable(transaction, box, key);
	}

	/**
	 * @param transaction
	 * @param box
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public Table getTable(Transaction transaction, Box box, String key) throws SQLException {
		key = setKey(key);
		return sqlRunner.getTable(transaction, box, key);
	}

	/**
	 * @param key
	 * @param box
	 * @return
	 * @throws SQLException
	 */
	public Table getTableByCache(String key, Box box) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		return getTableByCache(transaction, key, box);
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public Table getTableByCache(String key) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		Box box = BoxContext.getThread();
		return getTableByCache(transaction, key, box);
	}

	/**
	 * @param transaction
	 * @param key
	 * @param box
	 * @return
	 * @throws SQLException
	 */
	public Table getTableByCache(Transaction transaction, String key, Box box) throws SQLException {
		key = setKey(key);
		return sqlRunner.getTableByCache(transaction, key, box);
	}

}
