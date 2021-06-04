package com.biz.board;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class Board_test2 {

	/**
	 * 
	 */
	@Test
	public void testCallList() {

		Box box = BoxContext.getThread();

		box.put("FIND_OPT", "01");
		box.put("FIND_STR", "A");

		Board b = new Board();
		b.callList();

		String boardData = (String) box.get("BOARD_DATA");

		assertEquals("01=A", boardData);

	}

}
