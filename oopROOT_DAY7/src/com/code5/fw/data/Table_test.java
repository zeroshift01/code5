package com.code5.fw.data;

import junit.framework.TestCase;

/**
 * @author seuk
 *
 */
public class Table_test extends TestCase {

	public void test_Table_생성() {

		String[] cols = { "번호", "이름" };

		Table table = new Table(cols);

		table.addRecode(new String[] { "1", "홍길동" });
		table.addRecode(new String[] { "2", "홍길순" });
		table.addRecode(new String[] { "3", "로버트" });

		assertEquals(3, table.length());
		assertEquals("홍길순", table.s("이름", 1));
		assertEquals("홍길순", table.getBox(1).s("이름"));
	}

	public void test_Table_데이터추가() {

		String[] cols = { "번호", "이름" };

		Table table = new Table(cols);

		table.addRecode(new String[] { "1", "홍길동" });
		table.addRecode(new String[] { "2", "홍길순" });
		table.addRecode(new String[] { "3", "로버트" });

		table.addCol("별명");

		table.addCol("별명");

		table.setData("별명", 1, "홍길동친구");

		assertEquals(3, table.length());
		assertEquals("홍길동친구", table.s("별명", 1));
		assertEquals("홍길동친구", table.getBox(1).s("별명"));
	}

	public void test_Table_데이터추가_2() {

		String[] cols = { "번호", "이름" };

		Table table = new Table(cols);

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

	public void test_Table_데이터추가_3() {

		String[] cols = { "번호", "이름" };

		Table table = new Table(cols);

		table.addRecode(new String[] { "1", "홍길동" });
		table.addRecode(new String[] { "2", "홍길순" });
		table.addRecode(new String[] { "3", "로버트" });

		table.addCol("별명");

		table.addRecode();
		table.addRecode();
		table.addRecode();

		table.setData("별명", 5, "홍길동친구");

		assertEquals(6, table.length());

		assertEquals("홍길동친구", table.s("별명", 5));
		assertEquals("", table.s("별명", 4));

	}
}
