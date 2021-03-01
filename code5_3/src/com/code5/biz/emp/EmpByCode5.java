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

	/**
	 * @param x
	 * @throws Exception
	 */
	public static void main(String[] x) throws Exception {

		Box box = BoxContext.getThread();
		box.put("EMP_N", "N01");
		box.put("HP_N", "010-2222-3333");

		select();
		update();

		select();

		TransactionContext.getThread().closeConnection();

	}

	/**
	 * @throws Exception
	 */
	private static void select() throws Exception {

		Table table = SqlRunner.getSqlRunner().getTable("EMP001D_01");

		String[] cols = table.getCols();

		for (int i = 0; i < table.size(); i++) {

			for (int j = 0; j < cols.length; j++) {

				System.out.print(table.s(cols[j], i) + "\t");
			}
			System.out.println();
		}

	}

	/**
	 * @throws Exception
	 */
	private static void update() throws Exception {

		SqlRunner.getSqlRunner().executeSql("EMP001D_02");

		TransactionContext.getThread().commit();

	}

}
