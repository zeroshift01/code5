package com.biz.board;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.db.Sql;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class BoardByCode5 {

	/**
	 * @param x
	 * @throws Exception
	 */
	public static void main(String[] x) throws Exception {

		Box box = BoxContext.get();
		box.put("FIND_STR", "ABCD");
		box.put("EM", "aa@bb.com");

		Sql sql = new Sql(BoardByCode5.class);

		Table table = sql.getTable("BOARDBYCODE5_01");

		for (int i = 0; i < table.size(); i++) {
			String n = table.s("N", i);
			box.put("N", n);

			sql.executeSql("BOARDBYCODE5_02");
		}

		table = sql.getTable("BOARDBYCODE5_01");

		System.out.println(table.s("EM", 0));

		TransactionContext.get().commit();

	}
}