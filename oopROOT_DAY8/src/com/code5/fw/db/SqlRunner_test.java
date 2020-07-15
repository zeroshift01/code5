package com.code5.fw.db;

import com.code5.fw.web.Box;

import junit.framework.TestCase;

/**
 * @author seuk
 *
 */
public class SqlRunner_test extends TestCase {

	Transaction transaction = Transaction.getTransaction();
	Box box = Box.getThread();

	/**
	 * 
	 */
	SqlRunner sql = new SqlRunner();

	public void test_getParamForSQL3() throws Exception {
		SqlRunnerB sqlRunnerB = sql.getSqlRunnerB("SELECT * FROM TABS WHERE 1 = 2");
		assertEquals(sqlRunnerB.sql, "SELECT * FROM TABS WHERE 1 = 2");
		assertEquals(sqlRunnerB.param.size(), 0);
	}
	
	/**
	 * @throws Exception
	 */
	public void test_getParamForSQL2() throws Exception {
		SqlRunnerB sqlRunnerB = sql.getSqlRunnerB("SELECT * FROM TABS WHERE A = [A] AND B=[B] AND C = [C] AND 1 = 2");
		assertEquals(sqlRunnerB.sql, "SELECT * FROM TABS WHERE A = ? AND B=? AND C = ? AND 1 = 2");
		assertEquals(sqlRunnerB.param.get(0), "A");
	}

	/**
	 * @throws Exception
	 */
	public void test_getParamForSQL() throws Exception {
		SqlRunnerB sqlRunnerB = sql.getSqlRunnerB("SELECT * FROM TABS WHERE A = [A] AND B=[B] AND C = [C]");
		assertEquals(sqlRunnerB.sql, "SELECT * FROM TABS WHERE A = ? AND B=? AND C = ?");
		assertEquals(sqlRunnerB.param.get(0), "A");
	}
}
