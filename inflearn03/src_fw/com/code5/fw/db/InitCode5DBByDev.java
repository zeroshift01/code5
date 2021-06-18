package com.code5.fw.db;

import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class InitCode5DBByDev {

	public static void main(String[] xx) throws Exception {

		Sql sql = new Sql(InitCode5DBByDev.class);

		sql.executeSql("INITCODE5DBBYDEV_01");
		sql.executeSql("INITCODE5DBBYDEV_02");
		sql.executeSql("INITCODE5DBBYDEV_03");
		sql.executeSql("INITCODE5DBBYDEV_04");
		sql.executeSql("INITCODE5DBBYDEV_05");
		sql.executeSql("INITCODE5DBBYDEV_06");
		sql.executeSql("INITCODE5DBBYDEV_07");
		sql.executeSql("INITCODE5DBBYDEV_08");

		TransactionContext.get().commit();

	}

}
