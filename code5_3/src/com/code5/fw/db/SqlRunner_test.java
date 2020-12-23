package com.code5.fw.db;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

import junit.framework.TestCase;

/**
 * @author seuk
 *
 */
public class SqlRunner_test extends TestCase {

	@Override
	protected void tearDown() throws Exception {
		TransactionContext.getThread().commit();
	}

	/**
	 * @throws Exception
	 * 
	 *                   TODO [1]
	 */
	public void test_01() throws Exception {

		Box box = BoxContext.getThread();
		box.put("EMP_NM", "ABC");

		SqlRunner sql = SqlRunner.getSqlRunner();

		// INSERT INTO FW_SQL VALUES ('SQLRUNNER_TEST_01', 'SELECT EMP_N, EMP_NM, DEPT_N FROM EMP WHERE EMP_NM = [EMP_NM] ORDER BY EMP_N');
		Table table = sql.getTable("SQLRUNNER_TEST_01");

		assertEquals(2, table.size());

		String[] cols = table.getCols();

		assertEquals("EMP_N", cols[0]);
		assertEquals("EMP_NM", cols[1]);
		assertEquals("DEPT_N", cols[2]);

		assertEquals("N0001", table.s("EMP_N", 0));
		assertEquals("N0003", table.s("EMP_N", 1));

	}

	/**
	 * @throws Exception
	 * 
	 *                   TODO [2]
	 */
	public void test_02() throws Exception {

		Box box = BoxContext.getThread();
		box.put("EMP_N", "N0003");
		box.put("HP_N", "010-2222-3333");

		SqlRunner sql = SqlRunner.getSqlRunner();

		// INSERT INTO FW_SQL VALUES ('SQLRUNNER_TEST_02', 'UPDATE EMP SET HP_N = [HP_N] WHERE EMP_N =[EMP_N]');
		int i = sql.executeSql("SQLRUNNER_TEST_02");

		assertEquals(1, i);

	}

}
