package com.code5.fw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author seuk
 *
 */
public class SqlRunner {

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
	 * 
	 * 
	 * @return
	 * 
	 */
	public static SqlRunner getSqlRunner() {
		return sql;
	}

	/**
	 * 
	 * @param transaction
	 * @param SQL
	 * @return
	 * 
	 */
	String getSQL(Transaction transaction, String KEY) throws SQLException {

		PreparedStatement ps = transaction.prepareStatement("SELECT SQL FROM FW_SQL WHERE KEY = ?");
		ps.setString(1, KEY);

		ResultSet rs = transaction.getResultSet(ps);

		if (!rs.next()) {
			throw new RuntimeException();
		}

		String sql = rs.getString("SQL");

		transaction.close();

		return sql;

	}

	/**
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	SqlRunnerB getSqlRunnerB(String sql) throws SQLException {

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

		SqlRunnerB sqlRunnerB = new SqlRunnerB();
		sqlRunnerB.param = param;
		sqlRunnerB.sql = sqlB.toString();
		sqlRunnerB.sqlOrg = sql;

		return sqlRunnerB;
	}

	/**
	 * 
	 * 
	 * @param sql
	 * @return
	 * 
	 *         SQL 을 분석해서 파라메터 부분을 인식하고 바인트처리를 위한 SQL 변환을 한다.
	 * 
	 *         [13]
	 */
	SqlRunnerB getSqlRunnerB(Transaction transaction, String KEY) throws SQLException {

		String sql = getSQL(transaction, KEY);

		// [14]
		SqlRunnerB sqlRunnerB = getSqlRunnerB(sql);
		sqlRunnerB.key = KEY;

		System.out.println(sqlRunnerB.sqlOrg);
		System.out.println(sqlRunnerB.sql);
		System.out.println(sqlRunnerB.key);

		return sqlRunnerB;
	}

	/**
	 * 
	 * [4]
	 * 
	 * 
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 */
	public Table getTable(String FORM_NO) throws SQLException {

		// [7]
		Transaction transaction = TransactionContext.getThread();
		Box box = BoxContext.getThread();

		return getTable(transaction, box, FORM_NO);
	}

	/**
	 * 
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 * 
	 *                      [5]
	 */
	public Table getTable(Box box, String FORM_NO) throws SQLException {

		Transaction transaction = TransactionContext.getThread();

		return getTable(transaction, box, FORM_NO);
	}

	/**
	 * [6]
	 */
	public Table getTable(Transaction transaction, Box box, String FORM_NO) throws SQLException {

		SqlRunnerB sqlRunnerB = getSqlRunnerB(transaction, FORM_NO);

		PreparedStatement ps = transaction.prepareStatement(sqlRunnerB.sql);

		String exeSql = sqlRunnerB.sql;
		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			String key = sqlRunnerB.param.get(i);
			String data = box.s(key);
			ps.setString(i + 1, data);

			// [8]
			exeSql = exeSql.replaceFirst("\\?", "'" + data + "'");
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

			// [9]
			boolean isAddRecode = table.addRecode(recode);
			if (!isAddRecode) {
				break;
			}
		}

		transaction.close();

		return table;
	}

	/**
	 * 
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 * 
	 *                      [10]
	 */
	public int executeSql(String FORM_NO) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		Box box = BoxContext.getThread();
		return executeSql(transaction, box, FORM_NO);
	}

	/**
	 * [11]
	 */
	public int executeSql(Transaction transaction, Box box, String FORM_NO) throws SQLException {

		SqlRunnerB sqlRunnerB = getSqlRunnerB(transaction, FORM_NO);
		PreparedStatement ps = transaction.prepareStatement(sqlRunnerB.sql);

		String exeSql = sqlRunnerB.sql;
		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			String key = sqlRunnerB.param.get(i);
			String data = box.s(key);
			ps.setString(i + 1, data);

			exeSql = exeSql.replaceFirst("\\?", "'" + data + "'");
		}

		System.out.println(exeSql);

		int i = ps.executeUpdate();

		System.out.println("execute cnt [" + i + "]");

		transaction.close();

		return i;
	}

}
