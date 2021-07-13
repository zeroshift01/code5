package com.biz;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.code5.fw.data.InitYaml;
import com.code5.fw.db.Sql;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class InitCode5DBByDev {

	/**
	 * 
	 */
	private static Sql sql = new Sql(InitCode5DBByDev.class);

	/**
	 * @return
	 * @throws Exception
	 */
	private static List<String> getKey() throws Exception {

		String sqlFileUrl = InitYaml.get().getWebAppDir() + "/WEB-INF/classes/com/biz/InitCode5DBByDev.sql";

		List<String> list = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sqlFileUrl)));

		while (true) {

			String str = br.readLine();

			if (str == null) {
				break;
			}

			if (str.indexOf("--[[[") >= 0) {

				String key = str.replace("--[[[", "").trim();
				list.add(key);
			}

		}
		br.close();
		return list;

	}

	/**
	 * @param xx
	 * @throws Exception
	 */
	public static void main(String[] xx) throws Exception {

		InitYaml initYaml = InitYaml.get();

		System.out.println("[" + initYaml.getAppName() + "]");

		InitYaml.get().setAppName(InitCode5DBByDev.class.getName());

		System.out.println("[" + initYaml.getAppName() + "]");

		List<String> keys = getKey();

		for (int i = 0; i < keys.size(); i++) {

			String key = keys.get(i);

			try {
				sql.executeSql(key);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		TransactionContext.get().commit();
		System.out.println("ok");
	}

}
