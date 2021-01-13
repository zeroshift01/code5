package com.code5.fw.db;

import com.code5.fw.data.Box;
import com.code5.fw.data.DateTime;
import com.code5.fw.data.InitProperty;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.security.CryptPin;
import com.code5.fw.security.DataCrypt;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

import junit.framework.TestCase;

/**
 * @author seuk
 *
 */
public class SqlRunner_test extends TestCase {

	protected void setUp() throws Exception {
		InitProperty.init(this);
	}

	@Override
	protected void tearDown() throws Exception {
		TransactionContext.getThread().commit();
	}

	/**
	 * @throws Exception
	 * 
	 * 
	 */
	public void x__test_01() throws Exception {

		Box box = BoxContext.getThread();
		box.put("KEY", "THIS_KEY");

		SqlRunner sql = SqlRunner.getSqlRunner();

		Table table = sql.getTable("SQLRUNNER__test_01");

		String[] cols = table.getCols();
		for (int i = 0; i < table.size(); i++) {

			for (int j = 0; j < cols.length; j++) {

				System.out.println(table.s(cols[j], i));

			}

		}

	}

	/**
	 * @throws Exception
	 * 
	 */
	public void x__test_02() throws Exception {

		Box box = BoxContext.getThread();
		box.put("KEY", "THIS_KEY");
		box.put("SQL", "THIS_SQL");

		SqlRunner sql = SqlRunner.getSqlRunner();

		if (sql.executeSql("SQLRUNNER__test_02_2") == 0) {
			sql.executeSql("SQLRUNNER__test_02_1");
		}

	}

	/**
	 * @throws Exception
	 */
	public void __test_03() throws Exception {

		SqlRunner sql = SqlRunner.getSqlRunner();

		Box box = BoxContext.getThread();
		String xx = "";

		box.put("P2", "");

		xx = " P2 ^ IS_NULL ^ AND 2 = 2 ";

		assertEquals(sql.ifParsing(xx, box), "AND 2 = 2");

		box.put("P2", "HI");

		assertEquals(sql.ifParsing(xx, box), "");

		box.put("P2", "");

		xx = " P2 ^ IS_NOT_NULL ^ AND 2 = 2 ";

		assertEquals(sql.ifParsing(xx, box), "");

		box.put("P2", "HI");

		assertEquals(sql.ifParsing(xx, box), "AND 2 = 2");

		box.put("P2", "");

		xx = " P2 ^ HI ^ AND 2 = 2 ";

		assertEquals(sql.ifParsing(xx, box), "");

		box.put("P2", "HI");

		assertEquals(sql.ifParsing(xx, box), "AND 2 = 2");

	}

	public void _test_04() throws Exception {
		Box box = BoxContext.getThread();
		box.put("P1", "HI");
		box.put("P2", "");
		box.put("P3", "Y");

		SqlRunner sql = SqlRunner.getSqlRunner();
		Table table = sql.getTable("SQLRUNNER__test_03");
		table.toString();

	}

	public void test_05() throws Exception {

		SqlRunner sql = SqlRunner.getSqlRunner();

		String[] data = new String[] { "X.X__X1__X2", "ENC__D1", "DEC__D2", "ENC__D3__PRN_HP_N", "D4__PRN_HP_N",
				"PIN__PIN, ID", "SESSIONB.ID", "SESSIONB.IP", "SESSIONB.AUTH", "SYSDTM.DTM", "SYSDTM.DTM, D, 60",
				"SYSDTM.DTM__PRN_DTM", "SYSDTM.D", "SYSDTM.D, D, 60", "SYSDTM.D__PRN_D", "SYSDTM.TM",
				"SYSDTM.TM, S, 60", "BOX.P1", "BOX.ENC__P1", "BOX.P1__PRN_HP_N", "BOX.DEC__P1__PRN_HP_N" };

		String[] check = new String[] { "X.X__X1__X2", "D1", "D2", "D3", "D4", "PIN", "ID", "IP", "AUTH", "DTM", "DTM",
				"DTM", "D", "D", "D", "TM", "TM", "P1", "P1", "P1", "P1" };

		for (int i = 0; i < data.length; i++) {
			SqlRunnerParamB p = sql.paramParsing(data[i]);
			assertEquals(check[i], p.key);
		}
	}

	public void test_06() throws Exception {

		SqlRunner sql = SqlRunner.getSqlRunner();
		Box box = BoxContext.getThread();
		box.setSessionB(new SessionB("IDx", "AUTHx", "IPx"));
		box.put(Box.KEY_REMOTE_ADDR, "1.1.1.1");

		String data = null;
		SqlRunnerParamB p = null;

		p = sql.paramParsing("ENC__D1");
		box.put("D1", "data");
		data = getDataByParam(p, box);
		assertEquals(data, "80c253dcf3a330c5dbb29239f52c8fca");

		p = sql.paramParsing("DEC__D1");
		box.put("D1", "80c253dcf3a330c5dbb29239f52c8fca");
		data = getDataByParam(p, box);
		assertEquals(data, "data");

		p = sql.paramParsing("DEC__D1__PRN_HP_N");
		box.put("D1", "80c253dcf3a330c5dbb29239f52c8fca");
		data = getDataByParam(p, box);
		assertEquals(data, "data휴대폰형식화");

		p = sql.paramParsing("D1__PRN_HP_N");
		box.put("D1", "data");
		data = getDataByParam(p, box);
		assertEquals(data, "data휴대폰형식화");

		p = sql.paramParsing("PIN__PIN, ID");
		box.put("PIN", "abcd1234");
		box.put("ID", "id_A0");
		data = getDataByParam(p, box);
		assertEquals(data, "424ab5a6448f7b6aca9cd65c361b672c3d853622bd29001ee15bc5c50bcfa169");

		p = sql.paramParsing("SESSIONB.ID");
		data = getDataByParam(p, box);
		assertEquals(data, "IDx");

		p = sql.paramParsing("SESSIONB.IP");
		data = getDataByParam(p, box);
		assertEquals(data, "1.1.1.1");

		p = sql.paramParsing("SESSIONB.AUTH");
		data = getDataByParam(p, box);
		assertEquals(data, "AUTHx");

		p = sql.paramParsing("SYSDTM.DTM");
		p = sql.paramParsing("SYSDTM.DTM, D, 60");
		p = sql.paramParsing("SYSDTM.DTM__PRN_DTM");
		p = sql.paramParsing("SYSDTM.DTM__PRN_D");
		p = sql.paramParsing("SYSDTM.D");
		p = sql.paramParsing("SYSDTM.D, D, 60");
		p = sql.paramParsing("SYSDTM.D__PRN_D");
		p = sql.paramParsing("SYSDTM.TM");
		p = sql.paramParsing("SYSDTM.TM, S, 60");
		p = sql.paramParsing("BOX.P1");
		p = sql.paramParsing("BOX.ENC__P1");
		p = sql.paramParsing("BOX.P1__PRN_HP_N");
		p = sql.paramParsing("BOX.DEC__P1__PRN_HP_N");

	}

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

	String getDataByParamStep2(SqlRunnerParamB p, Box box, String data) {

		try {

			DataCrypt dataCrypt = DataCrypt.getDataCrypt("SDB");

			if (p.isEnc) {
				return dataCrypt.encrypt(data);
			}

			if (p.isDec) {
				return dataCrypt.decrypt(data);
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

				// 시간계산
				return data;
			}

			if ("S".equals(x1)) {

				if (x2 == null) {
					return "";
				}

				return data;
			}

		}

		return box.s(key);
	}
}
