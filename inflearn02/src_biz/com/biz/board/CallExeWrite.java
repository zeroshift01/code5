package com.biz.board;

import com.code5.fw.data.Box;
import com.code5.fw.web.BoxContext;

/**
 * @author zero
 *
 */
public class CallExeWrite {

	public static void main(String[] xx) {

		Box box = BoxContext.get();
		box.put("txt", "ABCD");

		Board b = new Board();
		b.exeWrite();

		for (int i = 0; i < 10; i++) {
			box.put("txt", "ABCD" + i);
			b.exeWrite();
		}

	}

}
