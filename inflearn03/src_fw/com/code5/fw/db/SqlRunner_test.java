package com.code5.fw.db;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class SqlRunner_test {

	/**
	 * @throws Exception
	 */
	@Test
	public void test_01() throws Exception {

		// 공통기능1
		Box box = BoxContext.get();
		box.put("FIND_STR", "ABCD");

		// 공통기능2
		Transaction transaction = TransactionContext.get();

		SqlRunner sql = SqlRunner.getSqlRunner();

		// JAVA 코드와 SQL 분리
		String key = "com.code5.fw.db.SqlRunner_test.SQLRUNNER_TEST_01";

		// SQL 기능 실행
		Table table = sql.getTable(transaction, box, key);

		// SQL 결과 사용
		assertEquals(2, table.size());

		String[] cols = table.getCols();

		assertEquals("TXT", cols[2]);
		assertEquals("EM", cols[3]);

		assertEquals("ABCD", table.s("TXT", 0));
		assertEquals("ABCD", table.s("TXT", 1));

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void test_02() throws Exception {

		Box box = BoxContext.get();
		box.put("N", "1");
		box.put("EM", "aa@bb.com");

		Transaction transaction = TransactionContext.get();

		SqlRunner sql = SqlRunner.getSqlRunner();

		String key = "com.code5.fw.db.SqlRunner_test.SQLRUNNER_TEST_02";
		int i = sql.executeSql(transaction, box, key);

		assertEquals(1, i);

		TransactionContext.commit();

	}

}
