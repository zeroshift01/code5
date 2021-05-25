package com.code5.biz.emp.emp001;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author zero
 *
 */
public class X {
	public static void main(String[] xxx) throws Exception {

		System.out.println("[" + main("EMP001D_02") + "]");
	}

	/**
	 * @return
	 */
	public static String main(String key) {

		InputStream is = null;
		try {

			key = "com.code5.biz.emp.emp001.Emp001D.EMP001D_01";
			key = key.replaceAll("\\.", "/");
			int p = key.lastIndexOf("/");

			String sqlUrl = key.substring(0, p) + ".sql";
			String sqlKey = key.substring(p + 1);

			ClassLoader cl = X.class.getClassLoader();

			is = cl.getResourceAsStream(sqlUrl);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			StringBuffer sql = new StringBuffer();
			boolean findSql = false;
			while (true) {

				String str = br.readLine();

				if (str == null) {
					break;
				}

				if (str.indexOf("--[[[") >= 0) {

					if (str.indexOf(sqlKey) >= 0) {
						findSql = true;
						continue;
					}
				}

				if (str.indexOf("--]]]") >= 0) {
					if (findSql) {
						break;
					}

				}

				if (findSql) {
					sql.append(str + "\n");
				}

			}

			return sql.toString().trim();

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {

			if (is != null) {
				try {
					is.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}

	}

}
