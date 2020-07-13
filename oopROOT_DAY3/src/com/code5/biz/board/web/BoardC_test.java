package com.code5.biz.board.web;

import com.code5.fw.web.Box;

/**
 * @author seuk
 *
 */
public class BoardC_test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// [1]
		Box box = Box.getThread();

		box.put("name", "hello world!");

		BoardC boardC = new BoardC();
		boardC.welcome();

	}

}
