package com.code5.fw.db;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.code5.fw.data.Box;
import com.code5.fw.data.DateTime;
import com.code5.fw.data.InitYaml;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.data.TableRecodeBase;
import com.code5.fw.security.CryptPin;
import com.code5.fw.security.DataCrypt;
import com.code5.fw.trace.Trace;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.Reload;

/**
 * @author zero
 *
 */
public class SqlRunner implements Reload {

	/**
	 * 
	 */
	private boolean isCache = InitYaml.get().isCache();

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
	SqlRunner() {
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
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	SqlRunnerB getSqlRunnerBStep1(String key) throws SQLException {

		InputStream is = null;
		try {

			key = key.replaceAll("\\.", "/");
			int p = key.lastIndexOf("/");

			if (p == -1) {
				throw new SQLException(key + " 를 확인해주세요.");
			}

			String sqlUrl = key.substring(0, p) + ".sql";
			String findSqlKey = key.substring(p + 1);

			ClassLoader cl = SqlRunner.class.getClassLoader();

			is = cl.getResourceAsStream(sqlUrl);
			if (is == null) {
				throw new SQLException(key + " 가 있는 Sql 파일을 찾을 수 없습니다.");
			}
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			StringBuffer sql = new StringBuffer();
			boolean findSql = false;
			String sqlKey = null;
			while (true) {

				String str = br.readLine();

				if (str == null) {
					break;
				}

				if (str.indexOf("--[[[") >= 0) {

					if (str.indexOf(findSqlKey) >= 0) {
						findSql = true;
						sqlKey = str;
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

			if (!findSql) {
				throw new SQLException("not found sql [" + findSqlKey + "]");
			}

			String sqlOrg = sql.toString();
			sqlOrg = sqlOrg.trim();
			if (sqlOrg.endsWith(";")) {
				sqlOrg = sqlOrg.substring(0, sqlOrg.length() - 1);
			}

			SqlRunnerB sqlRunnerB = new SqlRunnerB();
			sqlRunnerB.key = key;
			sqlRunnerB.sqlOrg = sqlOrg;
			sqlRunnerB.sqlKey = sqlKey;
			sqlRunnerB.timeOut = -1;
			sqlRunnerB.limit = -1;

			try {

				String[] sqlKeys = sqlKey.split(",");

				for (int i = 1; i < sqlKeys.length; i++) {
					String[] x = sqlKeys[i].split("=");
					String x1 = x[0].trim();
					String x2 = x[1].trim();

					if ("TIMEOUT".equals(x1)) {
						sqlRunnerB.timeOut = Integer.parseInt(x2);
						continue;
					}

					if ("LIMIT".equals(x1)) {
						sqlRunnerB.limit = Integer.parseInt(x2);
						continue;
					}
				}
			} catch (Exception ex) {
				trace.writeErr(ex);
			}

			return sqlRunnerB;

		} catch (Exception ex) {
			trace.writeErr(ex);
			throw new SQLException(ex.getMessage());
		} finally {

			if (is != null) {
				try {
					is.close();
				} catch (Exception ex) {
					trace.writeErr(ex);
				}
			}

		}

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
				trace.writeErr(sqlRunnerB.sqlOrg);
				throw new SQLException("sql 구문을 확인해주세요");
			}

			String key = sql.substring(sp + 2, ep).trim();
			SqlRunnerB thisSqlRunnerB = getSqlRunnerBStep1(key);
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
			if (isCache) {
				return sqlRunnerB;
			}
		}

		sqlRunnerB = getSqlRunnerBStep1(key);

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
	Table getTable(Transaction transaction, Box box, String FORM_NO) throws SQLException {

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
		if (sqlRunnerB.limit > 0) {
			table.setLimit(sqlRunnerB.limit);
		}

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
	 * @param transaction
	 * @param box
	 * @param FORM_NO
	 * @return
	 * @throws SQLException
	 * 
	 */
	int executeSql(Transaction transaction, Box box, String FORM_NO) throws SQLException {

		SqlRunnerB sqlRunnerB = getSqlRunnerB(transaction, box, FORM_NO);
		trace.write(sqlRunnerB.key);
		trace.write(sqlRunnerB.sql);

		transaction.setAutoCommitFalse();

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
	 * @param transaction
	 * @param key
	 * @param box
	 * @return
	 * @throws SQLException
	 */
	Table getTableByCache(Transaction transaction, String key, Box box) throws SQLException {

		StringBuffer cashKeyB = new StringBuffer();
		SqlRunnerB sqlRunnerB = getSqlRunnerB(transaction, box, key);

		cashKeyB.append(key + "|");

		for (int i = 0; i < sqlRunnerB.param.size(); i++) {

			SqlRunnerParamB p = sqlRunnerB.param.get(i);
			String data = getDataByParam(p, box);
			cashKeyB.append(data + "|");
		}

		String cashKey = cashKeyB.toString();
		Table table = cacheTableMap.get(cashKey);
		if (table != null) {
			if (isCache) {
				return table;
			}
		}

		table = getTable(transaction, box, key);
		cacheTableMap.put(cashKey, table);
		return table;

	}

	/**
	 * 
	 */
	private ConcurrentHashMap<String, SqlRunnerB> cacheSqlMap = new ConcurrentHashMap<String, SqlRunnerB>();

	/**
	 * 
	 */
	private ConcurrentHashMap<String, Table> cacheTableMap = new ConcurrentHashMap<String, Table>();

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
	 * @param b
	 * @return
	 * @throws Exception
	 */
	private SqlRunnerParamB setDataWhich(SqlRunnerParamB b) throws Exception {

		String key = b.key;

		if (key.startsWith("SYSDTM.")) {

			b.isGetSysdtm = true;

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

			b.key = x0;
			b.add1 = x1;
			b.add2 = x2;
			return b;

		}

		if (key.startsWith("SESSIONB.")) {

			b.isGetSessionB = true;

			key = key.substring("SESSIONB.".length());
			b.key = key;
			return b;
		}

		if (key.startsWith("BOX.")) {

			b.isGetBox = true;

			key = key.substring("BOX.".length());
			b.key = key;
			return b;
		}

		return b;

	}

	/**
	 * @param SqlRunnerParamB
	 * @return
	 */
	private SqlRunnerParamB setPrefixRule(SqlRunnerParamB b) throws Exception {

		String key = b.key;
		SqlRunnerParamConvertB p = b.prefix;

		if (key.endsWith("PARAM__")) {
			p.isParam = true;
			b.key = key.substring(0, key.length() - "PARAM__".length());
			return b;
		}

		if (key.startsWith("ENC__")) {

			p.isEnc = true;
			b.key = key.substring("ENC__".length());
			return b;

		}

		if (key.startsWith("E__")) {

			p.isEnc = true;
			b.key = key.substring("E__".length());
			return b;

		}

		if (key.startsWith("DEC__")) {

			p.isDec = true;
			b.key = key.substring("DEC__".length());
			return b;

		}

		if (key.startsWith("D__")) {

			p.isDec = true;
			b.key = key.substring("D__".length());
			return b;

		}

		if (key.startsWith("PIN__")) {

			String x = key.substring("PIN__".length());
			String[] xx = x.split(",");

			String x0 = xx[0].trim();
			String x1 = xx[1].trim();

			p.isPin = true;
			b.key = x0;
			b.add1 = x1;
			return b;
		}

		if (key.startsWith("TOENC__")) {

			String x = key.substring("TOENC__".length());
			String[] xx = x.split(",");

			String x0 = xx[0].trim();
			String x1 = xx[1].trim();

			p.isToken = true;
			b.key = x0;
			b.add1 = x1;
			return b;

		}

		if (key.startsWith("TODEC__")) {

			String x = key.substring("TODEC__".length());
			String[] xx = x.split(",");

			String x0 = xx[0].trim();
			String x1 = xx[1].trim();

			p.isTokenDec = true;
			b.key = x0;
			b.add1 = x1;
			return b;
		}

		if (key.endsWith("PRN_HP_N__")) {
			p.isPrnHpN = true;
			b.key = key.substring(0, key.length() - "PRN_HP_N__".length());
			return b;
		}

		if (key.endsWith("PRN_DTM__")) {
			p.isPrnDTM = true;
			b.key = key.substring(0, key.length() - "PRN_DTM__".length());
			return b;
		}

		if (key.endsWith("PRN_D__")) {
			p.isPrnD = true;
			b.key = key.substring(0, key.length() - "PRN_D__".length());
			return b;
		}

		if (key.endsWith("PARAM__")) {
			p.isParam = true;
			b.key = key.substring(0, key.length() - "PARAM__".length());
			return b;
		}

		if (key.endsWith("PRN_HP_N__")) {
			p.isPrnHpN = true;
			b.key = key.substring(0, key.length() - "PRN_HP_N__".length());
			return b;
		}

		if (key.endsWith("PRN_DTM__")) {
			p.isPrnDTM = true;
			b.key = key.substring(0, key.length() - "PRN_DTM__".length());
			return b;
		}

		if (key.endsWith("PRN_D__")) {
			p.isPrnD = true;
			b.key = key.substring(0, key.length() - "PRN_D__".length());
			return b;
		}

		return b;

	}

	/**
	 * @param SqlRunnerParamB
	 * @return
	 */
	private SqlRunnerParamB setSuffixRule(SqlRunnerParamB b) throws Exception {

		String key = b.key;
		SqlRunnerParamConvertB p = b.suffix;

		if (key.endsWith("__PARAM")) {
			p.isParam = true;
			b.key = key.substring(0, key.length() - "__PARAM".length());
			return b;
		}

		if (key.startsWith("__ENC")) {

			p.isEnc = true;
			b.key = key.substring("__ENC".length());
			return b;

		}

		if (key.startsWith("__E")) {

			p.isEnc = true;
			b.key = key.substring("__E".length());
			return b;

		}

		if (key.startsWith("__DEC")) {

			p.isDec = true;
			b.key = key.substring("__DEC".length());
			return b;

		}

		if (key.startsWith("__D")) {

			p.isDec = true;
			b.key = key.substring("__D".length());
			return b;

		}

		if (key.startsWith("__PIN")) {

			String x = key.substring("__PIN".length());
			String[] xx = x.split(",");

			String x0 = xx[0].trim();
			String x1 = xx[1].trim();

			p.isPin = true;
			b.key = x0;
			b.add1 = x1;
			return b;
		}

		if (key.startsWith("__TOENC")) {

			String x = key.substring("__TOENC".length());
			String[] xx = x.split(",");

			String x0 = xx[0].trim();
			String x1 = xx[1].trim();

			p.isToken = true;
			b.key = x0;
			b.add1 = x1;
			return b;

		}

		if (key.startsWith("__TODEC")) {

			String x = key.substring("__TODEC".length());
			String[] xx = x.split(",");

			String x0 = xx[0].trim();
			String x1 = xx[1].trim();

			p.isTokenDec = true;
			b.key = x0;
			b.add1 = x1;
			return b;
		}

		if (key.endsWith("__PRN_HP_N")) {
			p.isPrnHpN = true;
			b.key = key.substring(0, key.length() - "__PRN_HP_N".length());
			return b;
		}

		if (key.endsWith("__PRN_DTM")) {
			p.isPrnDTM = true;
			b.key = key.substring(0, key.length() - "__PRN_DTM".length());
			return b;
		}

		if (key.endsWith("__PRN_D")) {
			p.isPrnD = true;
			b.key = key.substring(0, key.length() - "__PRN_D".length());
			return b;
		}

		if (key.endsWith("__PARAM")) {
			p.isParam = true;
			b.key = key.substring(0, key.length() - "__PARAM".length());
			return b;
		}

		if (key.endsWith("__PRN_HP_N")) {
			p.isPrnHpN = true;
			b.key = key.substring(0, key.length() - "__PRN_HP_N".length());
			return b;
		}

		if (key.endsWith("__PRN_DTM")) {
			p.isPrnDTM = true;
			b.key = key.substring(0, key.length() - "__PRN_DTM".length());
			return b;
		}

		if (key.endsWith("__PRN_D")) {
			p.isPrnD = true;
			b.key = key.substring(0, key.length() - "__PRN_D".length());
			return b;
		}

		return b;

	}

	/**
	 * @param key
	 * @return
	 */
	SqlRunnerParamB paramParsing(String key) {

		SqlRunnerParamB b = new SqlRunnerParamB();
		b.key = key;
		b.keyOrg = key;

		try {

			b = setDataWhich(b);

			b = setPrefixRule(b);

			b = setSuffixRule(b);

			b.isParam = true;

			return b;

		} catch (Exception ex) {
			trace.write(ex);
			return b;
		}
	}

	/**
	 * @param p
	 * @param box
	 * @return
	 */
	String getDataByParam(SqlRunnerParamB b, Box box) {

		try {

			String data = getDataWhich(b, box);

			if ("".equals(data)) {
				return data;
			}

			return getDataByParam(b, box, data);

		} catch (Exception ex) {
			trace.write(ex);
			return "";
		}
	}

	/**
	 * @param p
	 * @param box
	 * @return
	 */
	String getDataByParam(SqlRunnerParamB b, Box box, String data) {

		try {

			data = convert(b, b.prefix, box, data);

			data = convert(b, b.suffix, box, data);

			return data;

		} catch (Exception ex) {
			trace.write(ex);
			return "";
		}
	}

	/**
	 * @param p
	 * @param c
	 * @param box
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private String convert(SqlRunnerParamB p, SqlRunnerParamConvertB c, Box box, String data) throws Exception {

		if (c.isPrnHpN) {
			return data.substring(0, 3) + "-" + data.substring(3, 7) + "-" + data.substring(7, 11);

		}

		if (c.isPrnD) {
			return data.substring(0, 4) + "/" + data.substring(4, 6) + "/" + data.substring(6, 8);

		}

		if (c.isPrnDTM) {
			return data.substring(0, 4) + "/" + data.substring(4, 6) + "/" + data.substring(6, 8) + " "
					+ data.substring(8, 10) + ":" + data.substring(10, 12) + ":" + data.substring(12, 14);

		}

		if (c.isParam) {
			data = data.replaceAll("-", "");
			data = data.replaceAll("(", "");
			data = data.replaceAll(",", "");
			return data;
		}

		if (c.isEnc) {

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

		if (c.isDec) {

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

		if (c.isPin) {
			String salt = box.s(p.add1);
			return CryptPin.cryptPin(data, salt);
		}

		if (c.isToken) {
			SessionB user = BoxContext.get().getSessionB();
			return user.createToken(p.add1, data);
		}

		if (c.isTokenDec) {
			SessionB user = BoxContext.get().getSessionB();
			return user.getDataByToken(p.add1, data);
		}

		return data;

	}

	/**
	 * @param p
	 * @param box
	 * @return
	 */
	String getDataWhich(SqlRunnerParamB p, Box box) {

		String key = p.key;
		String data = null;

		if (p.isGetBox) {

			Box thisBox = BoxContext.get();
			if (thisBox == null) {
				return "";
			}

			return thisBox.s(key);

		}

		if (p.isGetSessionB) {

			Box thisBox = BoxContext.get();
			if (thisBox == null) {
				return "";
			}

			SessionB user = thisBox.getSessionB();

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
