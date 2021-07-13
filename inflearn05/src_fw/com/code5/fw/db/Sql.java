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
	private String className = null;

	/**
	 * @param object
	 */
	public Sql(Object object) {
		String className = object.getClass().getName();
		this.className = className;
	}

	/**
	 * @param className
	 */
	public Sql(String className) {
		this.className = className;
	}

	/**
	* 
	*/
	private SqlRunner sqlRunner = SqlRunner.getSqlRunner();

	/**
	 * @param cl
	 */
	public Sql(Class<?> cl) {
		String className = cl.getName();
		this.className = className;
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
		Transaction transaction = TransactionContext.get();
		Box box = BoxContext.get();
		return executeSql(transaction, box, key);
	}

	/**
	 * @param key
	 * @param box
	 * @return
	 * @throws SQLException
	 */
	public int executeSql(String key, Box box) throws SQLException {
		Transaction transaction = TransactionContext.get();
		return executeSql(transaction, box, key);
	}

	/**
	 * @param key
	 * @return
	 */
	private String setKey(String key) {
		return this.className + "." + key;
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public Table getTable(String key) throws SQLException {
		Transaction transaction = TransactionContext.get();
		Box box = BoxContext.get();
		return getTable(transaction, box, key);
	}

	/**
	 * @param key
	 * @param box
	 * @return
	 * @throws SQLException
	 */
	public Table getTable(String key, Box box) throws SQLException {
		Transaction transaction = TransactionContext.get();
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
		System.out.println(key);
		return sqlRunner.getTable(transaction, box, key);
	}

	/**
	 * @param key
	 * @param box
	 * @return
	 * @throws SQLException
	 */
	public Table getTableByCache(String key, Box box) throws SQLException {
		Transaction transaction = TransactionContext.get();
		return getTableByCache(transaction, key, box);
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public Table getTableByCache(String key) throws SQLException {
		Transaction transaction = TransactionContext.get();
		Box box = BoxContext.get();
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
