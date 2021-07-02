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

	// className, SQL 을 사용하는 클래스의 패키지 이름
	// className 을 통해 SQL 파일을 찾기 쉽게 함
	// setKey()
	// com.code5.fw.db.SqlRunner_test.SQLRUNNER_TEST_01 -> SQLRUNNER_TEST_01

	// executeSql(), getTable()
	// 오버로딩, 개발자의 코드를 쉽게 해줌

	// Sql 같은 클래스를 통해 복잡한 기능 이해 없이 쉽게 사용할 수 있게 함

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
	 * @param cl
	 */
	public Sql(Class<?> cl) {
		String className = cl.getName();
		this.className = className;
	}

	/**
	 * @param className
	 */
	public Sql(String className) {
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
		return sqlRunner.getTable(transaction, box, key);
	}

}
