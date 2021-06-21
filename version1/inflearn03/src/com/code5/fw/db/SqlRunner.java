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
 * @author zero
 *
 */
public class SqlRunner {

	// 싱글톤 패턴

	// Table getTable(String key)
	// int executeSql(String key)

	// 오버로딩(overloading), AOP 관점 기능 확장

	// SqlRunnerB 어쩔수 없는 내용 결합이 필요할 땐 사용 범위를 한정

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
	 * 
	 * @param transaction
	 * @param SQL
	 * @return
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
	 */
	SqlRunnerB getSqlRunnerB(Transaction transaction, String KEY) throws SQLException {

		String sql = getSQL(transaction, KEY);

		SqlRunnerB sqlRunnerB = getSqlRunnerB(sql);
		sqlRunnerB.key = KEY;

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

		SqlRunnerB sqlRunnerB = getSqlRunnerB(transaction, key);

		PreparedStatement ps = transaction.prepareStatement(sqlRunnerB.sql);

		String exeSql = sqlRunnerB.sql;
		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			String thisKey = sqlRunnerB.param.get(i);
			String thisData = box.s(thisKey);
			ps.setString(i + 1, thisData);

			// [8]
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
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public Table getTable(String key) throws SQLException {

		Transaction transaction = TransactionContext.getThread();
		Box box = BoxContext.getThread();

		return getTable(transaction, box, key);
	}

	/**
	 * @param key
	 * @param box
	 * @return
	 * @throws SQLException
	 */
	public Table getTable(String key, Box box) throws SQLException {

		Transaction transaction = TransactionContext.getThread();

		return getTable(transaction, box, key);
	}

	/**
	 * @param transaction
	 * @param box
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public int executeSql(Transaction transaction, Box box, String key) throws SQLException {

		SqlRunnerB sqlRunnerB = getSqlRunnerB(transaction, key);

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

	/**
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public int executeSql(String key) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		Box box = BoxContext.getThread();
		return executeSql(transaction, box, key);
	}

	/**
	 * @param key
	 * @param box
	 * @return
	 * @throws SQLException
	 */
	public int executeSql(String key, Box box) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		return executeSql(transaction, box, key);
	}

}
