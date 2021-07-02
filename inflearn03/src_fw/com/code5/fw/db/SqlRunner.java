package com.code5.fw.db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;

/**
 * @author zero
 *
 */
public class SqlRunner {

	// 테스트 케이스를 통해 SQL 기능을 이해
	// 이해된 SQL 기능 구현

	// 1) KEY 를 통해 SQL 구문 가져오기
	// 2) 바인딩 변수 처리를 위한 SQL 구문 해석 및 자료구조 생성
	// 3) 실행

	/**
	 * 
	 */
	private static SqlRunner sql = new SqlRunner();

	/**
	 * 
	 */
	private SqlRunner() {
	}

	/**
	 * @return
	 */
	public static SqlRunner getSqlRunner() {
		return sql;
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	SqlRunnerB getSqlRunnerBStep1(String key) throws SQLException {

		InputStream is = null;
		try {

			key = key.replaceAll("\\.", "/");
			int p = key.lastIndexOf("/");

			String sqlUrl = key.substring(0, p) + ".sql";
			String sqlKey = key.substring(p + 1);

			String[] sqlKeys = sqlKey.split(",");
			String findSqlKey = sqlKeys[0];

			ClassLoader cl = SqlRunner.class.getClassLoader();

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

					if (str.indexOf(findSqlKey) >= 0) {
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

			String sqlOrg = sql.toString();
			sqlOrg = sqlOrg.trim();
			if (sqlOrg.endsWith(";")) {
				sqlOrg = sqlOrg.substring(0, sqlOrg.length() - 1);
			}

			SqlRunnerB sqlRunnerB = new SqlRunnerB();
			sqlRunnerB.sqlOrg = sqlOrg;
			sqlRunnerB.key = key;

			return sqlRunnerB;

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SQLException(ex.getMessage());
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

	/**
	 * @param sqlRunnerB
	 * @return
	 * @throws SQLException
	 */
	SqlRunnerB getSqlRunnerBStep2(SqlRunnerB sqlRunnerB) throws SQLException {

		String sql = sqlRunnerB.sqlOrg;

		ArrayList<String> param = new ArrayList<String>();

		StringBuffer sqlB = new StringBuffer();

		int fromIndex = -1;
		for (int i = 0; i < 1000; i++) {

			int sp = sql.indexOf("[", fromIndex);
			if (sp == -1) {

				sqlB.append(sql.substring(fromIndex + 1));

				break;
			}

			int ep = sql.indexOf("]", sp + 1);

			param.add(sql.substring(sp + 1, ep));
			sqlB.append(sql.substring(fromIndex + 1, sp) + "?");

			fromIndex = ep;
		}

		sqlRunnerB.param = param;
		sqlRunnerB.sql = sqlB.toString();

		return sqlRunnerB;
	}

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	SqlRunnerB getSqlRunnerB(String key) throws SQLException {

		SqlRunnerB sqlRunnerB = getSqlRunnerBStep1(key);
		sqlRunnerB = getSqlRunnerBStep2(sqlRunnerB);

		System.out.println(sqlRunnerB.sqlOrg);
		System.out.println(sqlRunnerB.sql);
		System.out.println(sqlRunnerB.key);

		return sqlRunnerB;
	}

	/**
	 * @param transaction
	 * @param box
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public Table getTable(Transaction transaction, Box box, String key) throws SQLException {

		SqlRunnerB sqlRunnerB = getSqlRunnerB(key);

		PreparedStatement ps = transaction.prepareStatement(sqlRunnerB.sql);

		String exeSql = sqlRunnerB.sql;
		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			String thisKey = sqlRunnerB.param.get(i);
			String thisData = box.s(thisKey);
			ps.setString(i + 1, thisData);

			exeSql = exeSql.replaceFirst("\\?", "'" + thisData + "'");
		}

		System.out.println(exeSql);

		ResultSet rs = transaction.getResultSet(ps);

		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		String[] cols = new String[columnCount];

		for (int i = 0; i < cols.length; i++) {
			cols[i] = metaData.getColumnName(i + 1);
		}

		Table table = new Table(cols);

		while (rs.next()) {

			String[] recode = new String[columnCount];

			for (int i = 0; i < cols.length; i++) {
				recode[i] = rs.getString(cols[i]);
			}

			boolean isAddRecode = table.addRecode(recode);
			if (!isAddRecode) {
				break;
			}
		}

		transaction.close();

		return table;
	}

	/**
	 * @param transaction
	 * @param box
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public int executeSql(Transaction transaction, Box box, String key) throws SQLException {

		SqlRunnerB sqlRunnerB = getSqlRunnerB(key);

		transaction.setAutoCommitFalse();

		PreparedStatement ps = transaction.prepareStatement(sqlRunnerB.sql);

		String exeSql = sqlRunnerB.sql;
		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			String thisKey = sqlRunnerB.param.get(i);
			String thisData = box.s(thisKey);
			ps.setString(i + 1, thisData);

			exeSql = exeSql.replaceFirst("\\?", "'" + thisData + "'");
		}

		System.out.println(exeSql);

		int i = ps.executeUpdate();

		System.out.println("execute cnt [" + i + "]");

		transaction.close();

		return i;
	}

}
