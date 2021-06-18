package com.biz;

import com.code5.fw.db.Sql;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class InitCode5DBByDev {

	public static void main(String[] xx) throws Exception {

		Sql sql = new Sql(InitCode5DBByDev.class);

		for (int i = 1; i <= 7; i++) {

			String x = ("" + (100 + i)).substring(1);

			String key = "INITCODE5DBBYDEV_" + x;

			try {
				sql.executeSql(key);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		TransactionContext.get().commit();

	}

}
