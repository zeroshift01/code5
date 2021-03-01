package com.code5.fw.db;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

import junit.framework.TestCase;

/**
 * @author zero
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
		box.put("EMP_N", "N01");

		SqlRunner sql = SqlRunner.getSqlRunner();

		Table table = sql.getTable("EMP001D_01");

		Table table = sql.getTable("SQLRUNNER_TEST_01");

		assertEquals(1, table.size());

		String[] cols = table.getCols();

		assertEquals("EMP_N", cols[0]);
		assertEquals("EMP_NM", cols[1]);
		assertEquals("HP_N", cols[2]);

		assertEquals("N01", table.s("EMP_N", 0));

	}

	/**
	 * @throws Exception
	 * 
	 *                   TODO [2]
	 */
	public void test_02() throws Exception {

		Box box = BoxContext.getThread();
		box.put("EMP_N", "N01");
		box.put("HP_N", "010-2222-3333");

		SqlRunner sql = SqlRunner.getSqlRunner();

<<<<<<< HEAD
		int i = sql.executeSql("EMP001D_02");
=======
		// INSERT INTO FW_SQL VALUES ('SQLRUNNER_TEST_02', 'UPDATE EMP SET HP_N = [HP_N]
		// WHERE EMP_N =[EMP_N]');
		int i = sql.executeSql("SQLRUNNER_TEST_02");
>>>>>>> branch 'master' of https://github.com/zeroshift01/zeroshift01

		assertEquals(1, i);

	}

}
