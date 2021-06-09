package com.code5.fw.db;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Statement;

import com.code5.fw.data.InitYaml;
import com.code5.fw.trace.Trace;

/**
 * @author zero
 *
 */
public class InitCode5DBByDev {

	private static Trace trace = new Trace(InitCode5DBByDev.class);
	private static Sql sql = new Sql(InitCode5DBByDev.class);

	public static void main(String[] xx) throws Exception {

		InitYaml.get().setAppName(InitCode5DBByDev.class.getName());

		String url = InitYaml.get().getAppRootDir() + "/doc/init_code5_db.sql";

		FileInputStream in = new FileInputStream(url);
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();

		while (true) {
			String str = br.readLine();
			if (str == null) {
				break;
			}
			sb.append(str + "\n");
		}

		in.close();

		Transaction transaction = new Transaction_SQLITE_JDBC_CODE5_DEV();
		transaction.setAutoCommitFalse();

		String[] sqls = sb.toString().split(";");
		for (int i = 0; i < sqls.length; i++) {

			String sql = sqls[i];

			if (sql == null) {
				continue;
			}

			sql = sql.trim();

			if ("".equals(sql)) {
				continue;
			}

			System.out.println(sql + ";");
			try {
				Statement st = transaction.createStatement();
				st.execute(sql);
				transaction.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		transaction.commit();

		System.out.println("FIN " + url);

	}

}
