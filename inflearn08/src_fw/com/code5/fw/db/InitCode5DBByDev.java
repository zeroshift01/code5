package com.code5.fw.db;

import com.code5.fw.data.InitYaml;
import com.code5.fw.trace.Trace;

/**
 * @author zero
 *
 */
public class InitCode5DBByDev {

	/**
	 * 
	 */
	private static Trace trace = new Trace(InitCode5DBByDev.class);

	/**
	 * 
	 */
	private static Sql sql = new Sql(InitCode5DBByDev.class);

	/**
	 * @param xx
	 * @throws Exception
	 */
	public static void main(String[] xx) throws Exception {

		InitYaml initYaml = InitYaml.get();

		trace.write("[" + initYaml.getAppName() + "]");

		InitYaml.get().setAppName(InitCode5DBByDev.class.getName());

		trace.write("[" + initYaml.getAppName() + "]");

		for (int i = 1; i <= 37; i++) {

			String x = ("" + (100 + i)).substring(1);

			String key = "INITCODE5DBBYDEV_" + x;

			sql.executeSql(key);

		}

		trace.write("ok");
	}

}
