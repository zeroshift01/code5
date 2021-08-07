package com.code5.fw.db;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.code5.fw.data.Box;
import com.code5.fw.data.BoxLocal;
import com.code5.fw.data.SessionB;
import com.code5.fw.data.Table;
import com.code5.fw.web.BoxContext;
import com.code5.fw.web.TransactionContext;

/**
 * @author zero
 *
 */
public class SqlRunner_test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		TransactionContext.get().commit();
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void test_03() throws Exception {

		SqlRunner sql = SqlRunner.getSqlRunner();

		Box box = BoxContext.get();
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

	/**
	 * @throws Exception
	 */
	@Test
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

	/**
	 * @throws Exception
	 */
	@Test
	public void test_06() throws Exception {

		SqlRunner sql = SqlRunner.getSqlRunner();
		Box box = BoxContext.get();
		box.setSessionB(new SessionB("IDx", "AUTHx", "IPx"));
		box.put(Box.KEY_REMOTE_ADDR, "1.1.1.1");

		String data = null;
		SqlRunnerParamB p = null;

		p = sql.paramParsing("ENC__D1");
		box.put("D1", "data");
		data = sql.getDataByParam(p, box);
		assertEquals(data, "80c253dcf3a330c5dbb29239f52c8fca");

		data = sql.getDataByParam(p, box);
		assertEquals(data, "80c253dcf3a330c5dbb29239f52c8fca");

		p = sql.paramParsing("DEC__D1");
		box.put("D1", "80c253dcf3a330c5dbb29239f52c8fca");
		data = sql.getDataByParam(p, box);
		assertEquals(data, "data");

		data = sql.getDataByParam(p, box);
		assertEquals(data, "data");

		p = sql.paramParsing("DEC__D1__PRN_HP_N");
		box.put("D1", "9148ca06a3a1de5616d949bc74f5f687");
		data = sql.getDataByParam(p, box);
		assertEquals(data, "010-1111-2222");

		p = sql.paramParsing("D1__PRN_HP_N");
		box.put("D1", "01011112222");
		data = sql.getDataByParam(p, box);
		assertEquals(data, "010-1111-2222");

		p = sql.paramParsing("PIN__PIN, ID");
		box.put("PIN", "abcd1234");
		box.put("ID", "id_A0");
		data = sql.getDataByParam(p, box);
		assertEquals(data, "424ab5a6448f7b6aca9cd65c361b672c3d853622bd29001ee15bc5c50bcfa169");

		p = sql.paramParsing("SESSIONB.ID");
		data = sql.getDataByParam(p, box);
		assertEquals(data, "IDx");

		p = sql.paramParsing("SESSIONB.IP");
		data = sql.getDataByParam(p, box);
		assertEquals(data, "1.1.1.1");

		p = sql.paramParsing("SESSIONB.AUTH");
		data = sql.getDataByParam(p, box);
		assertEquals(data, "AUTHx");

		p = sql.paramParsing("SYSDTM.DTM");
		data = sql.getDataByParam(p, box);
		System.out.println(data);

		p = sql.paramParsing("SYSDTM.DTM, D, 60");
		data = sql.getDataByParam(p, box);
		System.out.println(data);

		p = sql.paramParsing("SYSDTM.DTM__PRN_DTM");
		data = sql.getDataByParam(p, box);
		System.out.println(data);

		p = sql.paramParsing("SYSDTM.DTM__PRN_D");
		data = sql.getDataByParam(p, box);
		System.out.println(data);

		p = sql.paramParsing("SYSDTM.D");
		data = sql.getDataByParam(p, box);
		System.out.println(data);

		p = sql.paramParsing("SYSDTM.D, D, 60");
		data = sql.getDataByParam(p, box);
		System.out.println(data);

		p = sql.paramParsing("SYSDTM.D__PRN_D");
		data = sql.getDataByParam(p, box);
		System.out.println(data);

		p = sql.paramParsing("SYSDTM.TM");
		data = sql.getDataByParam(p, box);
		System.out.println(data);

		p = sql.paramParsing("SYSDTM.TM, S, 60");
		data = sql.getDataByParam(p, box);
		System.out.println(data);

		box.put("P1", "80c253dcf3a330c5dbb29239f52c8fca");
		Box thisBox = new BoxLocal();

		p = sql.paramParsing("BOX.P1");
		data = sql.getDataByParam(p, thisBox);
		assertEquals(data, "80c253dcf3a330c5dbb29239f52c8fca");

		p = sql.paramParsing("BOX.ENC__P1");
		data = sql.getDataByParam(p, thisBox);
		assertEquals(data,
				"6c4c4558324c97e8e6b8655b68e27666b000a31d04835cfb0e86d91e302a0fb150e5cc4b6178363ff73ff8457ec4ca7d");

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void test_07() throws Exception {
		Sql sql = new Sql(this);
		Box box = BoxContext.get();
		box.setSessionB(new SessionB("IDx", "AUTHx", "IPx"));
		box.put(Box.KEY_REMOTE_ADDR, "1.1.1.1");

		box.put("P1", "HI");
		box.put("P2", "");
		box.put("P3", "Y");
		box.put("HP_N", "01022223333");
		
		Table table = sql.getTable("SQLRUNNER_TEST_03");
		System.out.println(table.toString());
		for (int i = 0; i < 1000; i++) {
			table = sql.getTable("SQLRUNNER_TEST_03");
			System.out.println(table.toString());
		}

	}

	public void 동작안함_test_10() throws Exception {
		Sql sql = new Sql(this);
		sql.getTable("SQLRUNNER_TEST_10");
	}
}
