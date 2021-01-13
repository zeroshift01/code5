package com.code5.fw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.code5.fw.data.Box;
import com.code5.fw.data.Table;
import com.code5.fw.data.TableRecodeBase;
import com.code5.fw.trace.Trace;
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
	private Trace trace = new Trace(this);

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
	String getSqlRunnerB_1(Transaction transaction, String KEY) throws SQLException {

		String sql = cacheSqlMap.get(KEY);
		if (sql != null) {
			return sql;
		}

		PreparedStatement ps = transaction.prepareStatement("SELECT SQL FROM FW_SQL WHERE KEY = ?");
		ps.setString(1, KEY);

		ResultSet rs = transaction.getResultSet(ps);

		if (!rs.next()) {
			throw new RuntimeException();
		}

		sql = rs.getString("SQL");

		cacheSqlMap.put(KEY, sql);

		return sql;

	}

	/**
	 * @param transaction
	 * @return
	 */
	SqlRunnerB getSqlRunnerB_2(Transaction transaction, Box box, String sql) throws SQLException {

		StringBuffer sqlB = new StringBuffer();

		int fromIndex = -2;

		for (int i = 0; i < 1000; i++) {

			int sp = sql.indexOf("[[", fromIndex);
			if (sp == -1) {
				sqlB.append(sql.substring(fromIndex + 2));
				break;
			}

			int ep = sql.indexOf("]]", sp + 1);

			if (ep == -1) {
				throw new SQLException();
			}

			String key = sql.substring(sp + 2, ep).trim();
			String x1 = getSqlRunnerB_1(transaction, key);

			sqlB.append(sql.substring(fromIndex + 2, sp));
			sqlB.append(x1);

			fromIndex = ep;
		}

		sql = sqlB.toString();
		sqlB = new StringBuffer();
		fromIndex = -2;

		for (int i = 0; i < 1000; i++) {

			int sp = sql.indexOf("[~", fromIndex);
			if (sp == -1) {
				sqlB.append(sql.substring(fromIndex + 2));
				break;
			}

			int ep = sql.indexOf("~]", sp + 1);

			if (ep == -1) {
				throw new SQLException();
			}

			String x1 = sql.substring(sp + 2, ep);

			String x2 = ifParsing(x1, box);

			sqlB.append(sql.substring(fromIndex + 2, sp));
			sqlB.append(x2);

			fromIndex = ep;
		}

		sql = sqlB.toString();
		sqlB = new StringBuffer();
		fromIndex = -1;

		ArrayList<SqlRunnerParamB> param = new ArrayList<SqlRunnerParamB>();

		for (int i = 0; i < 1000; i++) {

			int sp = sql.indexOf("[", fromIndex);
			if (sp == -1) {

				sqlB.append(sql.substring(fromIndex + 1));

				break;
			}

			int ep = sql.indexOf("]", sp + 1);

			if (ep == -1) {
				throw new SQLException();
			}

			String key = sql.substring(sp + 1, ep);

			SqlRunnerParamB p = paramParsing(key);

			param.add(p);

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
	 */
	SqlRunnerB getSqlRunnerB(Transaction transaction, Box box, String KEY) throws SQLException {

		String sql = getSqlRunnerB_1(transaction, KEY);

		SqlRunnerB sqlRunnerB = getSqlRunnerB_2(transaction, box, sql);
		sqlRunnerB.key = KEY;

		return sqlRunnerB;
	}

	/**
	 * @param transaction
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 * 
	 */
	public Table getTable(Transaction transaction, Box box, String FORM_NO) throws SQLException {

		SqlRunnerB sqlRunnerB = getSqlRunnerB(transaction, box, FORM_NO);

		trace.write(sqlRunnerB.key);
		trace.write(sqlRunnerB.sql);

		PreparedStatement ps = transaction.prepareStatement(sqlRunnerB.sql);

		String exeSql = sqlRunnerB.sql;
		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			String key = sqlRunnerB.param.get(i).key;
			String data = box.s(key);
			ps.setString(i + 1, data);

			exeSql = exeSql.replaceFirst("\\?", "'" + data + "'");
		}

		ResultSet rs = transaction.getResultSet(ps);

		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		String[] cols = new String[columnCount];

		for (int i = 0; i < cols.length; i++) {
			cols[i] = metaData.getColumnName(i + 1);
		}

		Table table = new TableRecodeBase(cols);

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
	 * 
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 * 
	 */
	public Table getTable(String FORM_NO) throws SQLException {

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
	 */
	public Table getTable(String FORM_NO, Box box) throws SQLException {

		Transaction transaction = TransactionContext.getThread();

		return getTable(transaction, box, FORM_NO);
	}

	/**
	 * @param transaction
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 * 
	 */
	public int executeSql(Transaction transaction, Box box, String FORM_NO) throws SQLException {

		SqlRunnerB sqlRunnerB = getSqlRunnerB(transaction, box, FORM_NO);
		trace.write(sqlRunnerB.key);
		trace.write(sqlRunnerB.sql);
		PreparedStatement ps = transaction.prepareStatement(sqlRunnerB.sql);

		String exeSql = sqlRunnerB.sql;
		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			String key = sqlRunnerB.param.get(i).key;
			String data = box.s(key);
			ps.setString(i + 1, data);

			exeSql = exeSql.replaceFirst("\\?", "'" + data + "'");
		}

		trace.write(exeSql);

		int i = ps.executeUpdate();

		trace.write("execute cnt [" + i + "]");

		transaction.close();

		return i;
	}

	/**
	 * 
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 * 
	 */
	public int executeSql(String FORM_NO) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		Box box = BoxContext.getThread();
		return executeSql(transaction, box, FORM_NO);
	}

	/**
	 * @param FORM_NO
	 * @param box
	 * @return
	 * @throws SQLException
	 * 
	 */
	public int executeSql(String FORM_NO, Box box) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		return executeSql(transaction, box, FORM_NO);
	}

	/**
	 * 
	 */
	private ConcurrentHashMap<String, Table> cacheTableMap = new ConcurrentHashMap<String, Table>();

	/**
	 * 
	 */
	private ConcurrentHashMap<String, String> cacheSqlMap = new ConcurrentHashMap<String, String>();

	/**
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 */
	public Table getTableByCache(String FORM_NO, Box box) throws SQLException {
		Transaction transaction = TransactionContext.getThread();
		return getTableByCache(transaction, FORM_NO, box);
	}

	/**
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 */
	public Table getTableByCache(String FORM_NO) throws SQLException {

		Transaction transaction = TransactionContext.getThread();
		Box box = BoxContext.getThread();
		return getTableByCache(transaction, FORM_NO, box);
	}

	/**
	 * @param transaction
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 */
	public Table getTableByCache(Transaction transaction, String FORM_NO, Box box) throws SQLException {

		StringBuffer cashKeyB = new StringBuffer();
		SqlRunnerB sqlRunnerB = getSqlRunnerB(transaction, box, FORM_NO);

		cashKeyB.append(FORM_NO + "|");

		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			String key = sqlRunnerB.param.get(i).key;
			String data = box.s(key);

			cashKeyB.append(data + "|");
		}

		String cashKey = cashKeyB.toString();
		Table table = cacheTableMap.get(cashKey);
		if (table != null) {
			return table;
		}

		table = getTable(transaction, box, FORM_NO);
		cacheTableMap.put(cashKey, table);
		return table;

	}

	/**
	 * 
	 */
	public void reload() {
		cacheSqlMap.clear();
		cacheTableMap.clear();
	}

	/**
	 * @param sql
	 * @param box
	 * @return
	 * @throws Exception
	 */
	String ifParsing(String sql, Box box) {

		String[] xxx = sql.split("\\^");

		String x1 = xxx[0].trim();
		String x2 = xxx[1].trim();
		String x3 = xxx[2].trim();

		String data = box.s(x1);

		if ("IS_NULL".equals(x2)) {

			if ("".equals(data)) {
				return x3;
			}

			return "";
		}

		if ("IS_NOT_NULL".equals(x2)) {

			if (!"".equals(data)) {
				return x3;
			}

			return "";
		}

		if (x2.equals(data)) {
			return x3;
		}

		return "";

	}

	/**
	 * @param key
	 * @return
	 */
	SqlRunnerParamB paramParsing(String key) {

		SqlRunnerParamB p = new SqlRunnerParamB();
		p.keyOrg = key;
		p.key = key;

		if (key.startsWith("SYSDTM.")) {

			p.isGetSysdtm = true;

			String x = key.substring("SYSDTM.".length());

			String[] xx = x.split(",");

			String x0 = xx[0].trim();
			String x1 = null;
			String x2 = null;

			if (xx.length == 3) {

				x1 = xx[1].trim();
				x2 = xx[2].trim();

			} else if (xx.length == 2) {

				x1 = xx[1].trim();
			}

			p.key = x0;
			p.add1 = x1;
			p.add2 = x2;

		} else if (key.startsWith("SESSIONB.")) {

			p.isGetSessionB = true;

			key = key.substring("SESSIONB.".length());
			p.key = key;

		} else if (key.startsWith("BOX.")) {

			p.isGetBox = true;

			key = key.substring("BOX.".length());
			p.key = key;

		}

		key = p.key;

		if (key.startsWith("ENC__")) {
			p.isEnc = true;
			p.key = key.substring("ENC__".length());
		} else if (key.startsWith("DEC__")) {
			p.isDec = true;
			p.key = key.substring("DEC__".length());
		} else if (key.startsWith("PIN__")) {

			p.isPin = true;

			String x = key.substring("PIN__".length());
			String[] xx = x.split(",");

			String x0 = xx[0].trim();
			String x1 = xx[1].trim();

			p.key = x0;
			p.add1 = x1;
		}

		key = p.key;

		if (key.endsWith("__PRN_HP_N")) {
			p.isPrnHpN = true;
			p.key = key.substring(0, key.length() - "__PRN_HP_N".length());
		} else if (key.endsWith("__PRN_DTM")) {
			p.isPrnDTM = true;
			p.key = key.substring(0, key.length() - "__PRN_DTM".length());
		} else if (key.endsWith("__PRN_D")) {
			p.isPrnD = true;
			p.key = key.substring(0, key.length() - "__PRN_D".length());
		}

		return p;
	}
}
