package com.code5.fw.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.code5.fw.data.Box;
import com.code5.fw.data.DateTime;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.data.TableRecodeBase;
import com.code5.fw.security.CryptPin;
import com.code5.fw.security.DataCrypt;
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
	SqlRunnerB getSqlRunnerBStep1(Transaction transaction, String key) throws SQLException {

		PreparedStatement ps = transaction.prepareStatement("SELECT * FROM FW_SQL WHERE KEY = ?");
		ps.setString(1, key);

		ResultSet rs = transaction.getResultSet(ps);

		if (!rs.next()) {
			throw new RuntimeException("not SQL for KEY [" + key + "]");
		}

		String sql = rs.getString("SQL");
		int timeOut = rs.getInt("TIME_OUT");
		if (timeOut <= 0) {
			timeOut = -1;
		}

		SqlRunnerB sqlRunnerB = new SqlRunnerB();
		sqlRunnerB.sqlOrg = sql;
		sqlRunnerB.key = key;
		sqlRunnerB.timeOut = timeOut;

		return sqlRunnerB;

	}

	/**
	 * @param transaction
	 * @return
	 */
	SqlRunnerB getSqlRunnerBStep2(Transaction transaction, Box box, SqlRunnerB sqlRunnerB) throws SQLException {

		String sql = sqlRunnerB.sqlOrg;

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
			SqlRunnerB thisSqlRunnerB = getSqlRunnerBStep1(transaction, key);
			String addSql = thisSqlRunnerB.sqlOrg;

			sqlB.append(sql.substring(fromIndex + 2, sp));
			sqlB.append(addSql);

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

		sqlRunnerB.param = param;
		sqlRunnerB.sql = sqlB.toString();

		return sqlRunnerB;
	}

	/**
	 * 
	 * 
	 * @param sql
	 * @return
	 * 
	 */
	SqlRunnerB getSqlRunnerB(Transaction transaction, Box box, String key) throws SQLException {

		SqlRunnerB sqlRunnerB = cacheSqlMap.get(key);
		if (sqlRunnerB != null) {
			return sqlRunnerB;
		}

		sqlRunnerB = getSqlRunnerBStep1(transaction, key);

		sqlRunnerB = getSqlRunnerBStep2(transaction, box, sqlRunnerB);

		cacheSqlMap.put(key, sqlRunnerB);

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

		if (sqlRunnerB.timeOut != -1) {
			ps.setQueryTimeout(sqlRunnerB.timeOut);
		}

		String exeSql = sqlRunnerB.sql;
		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			SqlRunnerParamB p = sqlRunnerB.param.get(i);
			String data = getDataByParam(p, box);

			ps.setString(i + 1, data);

			exeSql = exeSql.replaceFirst("\\?", "'" + data + "'");
		}
		trace.write(exeSql);

		ResultSet rs = transaction.getResultSet(ps);

		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		String[] cols = new String[columnCount];
		String[] colsForResultSet = new String[columnCount];
		SqlRunnerParamB[] paramBs = new SqlRunnerParamB[columnCount];

		for (int i = 0; i < cols.length; i++) {
			String x = metaData.getColumnName(i + 1);
			paramBs[i] = paramParsing(x);
			cols[i] = paramBs[i].key;
			colsForResultSet[i] = paramBs[i].keyOrg;
		}

		Table table = new TableRecodeBase(cols);

		while (rs.next()) {

			String[] recode = new String[columnCount];

			for (int i = 0; i < cols.length; i++) {

				String data = rs.getString(colsForResultSet[i]);
				data = getDataByParam(paramBs[i], box, data);
				recode[i] = data;
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

		if (sqlRunnerB.timeOut != -1) {
			ps.setQueryTimeout(sqlRunnerB.timeOut);
		}

		String exeSql = sqlRunnerB.sql;
		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			SqlRunnerParamB p = sqlRunnerB.param.get(i);
			String data = getDataByParam(p, box);

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
	private ConcurrentHashMap<String, SqlRunnerB> cacheSqlMap = new ConcurrentHashMap<String, SqlRunnerB>();

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

			SqlRunnerParamB p = sqlRunnerB.param.get(i);
			String data = getDataByParam(p, box);
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

	/**
	 * @param p
	 * @param box
	 * @return
	 */
	String getDataByParam(SqlRunnerParamB p, Box box) {

		String data = getDataByParamStep1(p, box);

		if ("".equals(data)) {
			return data;
		}

		data = getDataByParamStep2(p, box, data);

		if ("".equals(data)) {
			return data;
		}

		data = getDataByParamStep3(p, box, data);

		return data;
	}

	/**
	 * @param p
	 * @param box
	 * @param data
	 * @return
	 */
	String getDataByParam(SqlRunnerParamB p, Box box, String data) {

		data = getDataByParamStep2(p, box, data);

		if ("".equals(data)) {
			return data;
		}

		data = getDataByParamStep3(p, box, data);

		return data;
	}

	/**
	 * @param p
	 * @param box
	 * @param data
	 * @return
	 */
	String getDataByParamStep3(SqlRunnerParamB p, Box box, String data) {

		if (p.isPrnHpN) {
			return data + "휴대폰형식화";
		}

		if (p.isPrnD) {
			return data + "날짜형식화";
		}

		if (p.isPrnDTM) {
			return data + "DTM형식화";
		}

		return data;
	}

	/**
	 * @param p
	 * @param box
	 * @param data
	 * @return
	 */
	String getDataByParamStep2(SqlRunnerParamB p, Box box, String data) {

		try {

			if (p.isEnc) {

				String cacheKey = "getDataByParamStep2_enc" + data.hashCode();
				String ret = box.s(cacheKey);
				if (!"".equals(ret)) {
					return ret;
				}

				DataCrypt dataCrypt = DataCrypt.getDataCrypt("SDB");
				ret = dataCrypt.encrypt(data);
				box.put(cacheKey, ret);
				return ret;

			}

			if (p.isDec) {

				String cacheKey = "getDataByParamStep2_dec" + data.hashCode();
				String ret = box.s(cacheKey);
				if (!"".equals(ret)) {
					return ret;
				}

				DataCrypt dataCrypt = DataCrypt.getDataCrypt("SDB");
				ret = dataCrypt.decrypt(data);
				box.put(cacheKey, ret);
				return ret;

			}

			if (p.isPin) {
				String salt = box.s(p.add1);
				return CryptPin.cryptPin(data, salt);
			}

			return data;

		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * @param p
	 * @param box
	 * @return
	 */
	String getDataByParamStep1(SqlRunnerParamB p, Box box) {

		String key = p.key;
		String data = null;

		if (p.isGetBox) {

			Box thisBox = BoxContext.getThread();
			if (thisBox == null) {
				return "";
			}

			return thisBox.s(key);

		}

		if (p.isGetSessionB) {

			Box thisBox = BoxContext.getThread();
			if (thisBox == null) {
				return "";
			}

			SessionB user = thisBox.getSessionB();
			if (user == null) {
				return "";
			}

			if ("ID".equals(key)) {
				return user.getId();
			}

			if ("IP".equals(key)) {
				return thisBox.s(Box.KEY_REMOTE_ADDR);
			}

			if ("AUTH".equals(key)) {
				return user.getAuth();
			}
		}

		if (p.isGetSysdtm) {

			data = DateTime.getThisDTM();

			String x1 = p.add1;
			String x2 = p.add2;

			if (x1 == null) {
				return data;

			}

			if ("D".equals(x1)) {

				if (x2 == null) {
					return "";
				}

				return data + " 계산된 결과 " + x1 + " " + x2;
			}

			if ("S".equals(x1)) {

				if (x2 == null) {
					return "";
				}

				return data + " 계산된 결과 " + x1 + " " + x2;
			}

		}

		return box.s(key);
	}
}
