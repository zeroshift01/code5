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

	public void test_01() throws Exception {

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
	 */
	public void test_02() throws Exception {

		Box box = BoxContext.getThread();
		box.put("KEY", "SQLRUNNER_TEST_02_1");
		box.put("SQL", "SELECT * FROM FW_VIEW");

		SqlRunner sql = SqlRunner.getSqlRunner();

		sql.executeSql("SQLRUNNER_TEST_02");

		TransactionContext.getThread().commit();

	}
}
