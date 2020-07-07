package com.code5.fw.data;

import junit.framework.TestCase;

/**
 * @author seuk
 *
 */
public class Table_test extends TestCase {

	public void test_Table_생성() {
		
		String s1 = "abcd";
		String s2 = "abcd";
		
		System.out.println(s1.hashCode()==s2.hashCode());

		String[][] data = { { "1", "홍길동" }, { "2", "홍길순" } };
		String[] cols = { "번호", "이름" };

		Table table = new Table(cols, data);

		assertEquals(2, table.length());
		assertEquals("홍길순", table.s("이름", 1));
		assertEquals("홍길순", table.getBox(1).s("이름"));
	}

	public void test_Table_데이터추가() {

		String[][] data = { { "1", "홍길동" }, { "2", "홍길순" } };
		String[] cols = { "번호", "이름" };

		Table table = new Table(cols, data);
		table.addData("별명", 1, "홍길동친구");

		assertEquals(2, table.length());
		assertEquals("홍길동친구", table.s("별명", 1));
		assertEquals("홍길동친구", table.getBox(1).s("별명"));
	}

	public void test_Table_데이터추가_2() {

		String[][] data = { { "1", "홍길동" }, { "2", "홍길순" } };
		String[] cols = { "번호", "이름" };

		Table table = new Table(cols, data);
		table.addData("별명", 5, "홍길동친구");

		assertEquals(5, table.length());

		assertEquals("홍길동친구", table.s("별명", 5));
		assertEquals("", table.s("별명", 4));

	}
}
