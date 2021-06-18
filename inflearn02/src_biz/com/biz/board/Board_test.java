package com.biz.board;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class Board_test {

	/**
	 * 
	 */
	@Test
	public void testCallList() {

		Box box = BoxContext.get();

		box.put("findStr", "abcd");

		Board b = new Board();
		b.exeWrite();

		String list = (String) box.get("list");

		assertEquals("list=[abcd]", list);

	}

}
