package com.code5.fw.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * @author zero
 *
 */
class TableRecodeBase_test {

	/**
	 * 
	 */
	@Test
	void test_Table_기본기능() {

		String[] cols = { "번호", "이름" };

		Table table = new TableRecodeBase(cols);

		table.addRecode(new String[] { "1", "홍길동" });
		table.addRecode(new String[] { "2", "홍길순" });
		table.addRecode(new String[] { "3", "로버트" });

		assertEquals(3, table.size());

		assertEquals("홍길순", table.s("이름", 1));
		assertEquals("홍길순", table.getBox(1).s("이름"));
	}

	/**
	 * 
	 */
	@Test
	void test_Table_데이터수정() {

		String[] cols = { "번호", "이름" };

		Table table = new TableRecodeBase(cols);

		table.addRecode(new String[] { "1", "홍길동" });
		table.addRecode(new String[] { "2", "홍길순" });
		table.addRecode(new String[] { "3", "로버트" });

		table.addCol("별명");

		table.setData("별명", 1, "홍길동친구");

		assertEquals(3, table.size());
		assertEquals("홍길동친구", table.s("별명", 1));
		assertEquals("홍길동친구", table.getBox(1).s("별명"));
	}

	/**
	 * 
	 */
	@Test
	void test_Table_수정할수없는데이터() {

		String[] cols = { "번호", "이름" };

		Table table = new TableRecodeBase(cols);

		table.addRecode(new String[] { "1", "홍길동" });
		table.addRecode(new String[] { "2", "홍길순" });
		table.addRecode(new String[] { "3", "로버트" });

		table.addCol("별명");

		RuntimeException exx = null;
		try {
			table.setData("별명", 5, "홍길동친구");
		} catch (RuntimeException ex) {
			exx = ex;
		}

		assertNotNull(exx);

	}

	/**
	 * 
	 */
	@Test
	void test_Table_최대추가할수있는크기확인() {

		String[] cols = { "번호", "이름" };

		Table table = new TableRecodeBase(cols);

		for (int i = 0; i < 10000; i++) {
			table.addRecode(new String[] { "1", "홍길동" });
			assertEquals(table.isNextRecode(), false);
		}

		assertEquals(table.size(), table.maxRecodeCount());

		assertEquals(table.addRecode(new String[] { "1", "홍길동" }), false);

		assertEquals(table.isNextRecode(), true);

	}
}
