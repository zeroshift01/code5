package com.code5.biz.emp;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.db.SqlRunner;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class EmpByCode5 {

	// SQL 의존문제를 해결하기 위한 방법
	// 1. SQL 과 JAVA 코드 분리 -> KEY
	// 2. 정적 SQL, 동적 SQL 장점을 흡수한 쉬운 사용 -> getTable, executeSql
	// 3. 컬랙션의 단점을 해결 -> Table

	/**
	 * @param x
	 * @throws Exception
	 */
	public static void main(String[] x) throws Exception {

		Box box = BoxContext.getThread();
		box.put("EMP_N", "N01");
		box.put("HP_N", "010-2222-3333");

		Table table = select();
		update(table);

		TransactionContext.getThread().commit();

		Table table2 = select();
		System.out.println(table2.getData("EMP_N", 0));
		System.out.println(table2.getData("HP_N", 0));

		TransactionContext.getThread().closeConnection();

	}

	/**
	 * @throws Exception
	 */
	private static Table select() throws Exception {

		Table table = SqlRunner.getSqlRunner().getTable("EMP001D_01");
		return table;

	}

	/**
	 * @throws Exception
	 */
	private static void update(Table table) throws Exception {

		SqlRunner sql = SqlRunner.getSqlRunner();

		Box box = BoxContext.getThread();

		for (int i = 0; i < table.size(); i++) {

			box.put("EMP_N", table.getData("EMP_N", i));

			int updateCnt = sql.executeSql("EMP001D_02");
			if (updateCnt != 1) {
				throw new Exception();
			}
		}

	}

}
