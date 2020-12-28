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
	 *                   
	 */
	public void test_01() throws Exception {

		Box box = BoxContext.getThread();
		box.put("KEY", "THIS_KEY");

		SqlRunner sql = SqlRunner.getSqlRunner();

		Table table = sql.getTable("SQLRUNNER_TEST_01");

		String[] cols = table.getCols();
		for (int i = 0; i < table.length(); i++) {

			for (int j = 0; j < cols.length; j++) {

				System.out.println(table.s(cols[j], i));

			}

		}

	}

	/**
	 * @throws Exception
	 * 
	 */
	public void test_02() throws Exception {

		Box box = BoxContext.getThread();
		box.put("KEY", "THIS_KEY");
		box.put("SQL", "THIS_SQL");

		SqlRunner sql = SqlRunner.getSqlRunner();

		if (sql.executeSql("SQLRUNNER_TEST_02_2") == 0) {
			sql.executeSql("SQLRUNNER_TEST_02_1");
		}

	}

}
