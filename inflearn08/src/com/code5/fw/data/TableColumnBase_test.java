package com.code5.fw.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * @author zero
 *
 */
class TableColumnBase_test {

	/**
	 * 
	 */
	@Test
	public void Table_기본기능() {

		Table table = new TableColumnBase();

		table.addCol("번호", new String[] { "1", "2", "3" });
		table.addCol("이름", new String[] { "홍길동", "홍길순", "로버트" });

		assertEquals(3, table.size());

		assertEquals("홍길순", table.s("이름", 1));
		assertEquals("홍길순", table.getBox(1).s("이름"));
	}

	/**
	 * 
	 */
	@Test
	void Table_데이터수정() {

		Table table = new TableColumnBase();

		table.addCol("번호", new String[] { "1", "2", "3" });
		table.addCol("이름", new String[] { "홍길동", "홍길순", "로버트" });

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
	void Table_수정할수없는데이터() {

		Table table = new TableColumnBase();

		table.addCol("번호", new String[] { "1", "2", "3" });
		table.addCol("이름", new String[] { "홍길동", "홍길순", "로버트" });

		table.addCol("별명");

		RuntimeException exx = null;
		try {
			table.setData("별명", 5, "홍길동친구");
		} catch (RuntimeException ex) {
			exx = ex;
		}

		assertNotNull(exx);

	}
}
